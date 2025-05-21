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

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;

import java.util.List;

import static net.gabriele333.fmtt.fmtt.MOD_ID;

public class ModNoiseGeneratorSettingsProvider {

    public static final ResourceKey<NoiseGeneratorSettings> SPACE = register("orbit");
    public static final ResourceKey<NoiseGeneratorSettings> MOON = register("moon");


    protected static final NoiseSettings SIMPLE_NOISE_SETTINGS = NoiseSettings.create(0, 256, 2, 1);

    private static ResourceKey<NoiseGeneratorSettings> register(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context) {
        context.register(SPACE, space());
    }

    public static NoiseGeneratorSettings space() {
        return create(
                SIMPLE_NOISE_SETTINGS,
                Blocks.AIR.defaultBlockState(),
                Blocks.AIR.defaultBlockState(),
                none(),
                SurfaceRuleData.air(),
                List.of(),
                0,
                true,
                false,
                false,
                false
        );
    }

    public static NoiseGeneratorSettings create(NoiseSettings noiseSettings, BlockState defaultBlock, BlockState defaultFluid, NoiseRouter noiseRouter, SurfaceRules.RuleSource surfaceRule, List<Climate.ParameterPoint> spawnTarget, int seaLevel, boolean disableMobGeneration, boolean aquifersEnabled, boolean oreVeinsEnabled, boolean useLegacyRandomSource) {
        return new NoiseGeneratorSettings(noiseSettings, defaultBlock, defaultFluid, noiseRouter, surfaceRule, spawnTarget, seaLevel, disableMobGeneration, aquifersEnabled, oreVeinsEnabled, useLegacyRandomSource);
    }

    public static NoiseRouter none() {
        return create(
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero()
        );
    }

    public static NoiseRouter create(
            DensityFunction barrierNoise,
            DensityFunction fluidLevelFloodednessNoise,
            DensityFunction fluidLevelSpreadNoise,
            DensityFunction lavaNoise,
            DensityFunction temperature,
            DensityFunction vegetation,
            DensityFunction continents,
            DensityFunction erosion,
            DensityFunction depth,
            DensityFunction ridges,
            DensityFunction initialDensityWithoutJaggedness,
            DensityFunction finalDensity,
            DensityFunction veinToggle,
            DensityFunction veinRidged,
            DensityFunction veinGap) {
        return new NoiseRouter(barrierNoise, fluidLevelFloodednessNoise, fluidLevelSpreadNoise, lavaNoise, temperature, vegetation, continents, erosion, depth, ridges, initialDensityWithoutJaggedness, finalDensity, veinToggle, veinRidged, veinGap);
    }
}
