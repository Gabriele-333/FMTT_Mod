package net.gabriele333.fmtt.network.clientbound;/*
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
 * File created on: 15/05/2025
 */


import net.gabriele333.fmtt.event.ClientModEvents;
import net.gabriele333.gabrielecore.network.ClientboundPacket;
import net.gabriele333.gabrielecore.network.CustomPayload;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public record SleepDreamResponsePacket() implements ClientboundPacket {
    public static final StreamCodec<RegistryFriendlyByteBuf, SleepDreamResponsePacket> STREAM_CODEC = StreamCodec.ofMember(
            SleepDreamResponsePacket::write,
            SleepDreamResponsePacket::decode);

    public static final Type<SleepDreamResponsePacket> TYPE = CustomPayload.createType("sleep_dream_response");


    @Override
    public Type<SleepDreamResponsePacket> type() {
        return TYPE;
    }

    public static SleepDreamResponsePacket decode(RegistryFriendlyByteBuf stream) {

        return new SleepDreamResponsePacket();
    }

    public void write(RegistryFriendlyByteBuf data) {

    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleOnClient(Player player) {

        ClientModEvents.setShowSleepImage(true);
        ClientModEvents.resetFadeTimer();

    }

}
