package net.gabriele333.fmtt.client.render.XpCrystallizer;/*
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
 * File created on: 10/05/2025
 */

import com.google.common.collect.ImmutableSet;
import net.gabriele333.fmtt.client.render.BasicUnbakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Function;

public class XpCrystallizerModel implements BasicUnbakedModel {
    private final String textureName;
    private static final ResourceLocation MAIN = ResourceLocation.parse("fmtt:block/xp_crystallizer/base");
    private static final ResourceLocation ROT1= ResourceLocation.parse("fmtt:block/xp_crystallizer/rot1");

    public XpCrystallizerModel(String textureName) {
        this.textureName = textureName;
    }


    @Nullable
    @Override
    public BakedModel bake(ModelBaker loader, Function<Material, TextureAtlasSprite> textureGetter, ModelState rotationContainer) {
        BakedModel main = loader.bake(MAIN, rotationContainer);
        BakedModel rot1 = loader.bake(ROT1, rotationContainer);
        return new XpCrystallizerBakedModel(main, rot1);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableSet.of(MAIN, ROT1);
    }
}
