package com.gluton.quickspyglasser.config;

import com.gluton.quickspyglasser.QuickSpyglasser;
import com.gluton.quickspyglasser.network.QuickSpyglasserNetwork;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Config(name = QuickSpyglasser.MOD_ID)
public class QuickSpyglasserConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip(count = 3)
    public String quickSpyglassItemId = "minecraft:spyglass";

    public static ConfigHolder<QuickSpyglasserConfig> init() {
        ConfigHolder<QuickSpyglasserConfig> configHolder = AutoConfig.register(QuickSpyglasserConfig.class, JanksonConfigSerializer::new);

        ServerLifecycleEvents.SERVER_STARTED.register((server) -> updateRequiredItem(configHolder));
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, resourceManager) -> {
            configHolder.load();
            updateRequiredItem(configHolder);
            QuickSpyglasserNetwork.syncRequiredItem(server);
        });

        QuickSpyglasser.oldQuickSpyglassItemId = configHolder.getConfig().quickSpyglassItemId;

        return configHolder;
    }

    private static void updateRequiredItem(ConfigHolder<QuickSpyglasserConfig> configHolder) {
        QuickSpyglasser qs = QuickSpyglasser.getInstance();
        QuickSpyglasserConfig config = configHolder.getConfig();

        // empty string, no item required
        if (config.quickSpyglassItemId.isBlank()) {
            qs.setQSItem(Items.AIR);
            return;
        }

        Identifier itemId = Identifier.tryParse(config.quickSpyglassItemId);
        // valid identifier
        if (itemId != null) {
            Item item = Registry.ITEM.get(itemId);
            // valid item id
            if (Registry.ITEM.getId(item).toString().equalsIgnoreCase(config.quickSpyglassItemId)) {
                qs.setQSItem(item);
                return;
            }
        }

        // if failed updating item, save/write the previous value to config
        config.quickSpyglassItemId = QuickSpyglasser.oldQuickSpyglassItemId;
        configHolder.save();
    }
}
