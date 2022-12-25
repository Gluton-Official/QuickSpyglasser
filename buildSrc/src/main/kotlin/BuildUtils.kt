import org.gradle.api.Project

fun Project.versionOf(dependency: String) = properties["${dependency}Version"].toString()

infix fun String.`+`(other: String) = "$this+$other"
infix fun String.`-`(other: String) = "$this-$other"
