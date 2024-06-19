package net.gabriele333.fmtt.block;/*
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

import net.gabriele333.fmtt.fmtt;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;


public class FMTTBlock {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(fmtt.MOD_ID);



    public static final DeferredBlock<Block> FMTT_BLOCK = BLOCKS.registerBlock("fmtt_block", Block::new, BlockBehaviour.Properties.of());


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
