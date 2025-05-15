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
 */
package net.gabriele333.fmtt.item;


import net.gabriele333.fmtt.fmtt;
import net.gabriele333.fmtt.item.crystals.CosmosCrystalItem;
import net.gabriele333.fmtt.item.crystals.TechCrystalItem;
import net.gabriele333.fmtt.item.crystals.TimeCrystalItem;
import net.gabriele333.fmtt.item.crystals.UnknownCrystalItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FMTTItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(fmtt.MOD_ID);


    //Crystals items
    public static final Supplier<Item> CRY_TIME = ITEMS.register("crystal_of_time",
            () -> new TimeCrystalItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
    public static final Supplier<Item> CRY_COSMOS = ITEMS.register("crystal_of_cosmos",
            () -> new CosmosCrystalItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
    public static final Supplier<Item> CRY_TECH = ITEMS.register("crystal_of_tech",
            () -> new TechCrystalItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
    public static final Supplier<Item> CRY_UNK = ITEMS.register("unknown_crystal",
            () -> new UnknownCrystalItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));


//FIXME: Fix this shit



    public static final Supplier<Item> CRY_MAGIC = ITEMS.register("crystal_of_magic",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    public static final Supplier<Item> CRY_CHANCE = ITEMS.register("crystal_of_chance",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    public static final Supplier<Item> CRY_EMPTY = ITEMS.register("empty_crystal",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final Supplier<Item> FMTT_XP_ITEM = ITEMS.register("fmtt_xp_item",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).fireResistant()));
    public static final Supplier<Item> FMTT_REWARD_ITEM = ITEMS.register("fmtt_reward_item",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final Supplier<Item> FMTT_COMPASS = ITEMS.register("fmtt_compass",
            () -> new Item(new Item.Properties().rarity(Rarity.COMMON).fireResistant()));







    public static final Supplier<Item> FMTT_ITEM = ITEMS.register("fmtt_item",
            ()-> new Item(new Item.Properties())
    );


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
