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


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import net.gabriele333.fmtt.fmtt;
import net.gabriele333.fmtt.planet.Planet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class fmttData extends SimpleJsonResourceReloadListener {

    private static final Map<ResourceKey<Level>, Planet> PLANETS = new HashMap<>();
    private static final Map<ResourceKey<Level>, ResourceKey<Level>> DIMENSIONS_TO_PLANETS = new HashMap<>();

    public fmttData() {
        super(Constants.GSON, "fmtt_planets");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        PLANETS.clear();
        DIMENSIONS_TO_PLANETS.clear();
        object.forEach((key, value) -> {
            JsonObject json = GsonHelper.convertToJsonObject(value, "fmtt_planets");
            if (!json.has("fmtt_solar_system")) {
                fmtt.LOGGER.info("Stellaris's fault");
                return;
            }
            Planet planet = Planet.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
            PLANETS.put(planet.dimension(), planet);
            DIMENSIONS_TO_PLANETS.put(planet.dimension(), planet.dimension());
            for (ResourceKey<Level> dimension : planet.additionalLaunchDimensions()) {
                DIMENSIONS_TO_PLANETS.put(dimension, planet.dimension());
            }
        });
    }
/*
    public static void encodePlanets(FriendlyByteBuf buf) {
        PacketHelper.writeWithYabn(buf, Planet.CODEC.listOf(), planets().values().stream().toList(), true)
                .get()
                .mapRight(DataResult.PartialResult::message)
                .ifRight();
    }

    public static Collection<Planet> decodePlanets(FriendlyByteBuf buf) {
        return PacketHelper.readWithYabn(buf, Planet.CODEC.listOf(), true)
                .get()
                .mapRight(DataResult.PartialResult::message)
                .ifRight()
                .left()
                .orElse(Collections.emptyList());
    }
*/
    public static ResourceKey<Level> getPlanetLocation(ResourceKey<Level> dimension) {
        return DIMENSIONS_TO_PLANETS.get(dimension);
    }

    @Nullable
    public static Planet getPlanet(ResourceKey<Level> location) {
        return PLANETS.get(location);
    }

    public static boolean isPlanet(ResourceKey<Level> location) {
        return PLANETS.containsKey(location);
    }

    public static boolean isSpace(ResourceKey<Level> location) {
        return isPlanet(location) && PLANETS.get(location).isSpace();
    }

    public static boolean canLaunchFrom(ResourceKey<Level> dimension) {
        return DIMENSIONS_TO_PLANETS.containsKey(dimension);
    }

    public static Map<ResourceKey<Level>, Planet> planets() {
        return PLANETS;
    }

    public static Set<ResourceLocation> solarSystems() {
        return PLANETS.values().stream().map(Planet::solarSystem).collect(Collectors.toUnmodifiableSet());
    }

    public static void setPlanets(Map<ResourceKey<Level>, Planet> planets) {
        PLANETS.clear();
        PLANETS.putAll(planets);
    }
}
