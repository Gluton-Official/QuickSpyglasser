package dev.gluton.quickspyglasser.forge

import dev.architectury.platform.forge.EventBuses
import dev.gluton.quickspyglasser.QuickSpyglasser
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.KotlinModLoadingContext

@Mod(QuickSpyglasser.ID)
object QuickSpyglasserForge {
	init {
		EventBuses.registerModEventBus(QuickSpyglasser.ID, KotlinModLoadingContext.get().getKEventBus())
		QuickSpyglasser.init()
	}
}
