package net.gabriele333.fmtt.client.render.crystal_item;/*
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
 * File created on: 04/04/2025
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

public class CrystalItemModel implements BasicUnbakedModel {
    private static ResourceLocation MAINCUBE;
    private static final ResourceLocation FRAME1 = ResourceLocation.parse("fmtt:item/crystal/frame1");
    private static final ResourceLocation FRAME2 = ResourceLocation.parse("fmtt:item/crystal/frame2");

    public CrystalItemModel(String mainCube) {
        this.MAINCUBE = ResourceLocation.parse(mainCube);
    }


    @Nullable
    @Override
    public BakedModel bake(ModelBaker loader, Function<Material, TextureAtlasSprite> textureGetter, ModelState rotationContainer) {
        BakedModel maincube = loader.bake(MAINCUBE, rotationContainer);
        BakedModel frame1 = loader.bake(FRAME1, rotationContainer);
        BakedModel frame2 = loader.bake(FRAME2, rotationContainer);
        return new CrystalItemBakedModel(maincube,frame1,frame2);

    }
    @Override
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableSet.of(MAINCUBE, FRAME1, FRAME2);
    }
}
