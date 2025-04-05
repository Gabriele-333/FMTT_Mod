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
  *
 *
 * THIS CODE IS COPIED AND MODIFIED FROM: https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/core/network/InitNetwork.java
 */
package net.gabriele333.fmtt.network;


import net.gabriele333.fmtt.network.clientbound.CompassResponsePacket;
import net.gabriele333.fmtt.network.clientbound.ErrorMessagePacket;
import net.gabriele333.fmtt.network.serverbound.CompassRequestPacket;
import net.gabriele333.fmtt.network.serverbound.FMTTRewardPacket;
import net.gabriele333.fmtt.network.serverbound.FMTTXpPacket;
import net.gabriele333.fmtt.network.serverbound.SpawnCrystalPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static net.gabriele333.fmtt.fmtt.LOGGER;
import static net.gabriele333.fmtt.fmtt.MOD_ID;


public class FMTTNetwork {
    public static void init(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(MOD_ID);

        // Clientbound
        clientbound(registrar, CompassResponsePacket.TYPE, CompassResponsePacket.STREAM_CODEC);
        clientbound(registrar, ErrorMessagePacket.TYPE, ErrorMessagePacket.STREAM_CODEC);


        // Serverbound
        serverbound(registrar, FMTTXpPacket.TYPE, FMTTXpPacket.STREAM_CODEC);
        serverbound(registrar, FMTTRewardPacket.TYPE, FMTTRewardPacket.STREAM_CODEC);
        serverbound(registrar, CompassRequestPacket.TYPE, CompassRequestPacket.STREAM_CODEC);
        serverbound(registrar, SpawnCrystalPacket.TYPE, SpawnCrystalPacket.STREAM_CODEC);

    }

    private static <T extends ClientboundPacket> void clientbound(PayloadRegistrar registrar,
                                                                  CustomPacketPayload.Type<T> type,
                                                                  StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        registrar.playToClient(type, codec, ClientboundPacket::handleOnClient);
    }

    private static <T extends ServerboundPacket> void serverbound(PayloadRegistrar registrar,
                                                                  CustomPacketPayload.Type<T> type,
                                                                  StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        registrar.playToServer(type, codec, ServerboundPacket::handleOnServer);
        LOGGER.info("Belin 2");
    }

}