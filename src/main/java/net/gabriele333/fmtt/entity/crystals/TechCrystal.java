package net.gabriele333.fmtt.entity.crystals;/*
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
 * File created on: 28/03/2025
 */

import net.gabriele333.fmtt.client.animations.CrystalAnimator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class TechCrystal extends BaseCrystal{
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("fmtt", "textures/entity/tech_crystal.png");

    public TechCrystal(EntityType<?> type, Level level) {
        super(type, level);
    }



    @Override
    protected CrystalAnimator createAnimator() {
        CrystalAnimator animator = new CrystalAnimator();
        animator.setRotationSpeedMain(1.2f);
        animator.setRotationSpeedFrame1(1.5f);
        animator.setRotationSpeedFrame2(0.9f);
        return animator;
    }

    @Override
    public float getScale() {
        return 1.0f;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
    @Override
    public ResourceLocation getEmissiveTexture() {
        return null;
    }
}
