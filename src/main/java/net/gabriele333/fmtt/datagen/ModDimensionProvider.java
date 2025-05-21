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
 * File created on: 21/05/2025
 */

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import static net.gabriele333.fmtt.fmtt.MOD_ID;

public class ModDimensionProvider {

    public static final ResourceKey<LevelStem> EARTH_ORBIT = register("earth_orbit");

    public static final ResourceKey<LevelStem> MOON = register("moon");

    private static ResourceKey<LevelStem> register(String name) {
        return ResourceKey.create(Registries.LEVEL_STEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }

    private static ResourceKey<NoiseGeneratorSettings> registerNoise(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<LevelStem> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

        space(context, EARTH_ORBIT, ModDimensionTypeProvider.EARTH_ORBIT, dimensionTypes, biomes, noiseSettings);

    }

    private static void space(BootstrapContext<LevelStem> context, ResourceKey<LevelStem> key, ResourceKey<DimensionType> type, HolderGetter<DimensionType> dimensionTypes, HolderGetter<Biome> biomes, HolderGetter<NoiseGeneratorSettings> noiseSettings) {
        context.register(key, new LevelStem(
                dimensionTypes.getOrThrow(type),
                new NoiseBasedChunkGenerator(
                        new FixedBiomeSource(
                                biomes.getOrThrow(ModBiomeDataProvider.SPACE)),
                        noiseSettings.getOrThrow(ModNoiseGeneratorSettingsProvider.SPACE))));
    }
}
