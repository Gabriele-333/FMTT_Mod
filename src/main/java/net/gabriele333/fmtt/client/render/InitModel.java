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


 THIS CODE IS COPIED AND MODIFIED FROM: https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/init/client/InitBuiltInModels.java
 */



import net.gabriele333.fmtt.client.render.compass.FMTTCompassModel;
import net.gabriele333.fmtt.fmttint;
import net.minecraft.client.resources.model.UnbakedModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


import java.util.function.Supplier;



@OnlyIn(Dist.CLIENT)
public final class InitModel {
    private InitModel() {
    }

    public static void init() {
        addBuiltInModel("item/fmtt_compass", FMTTCompassModel::new);
    }

    private static <T extends UnbakedModel> void addBuiltInModel(String id, Supplier<T> modelFactory) {
        BuiltInModelHooks.addBuiltInModel(fmttint.makeId(id), modelFactory.get());
    }
}

