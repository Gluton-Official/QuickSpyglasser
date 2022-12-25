plugins {
    id("com.github.johnrengelman.shadow")
}

val modId: String by rootProject.properties

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating // Don't use shadow from the shadow plugin because we don't want IDEA to index this.
val developmentFabric: Configuration = configurations.getByName("developmentFabric")

// imports parent project's configurations
configurations {
    compileClasspath.get().extendsFrom(configurations["common"])
    runtimeClasspath.get().extendsFrom(configurations["common"])
    developmentFabric.extendsFrom(configurations["common"])
}

dependencies {
    modApi("net.fabricmc.fabric-api", "fabric-api", versionOf("fabric") `+` versionOf("minecraft"))
    modApi("dev.architectury", "architectury-fabric", versionOf("architectury"))
//    modImplementation("net.fabricmc", "fabric-loader", versionOf("fabricLoader"))
    modImplementation("net.fabricmc", "fabric-language-kotlin", versionOf("fabricKotlin") `+` "kotlin.${System.getProperty("kotlinVersion")}")

    // uses parent projects as a dependency
    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionFabric")) { isTransitive = false }
}

val javaComponent = components.getByName<AdhocComponentWithVariants>("java")

// TODO: put in extended project?
javaComponent.withVariantsFromConfiguration(configurations["shadowRuntimeElements"]) {
//javaComponent.withVariantsFromConfiguration(configurations["sourceElements"]) {
    skip()
}

tasks {
    processResources {
        // TODO: simplify
        val gradleProperties: Map<String, String> by rootProject.extra
        inputs.properties(gradleProperties)
        filesMatching(listOf("fabric.mod.json", "$modId.mixins.json")) {
            expand(gradleProperties)
        }
    }

    shadowJar {
        exclude("architectury.common.json")

        configurations = listOf(project.configurations["shadowCommon"])
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        injectAccessWidener.set(true)
        inputFile.set(shadowJar.flatMap { it.archiveFile })
        dependsOn(shadowJar)
        archiveClassifier.set("fabric")
    }

    jar {
        archiveClassifier.set("dev")
    }

    sourcesJar {
        val commonSources = project(":common").tasks.getByName<Jar>("sourcesJar")
        dependsOn(commonSources)
        from(commonSources.archiveFile.map { zipTree(it) })
    }

    publishing {
        publications {
            create<MavenPublication>("mavenFabric") {
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
