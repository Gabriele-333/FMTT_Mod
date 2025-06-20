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

    public static final ResourceKey<Registry<Planet>> PLANET_REGISTRY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MOD_ID, "fmtt_planets"));

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
