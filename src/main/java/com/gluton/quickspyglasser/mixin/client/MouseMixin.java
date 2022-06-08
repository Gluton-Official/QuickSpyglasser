package com.gluton.quickspyglasser.mixin.client;

import com.gluton.quickspyglasser.QuickSpyglasserClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mouse.class)
public class MouseMixin {
    @Redirect(method = "updateMouse", at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/option/GameOptions;smoothCameraEnabled:Z"))
    private boolean smoothCameraEnabledRedirector(GameOptions gameOptions) {
        return gameOptions.smoothCameraEnabled || QuickSpyglasserClient.getInstance().shouldSmoothCamera();
    }

    @ModifyArg(method = "updateMouse", index = 0, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/util/SmoothUtil;smooth(DD)D"))
    private double smoothRedirector(double original) {
        var qsClient = QuickSpyglasserClient.getInstance();
        return qsClient.shouldSmoothCamera() ?
                (original / 8) * qsClient.getMouseScale() : original;
    }

    @ModifyVariable(method = "updateMouse", /* name = "g" */ ordinal = 2,
            at = @At(value = "FIELD", shift = At.Shift.BEFORE,
                    target = "Lnet/minecraft/client/option/GameOptions;smoothCameraEnabled:Z"))
    private double modifyMouseSensitivityScale(double g) {
        if (MinecraftClient.getInstance().player == null) return g;
        return g * QuickSpyglasserClient.getInstance().getMouseScale();
    }
}
