package net.gabriele333.fmtt.util.dimension;/*
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

import net.gabriele333.fmtt.mixins.LevelRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

import static net.gabriele333.fmtt.fmtt.MOD_ID;

public class DimensionRenderingUtil {

    public static final ResourceLocation BACKLIGHT = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/environment/backlight.png");

    public static final ResourceLocation SUN = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/environment/sun.png");

    public static final ResourceLocation EARTH = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/environment/earth.png");

    public static final List<ResourceLocation> SOLAR_SYSTEM_TEXTURES = List.of(
            DimensionRenderingUtil.EARTH
    );

    public static int getTicks() {
        return ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).getTicks();
    }
}
