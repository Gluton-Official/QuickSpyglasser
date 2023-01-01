package dev.gluton.quickspyglasser.forge

import dev.architectury.platform.forge.EventBuses
import dev.gluton.quickspyglasser.QuickSpyglasser
import dev.gluton.quickspyglasser.client.QuickSpyglasserClient
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist

@Mod(QuickSpyglasser.ID)
object QuickSpyglasserForge {
	init {
		EventBuses.registerModEventBus(QuickSpyglasser.ID, MOD_BUS)

		runForDist(
			clientTarget = {
				MOD_BUS.addListener { _: FMLClientSetupEvent -> QuickSpyglasserClient.init() }
			},
			serverTarget = {}
		)
	}
}
