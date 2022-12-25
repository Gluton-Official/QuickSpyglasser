plugins {
    id("com.github.johnrengelman.shadow")
}

val modId: String by rootProject.properties

architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)

    forge {
        convertAccessWideners.set(true)
        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)

        mixinConfig("$modId-common.mixins.json")
        mixinConfig("$modId.mixins.json")
    }
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating // Don't use shadow from the shadow plugin because we don't want IDEA to index this.
val developmentForge: Configuration = configurations.getByName("developmentForge")

// imports parent project's configurations
configurations {
    compileClasspath.get().extendsFrom(configurations["common"])
    runtimeClasspath.get().extendsFrom(configurations["common"])
    developmentForge.extendsFrom(configurations["common"])
}

dependencies {
    forge("net.minecraftforge", "forge", versionOf("minecraft") `-` versionOf("forge"))
    modApi("dev.architectury", "architectury-forge", versionOf("architectury"))

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionForge")) { isTransitive = false }

//    common(kotlin("stdlib-jdk8"))
}

val javaComponent = components.getByName<AdhocComponentWithVariants>("java")

javaComponent.withVariantsFromConfiguration(configurations["shadowRuntimeElements"]) {
//javaComponent.withVariantsFromConfiguration(configurations["sourceElements"]) {
    skip()
}

tasks {
    processResources {
        val gradleProperties: Map<String, String> by rootProject.extra
        inputs.properties(gradleProperties)
        filesMatching(listOf("META-INF/mods.toml", "$modId.mixins.json", "pack.mcmeta")) {
            expand(gradleProperties)
        }
    }

    shadowJar {
        exclude("fabric.mod.json")
        exclude("architectury.common.json")

        configurations = listOf(project.configurations["shadowCommon"])
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        inputFile.set(shadowJar.flatMap { it.archiveFile })
        dependsOn(shadowJar)
        archiveClassifier.set("forge")
    }

    jar {
        archiveClassifier.set("dev")
    }

    sourcesJar {
        val commonSources = project(":common").tasks.getByName<Jar>("sourcesJar")
        dependsOn(commonSources)
        from(commonSources.archiveFile.map { zipTree(it) })
        duplicatesStrategy = DuplicatesStrategy.INCLUDE // TODO: why only forge?
    }

    publishing {
        publications {
            create<MavenPublication>("mavenForge") {
                artifactId = modId `-` rootProject.name
                from(javaComponent)
            }
        }

        // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
        repositories {
            // Add repositories to publish to here.
        }
    }
}
