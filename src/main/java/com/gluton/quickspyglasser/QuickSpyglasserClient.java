package com.gluton.quickspyglasser;

import com.gluton.quickspyglasser.mixin.PlayerInventoryAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import org.lwjgl.glfw.GLFW;

import java.util.Iterator;
import java.util.List;

public class QuickSpyglasserClient implements ClientModInitializer {

	public static boolean isUsingSpyglass = false;
	public static ItemStack spyglassInUse;
	private static KeyBinding keySpyglass;

	@Override
	public void onInitializeClient() {
		keySpyglass = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.quickspyglasser.use", InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_C, "category.quickspyglasser.spyglass"));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null) return;

			if (isUsingSpyglass)
				if (!keySpyglass.isPressed() || !client.player.getInventory().contains(spyglassInUse))
					stopUsingSpyglass(client);
			while (keySpyglass.wasPressed())
				if (!isUsingSpyglass)
					useSpyglass(client);
		});
	}

	private void useSpyglass(MinecraftClient client) {
		ItemStack spyglass = getSimilarStack(client.player.getInventory(), Items.SPYGLASS.getDefaultStack());
		if (spyglass != null) {
			isUsingSpyglass = true;
			spyglassInUse = spyglass;
			Items.SPYGLASS.use(client.world, client.player, null);
		}
	}

	private void stopUsingSpyglass(MinecraftClient client) {
		isUsingSpyglass = false;
		spyglassInUse.onStoppedUsing(client.world, client.player, 0);
		spyglassInUse = null;
	}

	private static ItemStack getSimilarStack(PlayerInventory inventory, ItemStack itemStack) {
		if (inventory == null) return null;
		for (List<ItemStack> list : ((PlayerInventoryAccessor) inventory).getCombinedInventory()) {
			for (ItemStack stack : list) {
				if (!stack.isEmpty() && stack.isItemEqualIgnoreDamage(itemStack)) {
					return stack;
				}
			}
		}
		return null;
	}

	public static boolean rightHandedSpyglass(AbstractClientPlayerEntity player) {
		return player.getMainArm() == Arm.RIGHT && player.getStackInHand(player.getActiveHand()).isEmpty();
	}
}
