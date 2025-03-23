package net.gabriele333.fmtt.network.serverbound;/*
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




import net.gabriele333.fmtt.item.FMTTItems;
import net.gabriele333.fmtt.network.CustomFMTTPayload;
import net.gabriele333.fmtt.network.ServerboundPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;


import static net.gabriele333.fmtt.FMTTXP.PlayerFMTTXpProvider.PLAYERFMTTXP;
import static net.gabriele333.fmtt.fmtt.LOGGER;

public record FMTTXpPacket() implements ServerboundPacket {

    public static final StreamCodec<RegistryFriendlyByteBuf, FMTTXpPacket> STREAM_CODEC = StreamCodec
            .ofMember(
                    FMTTXpPacket::write,
                    FMTTXpPacket::decode);

    public static final Type<FMTTXpPacket> TYPE = CustomFMTTPayload.createType("fmtt_xp_packet");

    @Override
    public Type<FMTTXpPacket> type() {
        return TYPE;
    }
    public static FMTTXpPacket decode(RegistryFriendlyByteBuf stream) {
        return new FMTTXpPacket();
    }

    public void write(RegistryFriendlyByteBuf data) {
    }




    public void handleOnServer(ServerPlayer player) {
        assert player != null;
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem() == FMTTItems.FMTT_XP_ITEM.get()) {
            itemStack.shrink(1);
        }

        int currentXp = player.getData(PLAYERFMTTXP);
        player.setData(PLAYERFMTTXP, currentXp + 1);
        LOGGER.info("XP incremented"); // Optional: Confirm increment
    }

}
