package dev.gluton.quickspyglasser

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.SpyglassItem

val Player.isSpyglassing: Boolean get() = isScoping || isUsingSpyglassItem
val Player.isUsingSpyglassItem: Boolean get() = isUsingItem && useItem.item is SpyglassItem
