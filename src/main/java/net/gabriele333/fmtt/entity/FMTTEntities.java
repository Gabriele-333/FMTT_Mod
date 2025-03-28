package net.gabriele333.fmtt.entity;/*
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
 */



import net.gabriele333.fmtt.entity.crystals.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


import static net.gabriele333.fmtt.fmtt.MOD_ID;

public final class FMTTEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(Registries.ENTITY_TYPE, MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<TimeCrystal>> TIME_CRYSTAL = REGISTER.register("time_crystal",
            () -> EntityType.Builder.of(TimeCrystal::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(10)
                    .updateInterval(Integer.MAX_VALUE)
                    .build(ResourceLocation.fromNamespaceAndPath(MOD_ID, "crystal").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<CosmosCrystal>> COSMOS_CRYSTAL = REGISTER.register("cosmos_crystal",
            () -> EntityType.Builder.of(CosmosCrystal::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(10)
                    .updateInterval(Integer.MAX_VALUE)
                    .build(ResourceLocation.fromNamespaceAndPath(MOD_ID, "crystal").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<TechCrystal>> TECH_CRYSTAL = REGISTER.register("tech_crystal",
            () -> EntityType.Builder.of(TechCrystal::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(10)
                    .updateInterval(Integer.MAX_VALUE)
                    .build(ResourceLocation.fromNamespaceAndPath(MOD_ID, "crystal").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<ChanceCrystal>> CHANCE_CRYSTAL = REGISTER.register("chance_crystal",
            () -> EntityType.Builder.of(ChanceCrystal::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(10)
                    .updateInterval(Integer.MAX_VALUE)
                    .build(ResourceLocation.fromNamespaceAndPath(MOD_ID, "crystal").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<MagicCrystal>> MAGIC_CRYSTAL = REGISTER.register("magic_crystal",
            () -> EntityType.Builder.of(MagicCrystal::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(10)
                    .updateInterval(Integer.MAX_VALUE)
                    .build(ResourceLocation.fromNamespaceAndPath(MOD_ID, "crystal").toString()));

}