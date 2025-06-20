package net.gabriele333.fmtt.datagen;/*
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


import net.gabriele333.fmtt.client.dimension.MovementType;
import net.gabriele333.fmtt.client.dimension.PlanetRenderer;
import net.gabriele333.fmtt.client.dimension.SkyRenderable;
import net.gabriele333.fmtt.planet.Planet;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import net.gabriele333.fmtt.util.dimension.DimensionRenderingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static net.gabriele333.fmtt.fmtt.MOD_ID;

public class ModPlanetRendererProvider extends ModCodecProvider<PlanetRenderer> {

    public static final ResourceKey<Registry<PlanetRenderer>> PLANET_REGISTRY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MOD_ID, "planet_renderers"));

    public static final int DEFAULT_SUNRISE_COLOR = 0xd85f33;

    public static final SimpleWeightedRandomList<Integer> COLORED_STARS = SimpleWeightedRandomList.<Integer>builder()
            .add(0xA9BCDFFF, 3)   // Blue
            .add(0xBBD7FFFF, 5)   // Blue-White,
            .add(0xFFF4E8FF, 100) // Yellow-White
            .add(0xFFD1A0FF, 80)  // Orange
            .add(0xFF8A8AFF, 150) // Red
            .add(0xFF4500FF, 10)  // Orange-Red
            .add(0xFFFFF4FF, 60)  // White
            .add(0xFFF8E7FF, 40)  // Pale Yellow
            .add(0xFFFFFF00, 20)  // Very Pale Yellow
            .add(0xFFFF0000, 1)   // Bright Red
            .build();

    public static final SimpleWeightedRandomList<Integer> DEFAULT_STARS = SimpleWeightedRandomList.<Integer>builder()
            .add(0xffffffff, 1)
            .build();

    public ModPlanetRendererProvider(PackOutput packOutput) {
        super(packOutput, PlanetRenderer.CODEC, PLANET_REGISTRY, PackOutput.Target.RESOURCE_PACK);
    }

    @Override
    protected void build(BiConsumer<ResourceLocation, PlanetRenderer> consumer) {
        orbit(consumer, Planet.EARTH_ORBIT, DimensionRenderingUtils.EARTH, 0xff3c7cda, 10,
                new SkyRenderable(DimensionRenderingUtils.MOON, 8, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xffafb8cc));
    }

    private static void orbit(BiConsumer<ResourceLocation, PlanetRenderer> consumer, ResourceKey<Level> planet, ResourceLocation planetTexture, int backlightColor, int sunScale, SkyRenderable... additionalRenderables) {
        List<SkyRenderable> renderables = new ArrayList<>();
        renderables.add(new SkyRenderable(DimensionRenderingUtils.SUN, sunScale, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, 0xffffffd9));
        renderables.add(new SkyRenderable(planetTexture, 80, new Vec3(180, 0, 0), Vec3.ZERO, MovementType.STATIC, backlightColor));
        renderables.addAll(List.of(additionalRenderables));
        consumer.accept(
                planet.location(),
                new PlanetRenderer(
                        planet,
                        true,
                        true,
                        false,
                        false,
                        false,
                        DEFAULT_SUNRISE_COLOR,
                        13000,
                        Optional.of(0.6f),
                        0,
                        true,
                        COLORED_STARS,
                        renderables
                ));
    }

    @Override
    public @NotNull String getName() {
        return "Planet Renderers";
    }
}