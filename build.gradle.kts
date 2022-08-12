//plugins {
//    kotlin("jvm")
//
//    id("architectury-plugin")
//    id("dev.architectury.loom") apply false
////    id("com.matthewprenger.cursegradle")
////    id("com.modrinth.minotaur")
//}
//
//val modId: String by project
//val modGroup: String by project
//val modVersion: String by project
//
//val String.version: String
//    get() = rootProject.properties["${this}Version"].toString()
//val String.asPropertyMapping: Pair<String, String>
//    get() = this to project.properties[this].toString()
//
//infix fun String.`+`(other: String) = "$this+$other"
//infix fun String.`-`(other: String) = "$this-$other"
//
//val minecraftMajVersion = "minecraft".version.split('.').take(2).joinToString(".")
//
//architectury {
//    minecraft = minecraftMajVersion
//}
//
//base {
//    archivesName.set(modId)
//}
//
//allprojects {
//    apply("plugin" to "kotlin")
//    apply("plugin" to "architectury-plugin")
//    apply("plugin" to "maven-publish")
//
//    group = modGroup
//    version = modVersion
//
//    val javaVersion = JavaVersion.toVersion(System.getProperty("java.version"))
//    tasks {
//        withType<JavaCompile> {
//            options.encoding = "UTF-8"
//            options.release.set(javaVersion.toString().toInt())
//            sourceCompatibility = javaVersion.toString()
//            targetCompatibility = javaVersion.toString()
//        }
//
////        compileKotlin {
////            kotlinOptions {
////                jvmTarget = javaVersion.toString()
////            }
////        }
//
////        jar {
////            from("LICENSE") {
////                rename { "${it}_$modId" }
////            }
////        }
//
////        processResources {
////            val properties = listOf(
////                "modName",
////                "modId",
////                "modVersion",
////                "modDescription",
////                "fabricLoaderVersion",
////                "fabricKotlinVersion",
////            ).associate { it.asPropertyMapping } + mapOf(
////                "packagePath" to "$modGroup.$modId",
////                "javaVersion" to javaVersion.toString(),
////                "minecraftVersion" to "$minecraftMajVersion.x"
////            )
////
////            inputs.properties(properties)
////            filesMatching(listOf("fabric.mod.json", "quickspyglasser.mixins.json")) {
////                expand(properties)
////            }
////        }
//    }
//
//    java {
////        toolchain {
////            languageVersion.set(JavaLanguageVersion.of(javaVersion.toString()))
////        }
//        withSourcesJar()
//    }
//}
//
//subprojects {
//    apply("plugin" to "dev.architectury.loom")
//
//    loom {
//        silentMojangMappingsLicense()
//    }
//
//    dependencies {
//        minecraft("com.mojang", "minecraft", "minecraft".version)
//        mappings("net.fabricmc", "yarn", "minecraft".version `+` "yarnMappings".version, null, "v2")
//    }
//}
//
////dependencies {
////    minecraft("com.mojang", "minecraft", "minecraft".version)
////    forge("net.minecraftforge", "forge", "minecraft".version `-` "forge".version)
////
////    modImplementation("net.fabricmc", "fabric-loader", "fabricLoader".version)
////    modImplementation("net.fabricmc.fabric-api", "fabric-api", "fabric".version `+` "minecraft".version)
////    modImplementation("net.fabricmc", "fabric-language-kotlin", "fabricKotlin".version `+` "kotlin.${System.getProperty("kotlinVersion")}")
////}
