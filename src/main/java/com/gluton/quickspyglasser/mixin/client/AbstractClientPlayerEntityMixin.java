package com.gluton.quickspyglasser.mixin.client;

import com.gluton.quickspyglasser.QuickSpyglasserClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin {
    @Redirect(method = "getSpeed", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;isUsingSpyglass()Z"))
    private boolean shouldSpyglassSlow(AbstractClientPlayerEntity player) {
        return !QuickSpyglasserClient.getInstance().isUsingSpyglass() && player.isUsingItem() && player.getActiveItem().isOf(Items.SPYGLASS);
    }

    @Redirect(method = "getSpeed", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"))
    private float handleSpyglassScale(float delta, float start, float end) {
        float spyglassScale = QuickSpyglasserClient.getInstance().isUsingSpyglass() ? start * 0.1F : start;
        return MathHelper.lerp(delta, spyglassScale, end * spyglassScale);
    }
}

