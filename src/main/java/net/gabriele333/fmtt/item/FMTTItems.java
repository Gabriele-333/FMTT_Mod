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

import net.gabriele333.fmtt.block.FMTTBlock;
import net.gabriele333.fmtt.block.StarPieceBlock;
import net.gabriele333.fmtt.fmtt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FMTTItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(fmtt.MOD_ID);


    //Crystals items
    public static final Supplier<Item> CRY_TIME = ITEMS.register("crystal_of_time",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    public static final Supplier<Item> CRY_TECH = ITEMS.register("crystal_of_tech",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    public static final Supplier<Item> CRY_MAGIC = ITEMS.register("crystal_of_magic",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    public static final Supplier<Item> CRY_CHANCE = ITEMS.register("crystal_of_chance",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    public static final Supplier<Item> CRY_COSMOS = ITEMS.register("crystal_of_cosmos",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));
    public static final Supplier<Item> CRY_EMPTY = ITEMS.register("empty_crystal",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final Supplier<Item> FMTT_XP_ITEM = ITEMS.register("fmtt_xp_item",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final Supplier<Item> FMTT_COMPASS = ITEMS.register("fmtt_compass",
            () -> new Item(new Item.Properties().rarity(Rarity.COMMON).fireResistant()));






    public static final Supplier<Item> FMTT_ITEM = ITEMS.register("fmtt_item",
            ()-> new Item(new Item.Properties())
    );

    public static final Supplier<BlockItem> STAR_PIECE_BLOCK = ITEMS.register("star_piece_block",  () -> new BlockItem(StarPieceBlock.STAR_PIECE_BLOCK.get(), new Item.Properties() ));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
