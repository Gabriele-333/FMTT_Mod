package net.gabriele333.fmtt.util.dimension;/*
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

import net.gabriele333.fmtt.mixins.LevelRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

import static net.gabriele333.fmtt.fmtt.MOD_ID;

public class DimensionRenderingUtils {

    public static final ResourceLocation BACKLIGHT = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/environment/backlight.png");

    public static final ResourceLocation SUN = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/environment/sun.png");

    public static final ResourceLocation EARTH = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/environment/earth.png");
    public static final ResourceLocation MOON = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/environment/moon.png");

    public static final List<ResourceLocation> SOLAR_SYSTEM_TEXTURES = List.of(
            DimensionRenderingUtils.EARTH
    );

    public static int getTicks() {
        return ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).getTicks();
    }
}
