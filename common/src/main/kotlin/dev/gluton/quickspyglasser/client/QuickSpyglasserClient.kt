package dev.gluton.quickspyglasser.client

import com.mojang.blaze3d.platform.InputConstants
import dev.architectury.event.events.client.ClientTickEvent
import dev.architectury.registry.client.keymappings.KeyMappingRegistry
import dev.gluton.quickspyglasser.QuickSpyglasser
import dev.gluton.quickspyglasser.isSpyglassing
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer

@Environment(EnvType.CLIENT)
object QuickSpyglasserClient {
	val logger = QuickSpyglasser.logger

	private val spyglassKeybind = KeyMapping(
		"key.quickspyglasser.spyglass",
		InputConstants.Type.KEYSYM,
		InputConstants.KEY_Z,
		"category.quickspyglasser.quickspyglasser"
	)

	fun init() {
		KeyMappingRegistry.register(spyglassKeybind)
		ClientTickEvent.CLIENT_POST.register(QuickSpyglasserClient::processInput)
	}

	private fun processInput(minecraft: Minecraft) {
		spyglassKeybind.consume {
			minecraft.player?.let { player ->
				if (canSpyglass(player)) {
					spyglass(player)
				}
			}
		}
	}

	private fun spyglass(player: LocalPlayer) {
		if (player.isSpyglassing) return

		logger.info("Spyglassing!")
//		Items.SPYGLASS.use(player.level, player, InteractionHand.MAIN_HAND)
	}

	private fun canSpyglass(player: LocalPlayer): Boolean {
		return true
	}
}
