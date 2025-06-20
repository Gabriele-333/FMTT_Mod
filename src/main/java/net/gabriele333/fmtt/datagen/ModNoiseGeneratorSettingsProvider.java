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
