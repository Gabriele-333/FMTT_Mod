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
 * File created on: 05/04/2025
 */

import net.gabriele333.fmtt.entity.FMTTEntities;
import net.gabriele333.fmtt.entity.crystals.CosmosCrystal;
import net.gabriele333.fmtt.entity.crystals.TimeCrystal;
import net.gabriele333.fmtt.item.crystals.BaseCrystalItem;
import net.gabriele333.fmtt.network.CustomFMTTPayload;
import net.gabriele333.fmtt.network.ServerboundPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public record SpawnCrystalPacket(BlockPos pos, String crystalType, ItemStack itemStack) implements ServerboundPacket {
    public static final StreamCodec<RegistryFriendlyByteBuf, SpawnCrystalPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            SpawnCrystalPacket::pos,
            ByteBufCodecs.STRING_UTF8,
            SpawnCrystalPacket::crystalType,
            ItemStack.OPTIONAL_STREAM_CODEC,
            SpawnCrystalPacket::itemStack,
            SpawnCrystalPacket::new
    );

    public static final Type<SpawnCrystalPacket> TYPE = CustomFMTTPayload.createType("spawn_time_crystal");

    @Override
    public Type<SpawnCrystalPacket> type() {
        return TYPE;
    }

    @Override
    public void handleOnServer(ServerPlayer player) {
        // Qui puoi usare crystalType per spawnare l'entità corretta
        switch (crystalType) {
            case "time" -> spawnTimeCrystal(player);
            case "cosmos" -> spawnCosmosCrystal(player);
            default -> throw new IllegalArgumentException("Unknown crystal type: " + crystalType);
        }
    }

    private void spawnTimeCrystal(ServerPlayer player) {
        TimeCrystal entity = new TimeCrystal(FMTTEntities.TIME_CRYSTAL.get(), player.level());
        entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        entity.setOwner(player);
        entity.setItemStack(itemStack);

        player.level().addFreshEntity(entity);

        BaseCrystalItem.setEntity(itemStack, true);

        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
    }
    private void spawnCosmosCrystal(ServerPlayer player) {
        CosmosCrystal entity = new CosmosCrystal(FMTTEntities.COSMOS_CRYSTAL.get(), player.level());
        entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        entity.setOwner(player);
        entity.setItemStack(itemStack);

        player.level().addFreshEntity(entity);

        BaseCrystalItem.setEntity(itemStack, true);

        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
    }
    //FIXME: mancano gli altri cristalli
}