package dev.gluton.quickspyglasser

import dev.architectury.injectables.annotations.ExpectPlatform
import dev.architectury.utils.PlatformExpectedError
import java.nio.file.Path

object QuickSpyglasserExpectPlatform {
	@ExpectPlatform
	@JvmStatic
	fun configDirectory(): Path = throw PlatformExpectedError()
}
