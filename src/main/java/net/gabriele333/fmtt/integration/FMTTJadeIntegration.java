package net.gabriele333.fmtt.integration;/*
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
 * File created on: 12/05/2025
 */

import net.gabriele333.fmtt.block.XpCrystallizer.XpCrystallizer;
import net.gabriele333.fmtt.block.XpCrystallizer.XpCrystallizerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

import static net.gabriele333.fmtt.fmtt.MOD_ID;

public class FMTTJadeIntegration {
    public static void init(IEventBus modEventBus) {
        // Registra l'evento di setup comune invece di usare un listener generico
        modEventBus.addListener(FMTTJadeIntegration::onCommonSetup);
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        // Questo viene eseguito dopo che Jade è pronto
    }

    @WailaPlugin
    public static class FMTTJadePlugin implements IWailaPlugin {
        @Override
        public void register(IWailaCommonRegistration registration) {
            registration.registerBlockDataProvider(new XpCrystallizerDataProvider(), XpCrystallizerEntity.class);
        }

        @Override
        public void registerClient(IWailaClientRegistration registration) {
            registration.registerBlockComponent(new XpCrystallizerComponent(), XpCrystallizer.class);

            // Aggiungi questa linea per registrare l'icona (opzionale)
            registration.registerBlockIcon(new XpCrystallizerIconProvider(), XpCrystallizer.class);
        }
    }

    public static class XpCrystallizerDataProvider implements IServerDataProvider<BlockAccessor> {
        @Override
        public void appendServerData(CompoundTag data, BlockAccessor accessor) {
            if (accessor.getBlockEntity() instanceof XpCrystallizerEntity crystallizer) {
                CompoundTag energyData = new CompoundTag();
                energyData.putInt("energy", crystallizer.getEnergyStorage().getEnergyStored());
                energyData.putInt("maxEnergy", crystallizer.getEnergyStorage().getMaxEnergyStored());
                data.put("fmtt.xp_crystallizer", energyData);
            }
        }

        @Override
        public ResourceLocation getUid() {
            return ResourceLocation.fromNamespaceAndPath(MOD_ID, "xp_crystallizer_data");
        }
    }

    public static class XpCrystallizerComponent implements IBlockComponentProvider {
        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
            CompoundTag data = accessor.getServerData().getCompound("fmtt.xp_crystallizer");
            if (!data.isEmpty()) {
                int energy = data.getInt("energy");
                int maxEnergy = data.getInt("maxEnergy");
                tooltip.add(Component.translatable("tooltip.fmtt.energy", energy, maxEnergy));
            }
        }

        @Override
        public ResourceLocation getUid() {
            return ResourceLocation.fromNamespaceAndPath(MOD_ID, "xp_crystallizer");
        }
    }

    // Classe opzionale per l'icona personalizzata
    public static class XpCrystallizerIconProvider implements IBlockComponentProvider {
        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {}

        @Override
        public ResourceLocation getUid() {
            return ResourceLocation.fromNamespaceAndPath(MOD_ID, "xp_crystallizer_icon");
        }

        @Override
        public int getDefaultPriority() {
            return TooltipPosition.BODY;
        }
    }
}