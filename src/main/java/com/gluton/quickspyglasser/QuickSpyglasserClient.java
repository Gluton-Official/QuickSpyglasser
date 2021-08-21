package com.gluton.quickspyglasser;

import com.gluton.quickspyglasser.compat.trinket.SpyglassTrinket;
import com.gluton.quickspyglasser.config.QuickSpyglasserClientConfig;
import com.gluton.quickspyglasser.mixin.PlayerInventoryAccessor;
import com.gluton.quickspyglasser.network.QuickSpyglasserClientNetwork;
import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpyglassItem;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class QuickSpyglasserClient implements ClientModInitializer {

	public static final ConfigHolder<QuickSpyglasserClientConfig> CONFIG = QuickSpyglasserClientConfig.init();

	private static QuickSpyglasserClient instance;

	private boolean isUsingSpyglass;
	private ItemStack spyglassInUse;
	private Item quickSpyglassItem;
	private KeyBinding spyglassKeybind;

	@Override
	public void onInitializeClient() {
		instance = this;

		this.spyglassKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key." + QuickSpyglasser.MOD_ID + ".use", InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_C, "category" + QuickSpyglasser.MOD_ID + "spyglass"));
		ClientTickEvents.END_CLIENT_TICK.register(this::onEndTick);
		QuickSpyglasserClientNetwork.init();
	}

	public static QuickSpyglasserClient getInstance() {
		return instance;
	}

	private void onEndTick(MinecraftClient client) {
		if (client.player == null) return;

		while (this.spyglassKeybind.wasPressed() && !this.isUsingSpyglass) {
			useSpyglass(client);
		}
		if (this.isUsingSpyglass && !this.spyglassKeybind.wasPressed() && !this.spyglassKeybind.isPressed()
				|| (this.spyglassInUse != null && this.spyglassInUse.isEmpty()
						&& !client.player.getInventory().contains(this.spyglassInUse))) {
			stopUsingSpyglass(client);
		}
	}

	private void useSpyglass(@NotNull MinecraftClient client) {
		setSpyglassInUse(client.player);
		if (this.spyglassInUse == null) return;
		this.isUsingSpyglass = true;
		this.spyglassInUse.use(client.world, client.player, Hand.MAIN_HAND);
	}

	private void setSpyglassInUse(ClientPlayerEntity player) {
		// no item required, use default spyglass
		if (this.quickSpyglassItem == null || this.quickSpyglassItem == Items.AIR) {
			setSpyglassInUse(Items.SPYGLASS.getDefaultStack());
		// spyglass item required, use spyglass in inventory
		} else if (this.quickSpyglassItem instanceof SpyglassItem) {
			// get trinket
			if (QuickSpyglasser.getInstance().isTrinketsPresent()) {
				spyglassInUse = SpyglassTrinket.getEquippedTrinket(player);
			}
			// if no spyglass trinket was found, search inventory for spyglass
			if (this.spyglassInUse == null) {
				setSpyglassInUse(getSimilarStack(player.getInventory(), this.quickSpyglassItem.getDefaultStack()));
			}
			// if no spyglass was found in the player's inventory, spyglassInUse will still be null
		// non-spyglass item required, use default spyglass
		} else if (player.getInventory().contains(this.quickSpyglassItem.getDefaultStack())) {
			setSpyglassInUse(Items.SPYGLASS.getDefaultStack());
		}
	}

	private void stopUsingSpyglass(@NotNull MinecraftClient client) {
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

	public boolean shouldPlayUseSound() {
		return !isUsingSpyglass || CONFIG.getConfig().playSpyglassSound;
	}

	public boolean shouldRenderOverlay() {
		return !isUsingSpyglass || CONFIG.getConfig().showSpyglassOverlay;
	}

	public boolean shouldSmoothCamera() {
		return isUsingSpyglass && CONFIG.getConfig().cinematicModeZoom;
	}

	public float getMouseScale() {
		return isUsingSpyglass ? CONFIG.getConfig().mouseSensitivity / 100.0F : 1.0F;
	}

	public boolean isUsingSpyglass() {
		return this.isUsingSpyglass;
	}

	public void setQSItem(Item item) {
		this.quickSpyglassItem = item;
	}

	public Item getQSItem() {
		return this.quickSpyglassItem;
	}

	public void setSpyglassInUse(ItemStack stack) {
		this.spyglassInUse = stack;
	}

	public ItemStack getSpyglassInUse() {
		return this.spyglassInUse;
	}

	public static boolean rightHandedSpyglass(@NotNull AbstractClientPlayerEntity player) {
		return player.getMainArm() == Arm.RIGHT && player.getStackInHand(player.getActiveHand()).isEmpty();
	}
}
