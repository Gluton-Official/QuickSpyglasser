package dev.gluton.quickspyglasser.forge

import net.minecraftforge.fml.loading.FMLPaths
import java.nio.file.Path

object QuickSpyglasserExpectPlatformImpl {
	@JvmStatic
	fun configDirectory(): Path = FMLPaths.CONFIGDIR.get()
}
