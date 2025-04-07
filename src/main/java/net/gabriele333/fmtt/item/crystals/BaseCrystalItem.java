package net.gabriele333.fmtt.item.crystals;/*
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




import net.gabriele333.fmtt.network.serverbound.SpawnCrystalPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;


import java.util.List;


public abstract class BaseCrystalItem extends Item {
    protected final String crystalType;

    public BaseCrystalItem(Properties properties, String crystalType) {
        super(properties);
        this.crystalType = crystalType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        ItemStack itemStack = context.getItemInHand();

        if (hasEntity(itemStack)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide && context.getPlayer() instanceof ServerPlayer serverPlayer) {
            BlockPos pos = context.getClickedPos().above();
            PacketDistributor.sendToServer(new SpawnCrystalPacket(pos, this.crystalType, itemStack));
            setEntity(itemStack, true);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public static boolean hasEntity(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        return data != null && data.copyTag().getBoolean("HasEntity");
    }

    public static void setEntity(ItemStack stack, boolean hasEntity) {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        tag.putBoolean("HasEntity", hasEntity);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }
}