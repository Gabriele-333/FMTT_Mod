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
 *
 *
 *
 *
 * THIS CODE IS COPIED AND MODIFIED FROM https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/forge/1.20.1/src/main/java/appeng/client/render/model/MeteoriteCompassModel.java
 */
package net.gabriele333.fmtt.client.render.compass;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;

import net.gabriele333.fmtt.client.render.BasicUnbakedModel;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;




public class FMTTCompassModel implements BasicUnbakedModel {

    private static final ResourceLocation MODEL_BASE = ResourceLocation.parse(
            "fmtt:item/compass/fmtt_compass_base");

    private static final ResourceLocation MODEL_POINTER = ResourceLocation.parse(
            "fmtt:item/compass/fmtt_compass_pointer");

    @Nullable
    @Override
    public BakedModel bake(ModelBaker loader, Function<Material, TextureAtlasSprite> textureGetter,
                           ModelState rotationContainer) {
        BakedModel baseModel = loader.bake(MODEL_BASE, rotationContainer);
        BakedModel pointerModel = loader.bake(MODEL_POINTER, rotationContainer);
        return new FMTTCompassBakedModel(baseModel, pointerModel);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableSet.of(MODEL_BASE, MODEL_POINTER);
    }



}

