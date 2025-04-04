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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;



public abstract class BaseCrystal extends Entity {
    protected final CrystalAnimator animator;


    public BaseCrystal(EntityType<?> type, Level level) {
        super(type, level);
        this.animator = createAnimator();
    }

    protected abstract CrystalAnimator createAnimator();

    public CrystalAnimator getAnimator() {
        return animator;
    }

    public abstract float getScale();
    public abstract ResourceLocation getTexture();
    public abstract ResourceLocation getEmissiveTexture();
}