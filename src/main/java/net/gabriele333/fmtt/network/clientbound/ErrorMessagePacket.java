package net.gabriele333.fmtt.network.clientbound;

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

import net.gabriele333.gabrielecore.network.ClientboundPacket;
import net.gabriele333.gabrielecore.network.CustomPayload;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public record ErrorMessagePacket(String errorMessage) implements ClientboundPacket {

    public static final StreamCodec<RegistryFriendlyByteBuf, ErrorMessagePacket> STREAM_CODEC = StreamCodec.ofMember(
            ErrorMessagePacket::encode,
            ErrorMessagePacket::decode
    );


    public static final CustomPacketPayload.Type<ErrorMessagePacket> TYPE = CustomPayload.createType("error_message_packet");

    @Override
    public CustomPacketPayload.Type<ErrorMessagePacket> type() {
        return TYPE;
    }

    public static ErrorMessagePacket decode(RegistryFriendlyByteBuf buffer) {
        String errorMessage = buffer.readUtf(256);
        return new ErrorMessagePacket(errorMessage);
    }


    public void encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeUtf(this.errorMessage);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleOnClient(Player player) {
        player.sendSystemMessage(Component.literal(this.errorMessage));
    }
}
