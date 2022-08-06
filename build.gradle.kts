plugins {
//    id("dev.architectury.loom") version "0.11.0-SNAPSHOT"
    id("fabric-loom") version "0.9-SNAPSHOT"
    id("com.matthewprenger.cursegradle") version "1.4.0"
//    id("com.modrinth.minotaur") version "2.+"
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

base {
    archivesBaseName = project.properties["archives_base_name"].toString()
}

version = project.properties["mod_version"]
group = project.properties["maven_group"]

repositories {
    mavenCentral()
    maven { url = uri("https://maven.terraformersmc.com") }
    maven { url = uri("https://maven.shedaniel.me/") }
    maven {
        name = "Ladysnake Libs"
        url = uri("https://ladysnake.jfrog.io/artifactory/mods")
    }
    flatDir {
        dirs("libs") // for trinkets since latest version isn't on maven
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${project.properties["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${project.properties["yarn_mappings"]}:v2")
//    forge("net.minecraftforge:forge:${project.minecraft_version}-${project.forge_version}")

    modImplementation("net.fabricmc:fabric-loader:${project.properties["loader_version"]}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.properties["fabric_version"]}")
    modImplementation("me.shedaniel.cloth:cloth-config-fabric:${project.properties["cloth_version"]}") {
        exclude(group = "net.fabricmc.fabric-api")
    }
    modImplementation("dev.emi:trinkets:${project.properties["trinkets_version"]}") {
        exclude(group = "net.fabricmc.fabric-api")
    }
    // required for trinkets
    modImplementation("io.github.onyxstudios.Cardinal-Components-API:cardinal-components-base:${project.properties["cca_version"]}")
    modImplementation("io.github.onyxstudios.Cardinal-Components-API:cardinal-components-entity:${project.properties["cca_version"]}")

    modCompileOnly("com.terraformersmc:modmenu:${project.properties["modmenu_version"]}") {
        exclude(group = "net.fabricmc.fabric-api")
    }
    modCompileOnly("dev.emi:trinkets:${project.properties["trinkets_version"]}")
    // required for trinkets
    modCompileOnly("io.github.onyxstudios.Cardinal-Components-API:cardinal-components-base:${project.properties["cca_version"]}")
    modCompileOnly("io.github.onyxstudios.Cardinal-Components-API:cardinal-components-entity:${project.properties["cca_version"]}")

    modRuntime("com.terraformersmc:modmenu:${project.properties["modmenu_version"]}")
    modRuntime("dev.emi:trinkets:${project.properties["trinkets_version"]}")
    // required for trinkets
    modRuntime("io.github.onyxstudios.Cardinal-Components-API:cardinal-components-base:${project.properties["cca_version"]}")
    modRuntime("io.github.onyxstudios.Cardinal-Components-API:cardinal-components-entity:${project.properties["cca_version"]}")
}

tasks.processResources {
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

tasks.compileJava {
    options.release.set(16)
}

java {
    withSourcesJar()
}
