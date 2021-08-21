package com.gluton.quickspyglasser;

import com.gluton.quickspyglasser.command.QuickSpyglasserCommand;
import com.gluton.quickspyglasser.compat.trinket.SpyglassTrinket;
import com.gluton.quickspyglasser.config.QuickSpyglasserConfig;
import com.gluton.quickspyglasser.network.QuickSpyglasserNetwork;
import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuickSpyglasser implements ModInitializer {

    public static final String MOD_ID = "quickspyglasser";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ConfigHolder<QuickSpyglasserConfig> CONFIG = QuickSpyglasserConfig.init();

    public static String oldQuickSpyglassItemId;

    private static QuickSpyglasser instance;

    private boolean isTrinketsPresent;
    private Item quickSpyglassItem;


    @Override
    public void onInitialize() {
        instance = this;
        isTrinketsPresent = FabricLoader.getInstance().isModLoaded("trinkets");
        if (isTrinketsPresent) {
            SpyglassTrinket.register();
        }
        QuickSpyglasserNetwork.init();
        QuickSpyglasserCommand.init();
    }

    public static QuickSpyglasser getInstance() {
        return instance;
    }

    public boolean isTrinketsPresent() {
        return this.isTrinketsPresent;
    }

    public void setQSItem(Item item) {
        LOGGER.info("Set item to {}", Registry.ITEM.getId(item));
        this.quickSpyglassItem = item;
        QuickSpyglasser.oldQuickSpyglassItemId = Registry.ITEM.getId(item).toString();
    }

    public Item getQSItem() {
        return this.quickSpyglassItem;
    }
}
