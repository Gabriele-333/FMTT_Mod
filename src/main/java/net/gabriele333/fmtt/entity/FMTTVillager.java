package net.gabriele333.fmtt.entity;/*
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
 */




import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.gabriele333.fmtt.fmttint.MOD_ID;

public class FMTTVillager {
    private FMTTVillager() {
    }

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MOD_ID, "analyzer" );

    public static final ResourceKey<PoiType> POI_KEY = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, ID);

    public static final VillagerProfession PROFESSION = new VillagerProfession(ID.toString(), e -> e.is(POI_KEY),
            e -> e.is(POI_KEY), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LIBRARIAN);

    public static void initTrades(VillagerTradesEvent event) {
        if (!event.getType().name().equals(PROFESSION.name())) {
            return;
        }

        var trades = event.getTrades();

    }

    private static void sellItems(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, int minLevel,
                                  ItemLike soldItem, int numberOfItems, int maxUses, int xp) {
        addOffers(
                trades, minLevel,
                new VillagerTrades
                        .EmeraldForItems(soldItem, numberOfItems, maxUses, xp));
    }

    private static void buyItems(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, int minLevel,
                                 ItemLike boughtItem, int emeraldCost, int numberOfItems, int xp) {
        addOffers(
                trades, minLevel,
                new VillagerTrades.ItemsForEmeralds(boughtItem.asItem(), emeraldCost, numberOfItems, xp));
    }

    private static void addOffers(Int2ObjectMap<List<VillagerTrades.ItemListing>> offersByLevel, int minLevel,
                                  VillagerTrades.ItemListing... newOffers) {
        var entries = offersByLevel.computeIfAbsent(minLevel, key -> new ArrayList<>());
        Collections.addAll(entries, newOffers);
        offersByLevel.put(minLevel, entries);
    }
    public static void initProfession(Registry<VillagerProfession> registry) {
        Registry.register(registry, ID, PROFESSION);
    }



}