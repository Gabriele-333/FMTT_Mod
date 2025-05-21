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

import net.gabriele333.fmtt.planet.Planet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;



import java.util.OptionalLong;

import static net.gabriele333.fmtt.fmtt.MOD_ID;

public class ModDimensionTypeProvider {

    public static final ResourceKey<DimensionType> EARTH_ORBIT = register("earth_orbit");

    public static final ResourceKey<DimensionType> MOON = register("moon");


    private static ResourceKey<DimensionType> register(String name) {
        return ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<DimensionType> context) {
        orbit(context, EARTH_ORBIT, Planet.EARTH_ORBIT.location());

        context.register(
                MOON,
                create(
                        OptionalLong.empty(),
                        true,
                        false,
                        false,
                        true,
                        1.0,
                        true,
                        false,
                        -64,
                        384,
                        384,
                        BlockTags.INFINIBURN_OVERWORLD,
                        Planet.MOON.location(),
                        0.0f,
                        createMonsterSettings(
                                false,
                                false,
                                UniformInt.of(0, 7),
                                0)));

    }

    private static void orbit(BootstrapContext<DimensionType> context, ResourceKey<DimensionType> key, ResourceLocation dimensionSpecialEffects) {
        context.register(
                key,
                create(
                        OptionalLong.empty(),
                        true,
                        false,
                        false,
                        true,
                        1.0,
                        true,
                        false,
                        -64,
                        384,
                        384,
                        BlockTags.INFINIBURN_OVERWORLD,
                        dimensionSpecialEffects,
                        0.0f,
                        createMonsterSettings(
                                false,
                                false,
                                UniformInt.of(0, 7),
                                0)));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static DimensionType create(OptionalLong fixedTime, boolean hasSkyLight, boolean hasCeiling, boolean ultraWarm, boolean natural, double coordinateScale, boolean bedWorks, boolean respawnAnchorWorks, int minY, int height, int logicalHeight, TagKey<Block> infiniburn, ResourceLocation effectsLocation, float ambientLight, DimensionType.MonsterSettings monsterSettings) {
        return new DimensionType(fixedTime, hasSkyLight, hasCeiling, ultraWarm, natural, coordinateScale, bedWorks, respawnAnchorWorks, minY, height, logicalHeight, infiniburn, effectsLocation, ambientLight, monsterSettings);
    }

    public static DimensionType.MonsterSettings createMonsterSettings(boolean piglinSafe, boolean hasRaids, IntProvider monsterSpawnLightTest, int monsterSpawnBlockLightLimit) {
        return new DimensionType.MonsterSettings(piglinSafe, hasRaids, monsterSpawnLightTest, monsterSpawnBlockLightLimit);
    }
}
