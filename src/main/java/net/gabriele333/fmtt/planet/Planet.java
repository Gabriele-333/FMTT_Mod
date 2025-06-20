package net.gabriele333.fmtt.planet;/*
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
            ResourceLocation.CODEC.fieldOf("fmtt_solar_system").forGetter(Planet::solarSystem),
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
