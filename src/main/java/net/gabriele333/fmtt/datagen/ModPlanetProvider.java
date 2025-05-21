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
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static net.gabriele333.fmtt.fmtt.MOD_ID;

public class ModPlanetProvider extends ModCodecProvider<Planet> {

    public static final ResourceKey<Registry<Planet>> PLANET_REGISTRY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MOD_ID, "planets"));

    public ModPlanetProvider(PackOutput packOutput) {
        super(packOutput, Planet.CODEC, PLANET_REGISTRY);
    }

    @Override
    protected void build(BiConsumer<ResourceLocation, Planet> consumer) {
        orbit(consumer, ResourceLocation.fromNamespaceAndPath(MOD_ID, "solar_system"));

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "earth"),
                new Planet(
                        Level.OVERWORLD,
                        true,
                        (short) 15,
                        9.807f,
                        16,
                        ResourceLocation.fromNamespaceAndPath(MOD_ID, "solar_system"),
                        Optional.of(Planet.EARTH_ORBIT),
                        1,
                        List.of()
                )
        );

        consumer.accept(
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "moon"),
                new Planet(Planet.MOON,
                        false,
                        (short) -173,
                        1.622f,
                        24,
                        ResourceLocation.fromNamespaceAndPath(MOD_ID, "solar_system"),
                        Optional.of(Planet.EARTH_ORBIT),
                        1,
                        List.of()
                )

        );

    }

    private static void orbit(BiConsumer<ResourceLocation, Planet> consumer, ResourceLocation galaxy) {
        consumer.accept(
                Planet.EARTH_ORBIT.location(),
                new Planet(Planet.EARTH_ORBIT,
                        false,
                        (short) -270,
                        0.0f,
                        32,
                        galaxy,
                        Optional.empty(), 1,
                        List.of()
                )
        );
    }

    @Override
    public @NotNull String getName() {
        return "Planets";
    }
}
