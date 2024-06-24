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

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.gabriele333.fmtt.fmtt.MOD_ID;


public class FMTTCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FMTT_CREATIVE_TAB = CREATIVE_MODE_TABS.register("fmtt_creative_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(FMTTItems.FMTT_ITEM.get()))
            .title(Component.translatable("creativetab.fmtt_creative_tab"))
            .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(FMTTItems.CRY_TIME.get());
                        pOutput.accept(FMTTItems.CRY_TECH.get());
                        pOutput.accept(FMTTItems.CRY_MAGIC.get());
                        pOutput.accept(FMTTItems.CRY_CHANCE.get());
                        pOutput.accept(FMTTItems.CRY_COSMOS.get());
                        pOutput.accept(FMTTItems.CRY_EMPTY.get());
                        pOutput.accept(FMTTItems.FMTT_XP_ITEM.get());
                        pOutput.accept(FMTTItems.FMTT_COMPASS.get());
                        pOutput.accept(FMTTItems.STAR_PIECE_BLOCK.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}


