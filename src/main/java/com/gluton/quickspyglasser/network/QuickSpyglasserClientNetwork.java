package com.gluton.quickspyglasser.network;

import com.gluton.quickspyglasser.QuickSpyglasser;
import com.gluton.quickspyglasser.QuickSpyglasserClient;
import io.netty.handler.codec.DecoderException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;

import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
public class QuickSpyglasserClientNetwork {
    public static void init() {
        ClientLoginNetworking.registerGlobalReceiver(QuickSpyglasserNetwork.SYNC_CONFIG, (client, handler, buf, sender) -> receiveSyncConfigPacket(buf));
        ClientPlayNetworking.registerGlobalReceiver(QuickSpyglasserNetwork.SYNC_CONFIG, (client, handler, buf, sender) -> receiveSyncConfigPacket(buf));
//        ClientPlayNetworking.registerGlobalReceiver(QuickSpyglasserNetwork.USE_SPYGLASS_TRINKET, (client, handler, buf, responseSender) -> receiveUseSpyglassTrinketPacket(buf));
    }

    private static CompletableFuture<PacketByteBuf> receiveSyncConfigPacket(PacketByteBuf buf) {
        try {
            Registry.ITEM.getOrEmpty(buf.readIdentifier()).ifPresentOrElse(QuickSpyglasserClient.getInstance()::setQSItem,
                    () -> QuickSpyglasser.LOGGER.error("Server sent invalid item id!"));
            return CompletableFuture.completedFuture(PacketByteBufs.empty());
        } catch (DecoderException e) {
            QuickSpyglasser.LOGGER.error("Failed reading item id from buffer", e);
            return CompletableFuture.failedFuture(e);
        }
    }

//    private static CompletableFuture<PacketByteBuf> receiveUseSpyglassTrinketPacket(PacketByteBuf buf) {
//
//    }

    public static void sendUseSpyglassTrinketPacket(boolean using) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeUuid(MinecraftClient.getInstance().player.getUuid());
        buf.writeBoolean(using);
        ClientPlayNetworking.send(QuickSpyglasserNetwork.USE_SPYGLASS_TRINKET, buf);
    }
}
