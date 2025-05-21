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
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.Nullable;

import static net.gabriele333.fmtt.fmtt.MOD_ID;

public class ModBiomeDataProvider {

    public static final ResourceKey<Biome> SPACE = register("orbit");


    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(SPACE, biome(
                false,
                0.5f,
                0,
                0x000000,
                0x000000,
                new MobSpawnSettings.Builder(),
                new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers),
                null));

    }

    private static BiomeGenerationSettings.Builder moon(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        return caves(placedFeatures, configuredCarvers)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.LARGE_DRIPSTONE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.DRIPSTONE_CLUSTER)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.POINTED_DRIPSTONE);

    }
    private static BiomeGenerationSettings.Builder caves(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        return new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND)
                .addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
    }

    public static Biome biome(boolean hasPrecipitation, float temperature, float downfall, int skyColor, int fogColor, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder generationSettings, @Nullable Music backgroundMusic) {
        return biome(hasPrecipitation, temperature, downfall, skyColor, fogColor, null, 0x3f76e4, 0x50533, null, null, mobSpawnSettings, generationSettings, backgroundMusic);
    }

    public static Biome biome(boolean hasPrecipitation, float temperature, float downfall, int skyColor, int fogColor, @Nullable AmbientParticleSettings particles, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder generationSettings, @Nullable Music backgroundMusic) {
        return biome(hasPrecipitation, temperature, downfall, skyColor, fogColor, particles, 0x3f76e4, 0x50533, null, null, mobSpawnSettings, generationSettings, backgroundMusic);
    }

    public static Biome biome(boolean hasPrecipitation, float temperature, float downfall, int skyColor, int fogColor, AmbientParticleSettings particles, int waterColor, int waterFogColor, @Nullable Integer grassColorOverride, @Nullable Integer foliageColorOverride, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder generationSettings, @Nullable Music backgroundMusic) {
        var specoalEffectsBuilder = (new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(skyColor).skyColor(fogColor).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(backgroundMusic);
        if (grassColorOverride != null) {
            specoalEffectsBuilder.grassColorOverride(grassColorOverride);
        }

        if (particles != null) {
            specoalEffectsBuilder.ambientParticle(particles);
        }

        if (foliageColorOverride != null) {
            specoalEffectsBuilder.foliageColorOverride(foliageColorOverride);
        }

        return (new Biome.BiomeBuilder()).hasPrecipitation(hasPrecipitation).temperature(temperature).downfall(downfall).specialEffects(specoalEffectsBuilder.build()).mobSpawnSettings(mobSpawnSettings.build()).generationSettings(generationSettings.build()).build();
    }
}
