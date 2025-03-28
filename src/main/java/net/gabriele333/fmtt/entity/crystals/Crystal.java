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


import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;


public class Crystal extends Entity {
    private static final EntityDataAccessor<Boolean> SHOW_BOTTOM = SynchedEntityData.defineId(Crystal.class, EntityDataSerializers.BOOLEAN);


    public Crystal(EntityType<? extends Crystal> type, Level level) {
        super(type, level);
    }


    public boolean showsBottom() {
        return this.entityData.get(SHOW_BOTTOM);
    }

    public void setShowBottom(boolean show) {
        this.entityData.set(SHOW_BOTTOM, show);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(SHOW_BOTTOM, true);
    }
/*
    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            for (int i = 0; i < 4; i++) {
                double dx = (random.nextDouble() - 0.5) * 0.2;
                double dy = random.nextDouble() * 0.2;
                double dz = (random.nextDouble() - 0.5) * 0.2;
                level().addParticle(ParticleTypes.END_ROD, getX(), getY(), getZ(), dx, dy, dz);
            }
        }
    }
*/
    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        setShowBottom(tag.getBoolean("ShowBottom"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putBoolean("ShowBottom", showsBottom());
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(1.0);
    }
}