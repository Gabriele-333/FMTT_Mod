package net.gabriele333.fmtt.util;/*
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
 * File created on: 20/05/2025
 */

import net.gabriele333.fmtt.client.dimension.FMTTDimensionSpecialEffects;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class ClientPlatformUtilsImpl {

    public static final Map<ResourceKey<Level>, FMTTDimensionSpecialEffects> DIMENSION_RENDERERS = new HashMap<>();


    public static void registerPlanetRenderers(Map<ResourceKey<Level>, FMTTDimensionSpecialEffects> renderers) {
        DIMENSION_RENDERERS.clear();
        DIMENSION_RENDERERS.putAll(renderers);
    }

}
