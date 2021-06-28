package com.gluton.quickspyglasser;

import com.gluton.quickspyglasser.compat.trinket.SpyglassTrinket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class QuickSpyglasser implements ModInitializer {
    public static boolean isTrinketsPresent = false;

    @Override
    public void onInitialize() {
        isTrinketsPresent = FabricLoader.getInstance().isModLoaded("trinkets");
        if (isTrinketsPresent) {
            SpyglassTrinket.regsiter();
        }
    }
}
