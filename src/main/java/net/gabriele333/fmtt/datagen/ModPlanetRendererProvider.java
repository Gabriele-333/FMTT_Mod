package net.gabriele333.fmtt.datagen;/*
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