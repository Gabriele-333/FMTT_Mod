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
package net.gabriele333.fmtt;





import com.mojang.logging.LogUtils;
import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXpProvider;
import net.gabriele333.fmtt.data.FMTTDataProvider;
import net.gabriele333.fmtt.item.FMTTItems;
import net.gabriele333.fmtt.network.FMTTNetwork;
import net.gabriele333.fmtt.tags.FMTTBlockTagsProvider;
import net.gabriele333.fmtt.tags.FMTTItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

import static net.gabriele333.fmtt.item.FMTTCreativeTabs.CREATIVE_MODE_TABS;
import static net.gabriele333.fmtt.block.FMTTBlock.BLOCKS;



public abstract class fmtt {
    public static final String MOD_ID = "fmtt";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static fmtt INSTANCE;


    public fmtt(IEventBus modEventBus, ModContainer modContainer) {
        INSTANCE = this;


        modEventBus.addListener(FMTTNetwork::init);


        LOGGER.info("ciao");
        FMTTItems.register(modEventBus);
        PlayerFMTTXpProvider.register(modEventBus);
        BLOCKS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);


        modEventBus.addListener(EventPriority.LOWEST, this::onGatherData);

    }

    private static FMTTItemTagsProvider itemTagsProvider;
    public void onGatherData(GatherDataEvent event) {
        LOGGER.info("GatherDataEvent triggered.");

        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        FMTTDataProvider provider = new FMTTDataProvider("fmtt");

        var b = new FMTTBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        provider.addSubProvider(event.includeServer(), b);
        provider.addSubProvider(event.includeServer(), new FMTTItemTagsProvider(packOutput, lookupProvider, b.contentsGetter(), existingFileHelper));



        fmtt.itemTagsProvider = new FMTTItemTagsProvider(packOutput, lookupProvider, b.contentsGetter(), existingFileHelper);


    }
    public static FMTTItemTagsProvider getItemTagsProvider() {
        return itemTagsProvider;
    }

}