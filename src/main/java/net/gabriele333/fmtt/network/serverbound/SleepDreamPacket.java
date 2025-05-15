package net.gabriele333.fmtt.network.serverbound;/*
 * This file is part of From Magic To Tech.
 * Copyright (c) 2025, Gabriele_333, All rights reserved.
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
 * File created on: 14/05/2025
 */

import net.gabriele333.fmtt.Attachments.Attachements;
import net.gabriele333.fmtt.network.ClientboundPacket;
import net.gabriele333.fmtt.network.CustomFMTTPayload;
import net.gabriele333.fmtt.network.ServerboundPacket;
import net.gabriele333.fmtt.network.clientbound.SleepDreamResponsePacket;
import net.gabriele333.fmtt.util.ftbquest.FTBQuestTrigger;
import net.gabriele333.fmtt.util.ftbquest.QuestID;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import static net.gabriele333.fmtt.Attachments.Attachements.SLEEP_DREAM;
import static net.gabriele333.fmtt.fmtt.LOGGER;

public record SleepDreamPacket() implements ServerboundPacket {

    public static final StreamCodec<RegistryFriendlyByteBuf, SleepDreamPacket> STREAM_CODEC = StreamCodec
            .ofMember(
                    SleepDreamPacket::write,
                    SleepDreamPacket::decode);

    public static final Type<SleepDreamPacket> TYPE = CustomFMTTPayload.createType("sleep_dream_packet");

    @Override
    public Type<SleepDreamPacket> type() {
        return TYPE;
    }
    public static SleepDreamPacket decode(RegistryFriendlyByteBuf stream) {

        return new SleepDreamPacket();
    }

    public void write(RegistryFriendlyByteBuf data) {
    }

    public void handleOnServer(ServerPlayer player) {
        float dreamLevel = player.getData(Attachements.SLEEP_DREAM.get());
        float probability;

        LOGGER.info("Current dream level (float): {}", dreamLevel);

        if (dreamLevel == 0f) {  // Confronta con 0f invece di 0
            probability = 0.5f;
        } else {
            probability = 0.01f;
        }

        if (dreamLevel == 0f) {
            player.setData(Attachements.SLEEP_DREAM.get(), 1f);  // Imposta 1f
        }
        FTBQuestTrigger.triggerQuest(player, QuestID.FMTTIDS[0]);

        if (player.getRandom().nextFloat() < probability) {
            ClientboundPacket response = new SleepDreamResponsePacket();  // Aggiungi parametro
            PacketDistributor.sendToPlayer(player, response);
        }
    }
}
