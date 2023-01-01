package dev.gluton.quickspyglasser.client

import net.minecraft.client.KeyMapping

fun KeyMapping.consume(action: KeyMapping.() -> Unit) {
	while (consumeClick()) action()
}
