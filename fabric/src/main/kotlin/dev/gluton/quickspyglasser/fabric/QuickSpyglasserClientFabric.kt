package dev.gluton.quickspyglasser.fabric

import dev.gluton.quickspyglasser.client.QuickSpyglasserClient
import net.fabricmc.api.ClientModInitializer

object QuickSpyglasserClientFabric : ClientModInitializer {
	override fun onInitializeClient() {
		QuickSpyglasserClient.init()
	}
}
