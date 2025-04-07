package net.gabriele333.fmtt.client.render.crystal_item;/*
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
 * File created on: 04/04/2025
 */

import com.google.common.collect.ImmutableSet;
import net.gabriele333.fmtt.client.render.BasicUnbakedModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;


public class CrystalItemModel implements BasicUnbakedModel {
    public static final ModelProperty<Boolean> HAS_ENTITY_PROPERTY = new ModelProperty<>();
    private static ResourceLocation MAINCUBE;
    private static final ResourceLocation FRAME1 = ResourceLocation.parse("fmtt:item/crystal/frame1");
    private static final ResourceLocation FRAME2 = ResourceLocation.parse("fmtt:item/crystal/frame2");

    public CrystalItemModel(String mainCube) {
        this.MAINCUBE = ResourceLocation.parse(mainCube);
    }


    @Nullable
    @Override
    public BakedModel bake(ModelBaker loader, Function<Material, TextureAtlasSprite> textureGetter, ModelState rotationContainer) {
        BakedModel maincube = loader.bake(MAINCUBE, rotationContainer);
        BakedModel frame1 = loader.bake(FRAME1, rotationContainer);
        BakedModel frame2 = loader.bake(FRAME2, rotationContainer);
        return new CrystalItemBakedModel(maincube,frame1,frame2);

    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableSet.of(MAINCUBE, FRAME1, FRAME2);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            public @Nullable ModelData getModelData(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
                return ModelData.builder()
                        .with(HAS_ENTITY_PROPERTY, hasEntity(stack))
                        .build();
            }
        });
    }

    private static boolean hasEntity(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag()
                .getBoolean("HasEntity");
    }
}
