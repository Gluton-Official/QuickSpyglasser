package com.gluton.quickspyglasser.mixin;

import com.gluton.quickspyglasser.QuickSpyglasserClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mouse.class)
public class MouseMixin {
    @Redirect(method = "updateMouse", at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/option/GameOptions;smoothCameraEnabled:Z"))
    private boolean smoothCameraEnabledRedirector(GameOptions gameOptions) {
        return gameOptions.smoothCameraEnabled || QuickSpyglasserClient.shouldSmoothCamera();
    }

    @ModifyArg(method = "updateMouse", index = 0, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/util/SmoothUtil;smooth(DD)D"))
    private double smoothRedirector(double original) {
        return QuickSpyglasserClient.shouldSmoothCamera() ? original / 8 : original;
    }
}
