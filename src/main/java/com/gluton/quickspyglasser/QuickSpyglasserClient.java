package com.gluton.quickspyglasser;

import com.gluton.quickspyglasser.compat.trinket.SpyglassTrinket;
import com.gluton.quickspyglasser.config.QuickSpyglasserConfig;
import com.gluton.quickspyglasser.mixin.PlayerInventoryAccessor;
import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpyglassItem;
import net.minecraft.util.Arm;
import org.lwjgl.glfw.GLFW;

import java.util.List;

import static com.gluton.quickspyglasser.QuickSpyglasser.isTrinketsPresent;

public class QuickSpyglasserClient implements ClientModInitializer {

	public static final ConfigHolder<QuickSpyglasserConfig> CONFIG = QuickSpyglasserConfig.init();

	public static boolean isUsingSpyglass = false;
	public static ItemStack spyglassInUse;
	public static Item quickSpyglassItem;
	private static KeyBinding spyglassKeybind;

	@Override
	public void onInitializeClient() {
		spyglassKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.quickspyglasser.use", InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_C, "category.quickspyglasser.spyglass"));
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null) return;

			if (isUsingSpyglass)
				if (!spyglassKeybind.isPressed() || (spyglassInUse.isEmpty() && !client.player.getInventory().contains(spyglassInUse)))
					stopUsingSpyglass(client);
			while (spyglassKeybind.wasPressed())
				if (!isUsingSpyglass)
					useSpyglass(client);
		});
	}

	private void useSpyglass(MinecraftClient client) {
		if (quickSpyglassItem == null || quickSpyglassItem == Items.AIR) {
			// no item required
			spyglassInUse = Items.SPYGLASS.getDefaultStack();
		} else if (quickSpyglassItem instanceof SpyglassItem) {
			// spyglass item required
			if (isTrinketsPresent) {
				spyglassInUse = SpyglassTrinket.getEquippedTrinket(client.player);
			}
			// if no spyglass trinket was found
			if (spyglassInUse == null) {
				spyglassInUse = getSimilarStack(client.player.getInventory(), quickSpyglassItem.getDefaultStack());
			}
			// if no spyglass was found in the player's inventory
			if (spyglassInUse == null) return;
		} else if (client.player.getInventory().contains(quickSpyglassItem.getDefaultStack())) {
			// non-spyglass item required
			spyglassInUse = Items.SPYGLASS.getDefaultStack();
		} else {
			return;
		}
		isUsingSpyglass = true;
		spyglassInUse.use(client.world, client.player, null);
	}

	private void stopUsingSpyglass(MinecraftClient client) {
		spyglassInUse.onStoppedUsing(client.world, client.player, 0);
		isUsingSpyglass = false;
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

	public static boolean shouldPlayUseSound() {
		return !isUsingSpyglass || CONFIG.getConfig().playSpyglassSound;
	}

	public static boolean shouldRenderOverlay() {
		return !isUsingSpyglass || CONFIG.getConfig().showSpyglassOverlay;
	}

	public static boolean shouldSmoothCamera() {
		return isUsingSpyglass && CONFIG.getConfig().cinematicModeZoom;
	}

	public static float getMouseScale() {
		return isUsingSpyglass ? CONFIG.getConfig().mouseSensitivity / 100.0F : 1.0F;
	}

	public static boolean rightHandedSpyglass(AbstractClientPlayerEntity player) {
		return player.getMainArm() == Arm.RIGHT && player.getStackInHand(player.getActiveHand()).isEmpty();
	}
}
