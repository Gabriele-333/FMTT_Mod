package net.gabriele333.fmtt.tags;/*
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


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import java.util.concurrent.CompletableFuture;
import static net.gabriele333.fmtt.tags.FMTTTags.Items.CRAFTABLE;

public class FMTTItemTagsProvider extends ItemTagsProvider {


    public FMTTItemTagsProvider(PackOutput pPackOutput, CompletableFuture<HolderLookup.Provider> pProvider, CompletableFuture<TagLookup<Block>> pLookup,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(pPackOutput, pProvider, pLookup, "fmtt", existingFileHelper);
    }


    public void addItemToTagCraft(Item item) {
        tag(CRAFTABLE).add(item);
    }

    @Override
    public void addTags(HolderLookup.Provider pProvider) {
        tag(CRAFTABLE).add(Items.STONE);

    }
}
