package com.gluton.quickspyglasser.mixin;

import com.gluton.quickspyglasser.QuickSpyglasserClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "isUsingSpyglass", at = @At("RETURN"), cancellable = true)
    private void isUsingSpyglassInjector(CallbackInfoReturnable<Boolean> isUsingSpyglass) {
        if (QuickSpyglasserClient.isUsingSpyglass) {
            isUsingSpyglass.setReturnValue(true);
        }
    }
}
