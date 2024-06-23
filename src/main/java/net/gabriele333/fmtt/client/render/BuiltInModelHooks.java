package net.gabriele333.fmtt.client.render;/*
 * This file is part of From Magic To Tech.
 * Copyright (c) 2024, Gabriele_333, All rights reserved.
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

 THIS CODE IS COPIED AND MODIFIED FROM: https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/hooks/BuiltInModelHooks.java
 */



import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static net.gabriele333.fmtt.fmtt.MOD_ID;

public class BuiltInModelHooks {
    private static final Map<ResourceLocation, UnbakedModel> builtInModels = new HashMap<>();

    private BuiltInModelHooks() {
    }

    public static void addBuiltInModel(ResourceLocation id, UnbakedModel model) {
        if (builtInModels.put(id, model) != null) {
            throw new IllegalStateException("Duplicate built-in model ID: " + id);
        }
    }

    @Nullable
    public static UnbakedModel getBuiltInModel(ResourceLocation variantId) {
        if (!MOD_ID.equals(variantId.getNamespace())) {
            return null;
        }
        return builtInModels.get(variantId);
    }
}