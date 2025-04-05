package net.gabriele333.fmtt.entity.crystals;/*
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


import net.gabriele333.fmtt.client.animations.CrystalAnimator;
import net.gabriele333.fmtt.item.crystals.BaseCrystalItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;


public abstract class BaseCrystal extends Entity {
    protected final CrystalAnimator animator;
    private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(BaseCrystal.class, EntityDataSerializers.ITEM_STACK);
    private Player owner;

    public BaseCrystal(EntityType<?> type, Level level) {
        super(type, level);
        this.animator = createAnimator();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(ITEM_STACK, ItemStack.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("ItemStack")) {
            ItemStack stack = ItemStack.parseOptional(this.registryAccess(), compound.getCompound("ItemStack"));
            setItemStack(stack);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        if (!getItemStack().isEmpty()) {
            compound.put("ItemStack", getItemStack().save(this.registryAccess(), new CompoundTag()));
        }
    }
    protected abstract CrystalAnimator createAnimator();
    public abstract float getScale();
    public abstract ResourceLocation getTexture();
    public abstract ResourceLocation getEmissiveTexture();

    public CrystalAnimator getAnimator() {
        return animator;
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    public ItemStack getItemStack() {
        return this.entityData.get(ITEM_STACK);
    }

    public void setItemStack(ItemStack stack) {
        this.entityData.set(ITEM_STACK, stack.copy());
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        if (player == this.owner && hand == InteractionHand.MAIN_HAND) {
            if (!this.level().isClientSide) {
                ItemStack stack = getItemStack().copy();
                BaseCrystalItem.setEntity(stack, false);

                if (player.getInventory().add(stack)) {
                    player.inventoryMenu.broadcastChanges();
                } else {
                    player.drop(stack, false);
                }

                this.discard();
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.interactAt(player, vec, hand);
    }
}