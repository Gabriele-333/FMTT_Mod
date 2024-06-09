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
package net.gabriele333.fmtt.item;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FMTTCompassItem extends Item {
    public FMTTCompassItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isClientSide && entity instanceof Player) {
            updateCompass(stack, world, (Player) entity);
        }
    }

    private void updateCompass(ItemStack stack, Level world, Player player) {
        if (stack.getItem() == this) {
            CompoundTag nbt = stack.getOrCreateTag();
            double angle = calculateCompassAngle(world, player);
            nbt.putDouble("CompassAngle", angle);
            stack.setTag(nbt);
        }
    }

    private double calculateCompassAngle(Level world, Player player) {
        // Calcola l'angolo verso il punto di spawn del mondo o un punto fisso
        BlockPos spawnPos = world.getSharedSpawnPos();
        double dx = spawnPos.getX() - player.getX();
        double dz = spawnPos.getZ() - player.getZ();
        return Math.toDegrees(Math.atan2(dz, dx));
    }
}
