package net.gabriele333.fmtt.mixins;/*
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

 THIS CODE IS COPIED AND MODIFIED FROM:  https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/mixins/ModelBakeryMixin.java
 */

import net.gabriele333.fmtt.client.render.BuiltInModelHooks;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelBakery.class)
public class ModelBakeryMixin {
    @Inject(at = @At("HEAD"), method = "getModel", cancellable = true)
    private void getModelHook(ResourceLocation id, CallbackInfoReturnable<UnbakedModel> cir) {
        var model = BuiltInModelHooks.getBuiltInModel(id);

        if (model != null) {
            cir.setReturnValue(model);
        }
    }
}