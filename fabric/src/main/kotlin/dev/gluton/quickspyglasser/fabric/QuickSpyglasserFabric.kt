package dev.gluton.quickspyglasser.fabric

import dev.gluton.quickspyglasser.QuickSpyglasser
import net.fabricmc.api.ModInitializer

object QuickSpyglasserFabric : ModInitializer {
	override fun onInitialize() {
		QuickSpyglasser.init()
	}
}
