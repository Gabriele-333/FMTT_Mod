/*
 * This file is part of From Magic To Tech.
 * Copyright (c) 2024, Gabriele_333, All rights reserved.
 *
 * From Magic To Tech is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * From Magic To Tech is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with From Magic To Tech.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package net.gabriele333.fmtt.network;


import net.gabriele333.fmtt.fmtt;

import net.gabriele333.fmtt.network.packet.FMTTXpC2SP;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;


public class FMTTNetwork {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    public static int id() {
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

