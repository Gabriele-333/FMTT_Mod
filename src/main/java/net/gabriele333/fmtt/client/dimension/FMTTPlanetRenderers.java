package net.gabriele333.fmtt.client.dimension;/*
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
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import net.gabriele333.fmtt.fmtt;
import net.gabriele333.fmtt.util.ClientPlatformUtilsImpl;
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
import java.util.Optional;
import java.util.Set;

public class FMTTPlanetRenderers extends SimpleJsonResourceReloadListener {



    public FMTTPlanetRenderers() {
        super(Constants.GSON, "planet_renderers");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<ResourceKey<Level>, FMTTDimensionSpecialEffects> EFFECTS = new HashMap<>();
        object.forEach((key, value) -> {
            JsonObject json = GsonHelper.convertToJsonObject(value, "planets");
            Optional<PlanetRenderer> optionalRenderer = PlanetRenderer.CODEC.parse(JsonOps.INSTANCE, json).resultOrPartial(fmtt.LOGGER::error);
            optionalRenderer.ifPresent(renderer -> {
                EFFECTS.put(renderer.dimension(), new FMTTDimensionSpecialEffects(renderer));
            });
        });
        ClientPlatformUtilsImpl.registerPlanetRenderers(EFFECTS);
    }

}