import org.gradle.api.Project

infix fun String.`+`(other: String) = "$this+$other"
infix fun String.`-`(other: String) = "$this-$other"

fun Project.versionStringOf(dependency: String) = properties["${dependency}Version"].toString()
fun Project.versionOf(dependency: String) = Version(versionStringOf(dependency))

data class Version(val major: String, val minor: String, val patch: String? = null) {
	constructor(version: String) : this(
		version.split('.').first(),
		version.split('.').drop(1).first(),
		version.split('.').getOrNull(2)
	)

	val majMinor: String get() = "$major.$minor"

	override fun toString() = "$major.$minor${patch?.let { ".$it" } ?: ""}"
}
