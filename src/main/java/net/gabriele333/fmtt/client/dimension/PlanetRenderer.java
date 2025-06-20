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
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.Level;


import java.util.List;
import java.util.Optional;

public record PlanetRenderer(
        ResourceKey<Level> dimension,
        boolean customClouds,
        boolean customSky,
        boolean customWeather,
        boolean hasThickFog,
        boolean hasFog,
        int sunriseColor,
        int stars,
        Optional<Float> starBrightness,
        int sunriseAngle,
        boolean renderInRain,
        WeightedRandomList<WeightedEntry.Wrapper<Integer>> starColors,
        List<SkyRenderable> skyRenderables
) {

    public static final Codec<PlanetRenderer> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(PlanetRenderer::dimension),
            Codec.BOOL.fieldOf("custom_clouds").forGetter(PlanetRenderer::customClouds),
            Codec.BOOL.fieldOf("custom_sky").forGetter(PlanetRenderer::customSky),
            Codec.BOOL.fieldOf("custom_weather").forGetter(PlanetRenderer::customWeather),
            Codec.BOOL.fieldOf("has_thick_fog").forGetter(PlanetRenderer::hasThickFog),
            Codec.BOOL.fieldOf("has_fog").forGetter(PlanetRenderer::hasFog),
            Codec.INT.fieldOf("sunrise_color").forGetter(PlanetRenderer::sunriseColor),
            Codec.INT.fieldOf("stars").forGetter(PlanetRenderer::stars),
            Codec.FLOAT.optionalFieldOf("star_brightness").forGetter(PlanetRenderer::starBrightness),
            Codec.INT.fieldOf("sunrise_angle").forGetter(PlanetRenderer::sunriseAngle),
            Codec.BOOL.fieldOf("render_in_rain").forGetter(PlanetRenderer::renderInRain),
            SimpleWeightedRandomList.codec(WeightedEntry.Wrapper.codec(Codec.INT)).fieldOf("star_colors").forGetter(PlanetRenderer::starColors),
            SkyRenderable.CODEC.listOf().fieldOf("sky_renderables").forGetter(PlanetRenderer::skyRenderables)
    ).apply(inst, PlanetRenderer::new));
}