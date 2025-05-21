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
import net.gabriele333.fmtt.Attachments.Attachments;
import net.gabriele333.fmtt.block.XpCrystallizer.XpCrystallizerEntity;
import net.gabriele333.fmtt.data.FMTTDataProvider;
import net.gabriele333.fmtt.datagen.ModPlanetProvider;
import net.gabriele333.fmtt.datagen.ModPlanetRendererProvider;
import net.gabriele333.fmtt.datagen.ModRegistryProvider;
import net.gabriele333.fmtt.datagen.fmttData;
import net.gabriele333.fmtt.entity.FMTTEntities;
import net.gabriele333.fmtt.entity.FMTTVillager;
import net.gabriele333.fmtt.integration.FMTTJadeIntegration;
import net.gabriele333.fmtt.item.FMTTItems;
import net.gabriele333.fmtt.network.FMTTNetwork;
import net.gabriele333.fmtt.tags.FMTTBlockTagsProvider;
import net.gabriele333.fmtt.tags.FMTTItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;


import static net.gabriele333.fmtt.block.FMTTBlockEntity.BLOCK_ENTITIES;
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
        Attachments.register(modEventBus);
        BLOCKS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        FMTTEntities.REGISTER.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        FMTTJadeIntegration.init(modEventBus);

        modEventBus.addListener(XpCrystallizerEntity::registerCapabilities);
        modEventBus.addListener((RegisterEvent event) -> {
            if (event.getRegistryKey() == Registries.VILLAGER_PROFESSION) {
                FMTTVillager.initProfession(event.getRegistry(Registries.VILLAGER_PROFESSION));
            } else if (event.getRegistryKey() == Registries.POINT_OF_INTEREST_TYPE) {
                FMTTVillager.initPointOfInterestType(event.getRegistry(Registries.POINT_OF_INTEREST_TYPE));
            }
        });

        NeoForge.EVENT_BUS.addListener(FMTTVillager::initTrades);

        modEventBus.addListener(EventPriority.LOWEST, this::onGatherData);
        NeoForge.EVENT_BUS.addListener(this::onAddReloadListener);

    }

    private static FMTTItemTagsProvider itemTagsProvider;
    public void onGatherData(GatherDataEvent event) {
        LOGGER.info("GatherDataEvent triggered.");
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();



        generator.addProvider(event.includeClient(), new ModPlanetRendererProvider(packOutput));
        generator.addProvider(event.includeServer(), new ModPlanetProvider(packOutput));

        generator.addProvider(event.includeServer(), new ModRegistryProvider(packOutput, lookupProvider));

        FMTTDataProvider provider = new FMTTDataProvider("fmtt");


        var b = new FMTTBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        provider.addSubProvider(event.includeServer(), b);
        provider.addSubProvider(event.includeServer(), new FMTTItemTagsProvider(packOutput, lookupProvider, b.contentsGetter(), existingFileHelper));


    }

    public void onAddReloadListener(AddReloadListenerEvent event) {
        onAddReloadListeners((id, listener) -> event.addListener(listener));
    }
    public static void onAddReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
        registry.accept(ResourceLocation.fromNamespaceAndPath(MOD_ID, "planets"), new fmttData());
    }

}