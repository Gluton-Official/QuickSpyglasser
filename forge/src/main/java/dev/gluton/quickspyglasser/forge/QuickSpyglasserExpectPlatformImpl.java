package dev.gluton.quickspyglasser.forge;

import dev.gluton.quickspyglasser.QuickSpyglasserExpectPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class QuickSpyglasserExpectPlatformImpl {
    /**
     * This is our actual method to {@link QuickSpyglasserExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
