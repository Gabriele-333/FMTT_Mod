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
