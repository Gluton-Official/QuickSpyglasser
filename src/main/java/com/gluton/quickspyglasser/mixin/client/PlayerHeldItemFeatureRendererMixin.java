package com.gluton.quickspyglasser.mixin.client;

import com.gluton.quickspyglasser.QuickSpyglasserClient;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerHeldItemFeatureRenderer.class)
public class PlayerHeldItemFeatureRendererMixin {
	@Redirect(method = "renderItem", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/entity/LivingEntity;getActiveItem()Lnet/minecraft/item/ItemStack;"))
	private ItemStack getActiveItemRedirector(LivingEntity entity) {
		if (QuickSpyglasserClient.getInstance().isUsingSpyglass()) {
			return QuickSpyglasserClient.getInstance().getSpyglassInUse();
		}
		return entity.getActiveItem();
	}
}
