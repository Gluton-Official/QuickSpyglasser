package com.gluton.quickspyglasser.network;

import com.gluton.quickspyglasser.QuickSpyglasser;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class QuickSpyglasserNetwork {
    public static final Identifier SYNC_CONFIG = new Identifier(QuickSpyglasser.MOD_ID, "packets/config_sync");

    public static void init() {
        ServerLoginNetworking.registerGlobalReceiver(SYNC_CONFIG, (server, handler, understood, buf, sync, sender) -> {
            if (!understood) {
                QuickSpyglasser.LOGGER.debug("Player did not understand config sync packet");
            }
        });
        ServerLoginConnectionEvents.QUERY_START.register((handler, server, sender, sync) -> sender.sendPacket(SYNC_CONFIG, buildSpyglassItemBuffer()));
    }

    public static void syncRequiredItem(MinecraftServer server) {
        final PacketByteBuf buf = buildSpyglassItemBuffer();
        PlayerLookup.all(server).forEach(p -> ServerPlayNetworking.send(p, SYNC_CONFIG, buf));
    }

    private static PacketByteBuf buildSpyglassItemBuffer() {
        return PacketByteBufs.create().writeIdentifier(Registry.ITEM.getId(QuickSpyglasser.getInstance().getQSItem()));
    }
}
