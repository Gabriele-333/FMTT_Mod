/*
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
 *
 *
 *
 *
 * THIS CODE IS COPIED AND MODIFIED FROM https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/client/render/BasicUnbakedModel.java
 */

package net.gabriele333.fmtt.client.render;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;


public interface BasicUnbakedModel extends UnbakedModel {
    @Override
    default Collection<ResourceLocation> getDependencies() {
        return Collections.emptyList();
    }

    @Override
    default void resolveParents(Function<ResourceLocation, UnbakedModel> function) {
        for (ResourceLocation dependency : getDependencies()) {
            function.apply(dependency).resolveParents(function);
        }
    }

}