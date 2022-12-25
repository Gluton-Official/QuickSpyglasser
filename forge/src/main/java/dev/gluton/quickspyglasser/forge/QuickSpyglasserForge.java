package dev.gluton.quickspyglasser.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.gluton.quickspyglasser.QuickSpyglasser;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(QuickSpyglasser.MOD_ID)
public class QuickSpyglasserForge {
    public QuickSpyglasserForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(QuickSpyglasser.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        QuickSpyglasser.init();
    }
}
