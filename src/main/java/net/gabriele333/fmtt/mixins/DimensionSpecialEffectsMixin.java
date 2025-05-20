package net.gabriele333.fmtt.mixins;/*
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


import net.gabriele333.fmtt.util.ClientPlatformUtilsImpl;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionSpecialEffects.class)
public abstract class DimensionSpecialEffectsMixin {

    // Mixin directly instead of using the event because Ad Astra dimension renderers are added dynamically via resource pack.
    @Inject(method = "forType", at = @At("HEAD"), cancellable = true)
    private static void fmtt$forType(DimensionType type, CallbackInfoReturnable<DimensionSpecialEffects> cir) {
        ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, type.effectsLocation());
        if (ClientPlatformUtilsImpl.DIMENSION_RENDERERS.containsKey(dimension)) {
            cir.setReturnValue(ClientPlatformUtilsImpl.DIMENSION_RENDERERS.get(dimension));
        }
    }
}
