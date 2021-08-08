package com.gluton.quickspyglasser.config;

import com.gluton.quickspyglasser.QuickSpyglasser;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

@Config(name = QuickSpyglasser.MOD_ID + "-client")
@Environment(EnvType.CLIENT)
public class QuickSpyglasserClientConfig implements ConfigData {
    public boolean showSpyglassOverlay = true;
    public boolean playSpyglassSound = true;
    public boolean cinematicModeZoom = false;
    @ConfigEntry.Gui.Tooltip(count = 2)
    @ConfigEntry.BoundedDiscrete(max = 800)
    public int mouseSensitivity = 100;

    public static ConfigHolder<QuickSpyglasserClientConfig> init() {
        ConfigHolder<QuickSpyglasserClientConfig> configHolder = AutoConfig.register(QuickSpyglasserClientConfig.class, JanksonConfigSerializer::new);
        ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, resourceManager) -> configHolder.load());
        return configHolder;
    }

    @Environment(EnvType.CLIENT)
    public static class QuickSpyglasserModMenu implements ModMenuApi {
        @Override
        public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return screen -> AutoConfig.getConfigScreen(QuickSpyglasserClientConfig.class, screen).get();
        }
    }
}
