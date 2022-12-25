package dev.gluton.quickspyglasser.fabric;

import dev.gluton.quickspyglasser.QuickSpyglasserExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class QuickSpyglasserExpectPlatformImpl {
    /**
     * This is our actual method to {@link QuickSpyglasserExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
