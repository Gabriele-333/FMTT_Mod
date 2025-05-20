package net.gabriele333.fmtt.planet;/*
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

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.gabriele333.fmtt.datagen.fmttData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

import static net.gabriele333.fmtt.fmtt.MOD_ID;

public record Planet(
        ResourceKey<Level> dimension,
        boolean oxygen, short temperature,
        float gravity, int solarPower,
        ResourceLocation solarSystem,
        Optional<ResourceKey<Level>> orbit, int tier,
        List<ResourceKey<Level>> additionalLaunchDimensions
) {

    public static final ResourceKey<Level> EARTH_ORBIT = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MOD_ID, "earth_orbit"));


    public static final ResourceKey<Level> MOON = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MOD_ID, "moon"));


    public static final Codec<Planet> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(Planet::dimension),
            Codec.BOOL.fieldOf("oxygen").forGetter(Planet::oxygen),
            Codec.SHORT.fieldOf("temperature").forGetter(Planet::temperature),
            Codec.FLOAT.fieldOf("gravity").forGetter(Planet::gravity),
            Codec.INT.fieldOf("solar_power").forGetter(Planet::solarPower),
            ResourceLocation.CODEC.fieldOf("solar_system").forGetter(Planet::solarSystem),
            ResourceKey.codec(Registries.DIMENSION).optionalFieldOf("orbit").forGetter(Planet::orbit),
            Codec.INT.fieldOf("tier").forGetter(Planet::tier),
            ResourceKey.codec(Registries.DIMENSION).listOf().optionalFieldOf("additional_launch_dimensions", List.of()).forGetter(Planet::additionalLaunchDimensions)
    ).apply(instance, Planet::new));

    public ResourceKey<Level> orbitIfPresent() {
        return orbit.orElse(dimension);
    }

    public boolean isSpace() {
        return orbit.isEmpty();
    }

    public Optional<ResourceKey<Level>> getOrbitPlanet() {
        for (var planet : fmttData.planets().values()) {
            if (planet.orbit().isEmpty()) continue;
            if (planet.orbit().get().equals(dimension)) return Optional.of(planet.dimension());
        }
        return Optional.empty();
    }
}
