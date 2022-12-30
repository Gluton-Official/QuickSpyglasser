package dev.gluton.quickspyglasser.forge

import dev.architectury.platform.forge.EventBuses
import dev.gluton.quickspyglasser.QuickSpyglasserCommon
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.KotlinModLoadingContext

@Mod(QuickSpyglasserCommon.ID)
object QuickSpyglasser {
	init {
		EventBuses.registerModEventBus(QuickSpyglasserCommon.ID, KotlinModLoadingContext.get().getKEventBus())
	}
}
