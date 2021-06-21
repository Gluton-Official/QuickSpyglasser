package com.gluton.quickspyglasser.mixin;

import com.gluton.quickspyglasser.QuickSpyglasserClient;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "renderSpyglassOverlay", at = @At("HEAD"), cancellable = true)
    private void renderSpyglassOverlayInjector(CallbackInfo info) {
        if (!QuickSpyglasserClient.shouldRenderOverlay()) {
            info.cancel();
        }
    }
}
