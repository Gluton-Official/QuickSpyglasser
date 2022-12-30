plugins {
    `maven-publish`
    id("architectury-plugin")
}

val modId: String by rootProject.properties

architectury {
    common(rootProject.properties["architecturyPlatforms"].toString().split(","))
}

loom {
    accessWidenerPath.set(file("src/main/resources/$modId.accesswidener"))
}

dependencies {
    // We depend on fabric loader here to use the fabric @Environment annotations and get the mixin dependencies
    // Do NOT use other classes from fabric loader
    modImplementation("net.fabricmc", "fabric-loader", versionStringOf("fabricLoader"))
    modApi("dev.architectury", "architectury", versionStringOf("architectury")) {
        exclude(group = "net.fabricmc", module = "fabric-loader")
    }

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
}

tasks {
    processResources {
        val gradleProperties: Map<String, String> by rootProject.extra
        inputs.properties(gradleProperties)
        filesMatching(listOf("$modId-common.mixins.json", "architectury.common.json")) {
            expand(gradleProperties)
        }
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                artifactId = modId
                from(getComponents().getByName("java"))
            }
        }

        // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
        repositories {
            // Add repositories to publish to here.
        }
    }
}
