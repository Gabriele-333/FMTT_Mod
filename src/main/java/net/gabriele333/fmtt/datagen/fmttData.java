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
 * File created on: 20/05/2025
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.lib.Constants;
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
        super(Constants.GSON, "planets");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        PLANETS.clear();
        DIMENSIONS_TO_PLANETS.clear();
        object.forEach((key, value) -> {
            JsonObject json = GsonHelper.convertToJsonObject(value, "planets");
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
