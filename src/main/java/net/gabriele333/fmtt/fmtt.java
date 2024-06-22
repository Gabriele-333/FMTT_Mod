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
import net.gabriele333.fmtt.block.FMTTBlock;
import net.gabriele333.fmtt.client.render.InitModel;
import net.gabriele333.fmtt.config.FMTTConfig;
import net.gabriele333.fmtt.item.FMTTItems;
import net.gabriele333.fmtt.network.FMTTNetwork;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import static net.gabriele333.fmtt.FMTTCreativeTabs.CREATIVE_MODE_TABS;


@Mod(fmtt.MOD_ID)
public class fmtt {
    public static final String MOD_ID = "fmtt";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static fmtt INSTANCE;



    public fmtt(IEventBus modEventBus, ModContainer container) {
        INSTANCE = this;
        InitModel.init();

        //container.registerConfig(ModConfig.Type.COMMON, FMTTConfig);


        //IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(FMTTNetwork::init);

        LOGGER.info("ciao");
        LOGGER.info("Dm me on Discord if you find this");
        FMTTItems.register(modEventBus);
        FMTTBlock.register(modEventBus);
        PlayerFMTTXpProvider.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);


        //modEventBus.addListener(this::commonSetup);
        //MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

    }

/*

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        FMTTNetwork.init(ResourceLocation.fromNamespaceAndPath(MOD_ID, "main"));
    }*/

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }


    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
