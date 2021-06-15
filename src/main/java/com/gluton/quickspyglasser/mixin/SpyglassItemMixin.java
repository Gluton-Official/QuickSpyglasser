package com.gluton.quickspyglasser.mixin;

import com.gluton.quickspyglasser.QuickSpyglasserClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.SpyglassItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpyglassItem.class)
public class SpyglassItemMixin {
	@Inject(method = "getMaxUseTime", at = @At("RETURN"), cancellable = true)
	private void getMaxUseTimeInjector(ItemStack stack, CallbackInfoReturnable<Integer> maxUseTime) {
		maxUseTime.setReturnValue(72000);
	}

	@Redirect(method = "use",at = @At(value = "INVOKE",
			target = "Lnet/minecraft/item/ItemUsage;consumeHeldItem(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;"))
	private TypedActionResult<ItemStack> consumeHeldItemRedirector(World world, PlayerEntity user, Hand hand) {
		if (QuickSpyglasserClient.isUsingSpyglass && user instanceof ClientPlayerEntity) {
			return TypedActionResult.consume(QuickSpyglasserClient.spyglassInUse);
		} else {
			return ItemUsage.consumeHeldItem(world, user, hand);
		}
	}
}
