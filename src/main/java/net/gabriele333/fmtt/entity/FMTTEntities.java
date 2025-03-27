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
 *Inspired by AE2: https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/core/definitions/AEEntities.java#L45
 */


import net.gabriele333.fmtt.entity.crystals.Crystal;
import net.minecraft.SharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.entity.EntityType.Builder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


import static net.gabriele333.fmtt.fmtt.MOD_ID;

public final class FMTTEntities {

    public static final DeferredRegister<EntityType<?>> DR = DeferredRegister.create(Registries.ENTITY_TYPE,
            MOD_ID);

    public static final Map<String, String> ENTITY_ENGLISH_NAMES = new HashMap<>();

    public static final DeferredHolder<EntityType<?>, EntityType<Crystal>> CRYSTAL = create(
            "crystal",
            "Crystal",
            Crystal::new,
            MobCategory.MISC,
            builder -> builder
                    .sized(0.98F, 0.98F)
                    .setTrackingRange(10)
                    .setUpdateInterval(Integer.MAX_VALUE)
                    .setShouldReceiveVelocityUpdates(false)
                    .fireImmune()
    );

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> create(String id,
                                                                                          String englishName,
                                                                                          EntityType.EntityFactory<T> entityFactory,
                                                                                          MobCategory classification,
                                                                                          Consumer<Builder<T>> customizer) {
        ENTITY_ENGLISH_NAMES.put(id, englishName);
        return DR.register(id, () -> {
            Builder<T> builder = Builder.of(entityFactory, classification);
            customizer.accept(builder);
            boolean prev = SharedConstants.CHECK_DATA_FIXER_SCHEMA;
            SharedConstants.CHECK_DATA_FIXER_SCHEMA = false;
            EntityType<T> result = builder.build(id);
            SharedConstants.CHECK_DATA_FIXER_SCHEMA = prev;
            return result;
        });
    }

}