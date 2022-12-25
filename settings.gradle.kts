pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        maven("https://maven.architectury.dev/") { name = "Architectury" }
        maven("https://files.minecraftforge.net/maven/") { name = "Forge" }
//        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        kotlin("jvm") version System.getProperty("kotlinVersion")

        val architecturyPluginVersion: String by settings
        val architecturyLoomVersion: String by settings
        val shadowVersion: String by settings
        val curseGradleVersion: String by settings
        val modrinthVersion: String by settings

        id("architectury-plugin") version architecturyPluginVersion
        id("dev.architectury.loom") version architecturyLoomVersion
        id("com.github.johnrengelman.shadow") version shadowVersion
        id("com.matthewprenger.cursegradle") version curseGradleVersion
        id("com.modrinth.minotaur") version modrinthVersion
    }
}

include("common", "fabric", "forge")

val modDisplayName: String by settings
rootProject.name = modDisplayName.filterNot(Char::isWhitespace)
