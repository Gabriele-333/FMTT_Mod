package net.gabriele333.fmtt.client.dimension;/*
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
 * File created on: 19/05/2025
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FMTTPlanetRenderers extends SimpleJsonResourceReloadListener {

    private static final Map<ResourceKey<Level>, FMTTDimensionSpecialEffects> EFFECTS = new HashMap<>();

    public FMTTPlanetRenderers() {
        super(Constants.GSON, "planet_renderers");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        EFFECTS.clear(); // evita duplicazioni
        object.forEach((key, value) -> {
            JsonObject json = GsonHelper.convertToJsonObject(value, "planets");
            PlanetRenderer renderer = PlanetRenderer.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
            EFFECTS.put(renderer.dimension(), new FMTTDimensionSpecialEffects(renderer));
        });
    }

    public static @Nullable FMTTDimensionSpecialEffects getEffect(ResourceKey<Level> dim) {
        return EFFECTS.get(dim);
    }

    public static Set<ResourceKey<Level>> getRegisteredDimensions() {
        return EFFECTS.keySet();
    }
}