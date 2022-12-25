package dev.gluton.quickspyglasser.fabric;

import dev.gluton.quickspyglasser.QuickSpyglasser;
import net.fabricmc.api.ModInitializer;

public class QuickSpyglasserFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        QuickSpyglasser.init();
    }
}
