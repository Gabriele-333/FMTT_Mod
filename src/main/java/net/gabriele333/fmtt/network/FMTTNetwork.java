package net.gabriele333.fmtt.network;


import net.gabriele333.fmtt.fmtt;

import net.gabriele333.fmtt.network.packet.FMTTXpC2SP;
//import net.gabriele333.fmtt.network.packet.FMTTXpRequestC2SP;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class FMTTNetwork {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(fmtt.MOD_ID, "fmtt_net"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(FMTTXpC2SP.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FMTTXpC2SP::new)
                .encoder(FMTTXpC2SP::toBytes)
                .consumerMainThread(FMTTXpC2SP::handle)
                .add();
        /*net.messageBuilder(FMTTXpRequestC2SP.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FMTTXpRequestC2SP::new)
                .encoder(FMTTXpRequestC2SP::toBytes)
                .consumerMainThread(FMTTXpRequestC2SP::handle)
                .add();
*/

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}

