package com.gluton.quickspyglasser.network;

import com.gluton.quickspyglasser.QuickSpyglasser;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class QuickSpyglasserNetwork {
    public static final Identifier SYNC_CONFIG = new Identifier(QuickSpyglasser.MOD_ID, "packets/config_sync");
    public static final Identifier USE_SPYGLASS_TRINKET = new Identifier(QuickSpyglasser.MOD_ID, "packets/use_spyglass_trinket");

    public static void init() {
        ServerLoginNetworking.registerGlobalReceiver(SYNC_CONFIG, (server, handler, understood, buf, sync, sender) -> {
            QuickSpyglasser.LOGGER.debug("Received confirmation packet from player");
            if (!understood) {
                QuickSpyglasser.LOGGER.debug("Player did not understand config sync packet");
            }
        });
//        ServerLoginNetworking.registerGlobalReceiver(USE_SPYGLASS_TRINKET, (server, handler, understood, buf, sync, sender) -> {
//            QuickSpyglasser.LOGGER.debug("Received use packet, distributing...");
//            updateSpyglassTrinketUse(server, buf);
//        });
//        ServerLoginConnectionEvents.QUERY_START.register((handler, server, sender, sync) -> sender.sendPacket(SYNC_CONFIG, buildSpyglassItemByteBuf()));
    }

    public static void updateSpyglassTrinketUse(MinecraftServer server, PacketByteBuf buf) {
        // TODO: rather than sending an update packet to the clients for them to store the use state, store a spyglass use state on server
        // TODO: actually, do whatever the game already does for rendering spyglass use since Player.isUsingSpyglass() is already used
    }

    public static void syncRequiredItem(MinecraftServer server) {
        final PacketByteBuf buf = buildSpyglassItemByteBuf();
        PlayerLookup.all(server).forEach(p -> ServerPlayNetworking.send(p, SYNC_CONFIG, buf));
    }

    private static PacketByteBuf buildSpyglassItemByteBuf() {
        return PacketByteBufs.create().writeIdentifier(Registry.ITEM.getId(QuickSpyglasser.getInstance().getQSItem()));
    }
}
