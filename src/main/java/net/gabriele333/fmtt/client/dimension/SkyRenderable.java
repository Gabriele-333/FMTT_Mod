package net.gabriele333.fmtt.client.dimension;/*
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
 * File created on: 19/05/2025
 */

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.gabriele333.fmtt.util.ModUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public record SkyRenderable(
        ResourceLocation texture,
        float scale,
        Vec3 globalRotation,
        Vec3 localRotation,
        MovementType movementType,
        boolean blend,
        int backLightColor,
        float backLightScale) {

    public static final Codec<SkyRenderable> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(SkyRenderable::texture),
            Codec.FLOAT.fieldOf("scale").forGetter(SkyRenderable::scale),
            Vec3.CODEC.fieldOf("global_rotation").forGetter(SkyRenderable::globalRotation),
            Vec3.CODEC.fieldOf("local_rotation").forGetter(SkyRenderable::localRotation),
            ModUtils.createEnumCodec(MovementType.class).fieldOf("movement_type").forGetter(SkyRenderable::movementType),
            Codec.BOOL.fieldOf("blend").forGetter(SkyRenderable::blend),
            Codec.INT.fieldOf("back_light_color").forGetter(SkyRenderable::backLightColor),
            Codec.FLOAT.fieldOf("back_light_scale").forGetter(SkyRenderable::backLightScale)
    ).apply(inst, SkyRenderable::new));

    public SkyRenderable(ResourceLocation texture, float scale, Vec3 globalRotation, Vec3 localRotation, MovementType movementType, int backLightColor) {
        this(texture, scale, globalRotation, localRotation, movementType, false, backLightColor, scale * 3);
    }

    public SkyRenderable(ResourceLocation texture, float scale, Vec3 globalRotation, Vec3 localRotation, MovementType movementType, boolean blend, int backLightColor) {
        this(texture, scale, globalRotation, localRotation, movementType, blend, backLightColor, scale * 3);
    }
}