package dev.gluton.quickspyglasser.fabric

import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Path

object QuickSpyglasserExpectPlatformImpl {
	@JvmStatic
	fun configDirectory(): Path = FabricLoader.getInstance().configDir
}
