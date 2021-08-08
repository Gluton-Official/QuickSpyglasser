package com.gluton.quickspyglasser.network;

import com.gluton.quickspyglasser.QuickSpyglasser;
import com.gluton.quickspyglasser.QuickSpyglasserClient;
import io.netty.handler.codec.DecoderException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;

import java.util.concurrent.CompletableFuture;

@Environment(EnvType.CLIENT)
public class QuickSpyglasserClientNetwork {
    public static void init() {
        ClientLoginNetworking.registerGlobalReceiver(QuickSpyglasserNetwork.SYNC_CONFIG, (client, handler, buf, sender) -> receive(buf));
        ClientPlayNetworking.registerGlobalReceiver(QuickSpyglasserNetwork.SYNC_CONFIG, (client, handler, buf, sender) -> receive(buf));
    }

    private static CompletableFuture<PacketByteBuf> receive(PacketByteBuf buf) {
        try {
            Registry.ITEM.getOrEmpty(buf.readIdentifier()).ifPresentOrElse(QuickSpyglasserClient.getInstance()::setQSItem,
                    () -> QuickSpyglasser.LOGGER.error("Server sent invalid item id!"));
            return CompletableFuture.completedFuture(PacketByteBufs.empty());
        } catch (DecoderException e) {
            QuickSpyglasser.LOGGER.error("Failed reading item id from buffer", e);
            return CompletableFuture.failedFuture(e);
        }
    }
}
