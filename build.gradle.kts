
import dev.architectury.plugin.ArchitecturyPlugin
import net.fabricmc.loom.api.LoomGradleExtensionAPI
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

plugins {
    java
    `kotlin-dsl`
    kotlin("jvm") apply false

    id("architectury-plugin")
    id("dev.architectury.loom") apply false

    // TODO: setup
//    id("com.matthewprenger.cursegradle")
//    id("com.modrinth.minotaur")
}

val modId: String by rootProject
val modGroup: String by rootProject
val modVersion: String by rootProject

val gradleProperties: Map<String, String> by extra(
    listOf(
        "modDisplayName",
        "modId",
        "modGroup",
        "modVersion",
        "modAuthors",
        "modDescription",
        "modLicense",
        "modHomepage",
        "modSources",
        "modIssues"
    ).associateWith { rootProject.properties[it].toString() }
    + rootProject.properties
        .filter { (k, _) -> k.endsWith("Version") }
        .mapValues { (_, v) -> v.toString() }
    + mapOf(
        "modName" to rootProject.name,
        "packagePath" to "${rootProject.properties["modGroup"]}.${rootProject.properties["modId"]}",
        "javaVersion" to System.getProperty("java.version"),
        "minecraftMajMinVersion" to versionOf("minecraft").majMinor,
        "forgeMajVersion" to versionOf("forge").major,
        "kotlinForForgeMajMinVersion" to versionOf("kotlinForForge").majMinor,
        "fabricLoaderMajMinVersion" to versionOf("fabricLoader").majMinor,
    )
)

architectury {
    minecraft = gradleProperties["minecraftMajMinVersion"]!!.toString()
}

subprojects {
    apply(plugin = "dev.architectury.loom")

    val loom: LoomGradleExtensionAPI by project.extensions

    loom.silentMojangMappingsLicense()

    dependencies {
        "minecraft"("com.mojang", "minecraft", versionStringOf("minecraft"))
        "mappings"(loom.officialMojangMappings())
    }
}

allprojects {
    apply<JavaPlugin>()
    apply<ArchitecturyPlugin>()
    apply<MavenPublishPlugin>()
    apply<KotlinPluginWrapper>()

    base.archivesName.set(modId)
    group = modGroup
    version = modVersion

    repositories {

    }

    dependencies {
        compileOnly("org.jetbrains.kotlin", "kotlin-gradle-plugin", System.getProperty("kotlinVersion"))
    }

    val javaVersion = JavaVersion.toVersion(System.getProperty("java.version")).toString()
    tasks {
        compileJava {
            options.encoding = "UTF-8"
            options.release.set(javaVersion.toInt())
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }

        compileKotlin {
            kotlinOptions {
                jvmTarget = javaVersion
                freeCompilerArgs = listOf(
                    "-Xjvm-default-all",
                    "-Xlambdas=indy",
                )
            }
        }

        jar {
            from("LICENSE") {
                rename { "${it}_$modId" }
            }
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(javaVersion))
        }
        withSourcesJar()
    }
}
