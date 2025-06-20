package net.gabriele333.fmtt.client.dimension;/*
 * This file is part of From Magic To Tech.
 * Copyright (c) 2025, Gabriele_333. All rights reserved.
 *
 * This file includes code copied and modified from the Ad Astra project,
 * originally licensed under the MIT License.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Original source: https://github.com/terrarium-earth/Ad-Astra
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