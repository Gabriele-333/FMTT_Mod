package net.gabriele333.fmtt;/*
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

import net.gabriele333.fmtt.client.models.CrystalModelBase;
import net.gabriele333.fmtt.client.models.FMTTModelLayers;
import net.gabriele333.fmtt.client.render.InitModel;
import net.gabriele333.fmtt.client.render.crystals.CrystalRenderer;
import net.gabriele333.fmtt.config.ClientConfig;
import net.gabriele333.fmtt.entity.FMTTEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;


@OnlyIn(Dist.CLIENT)
public class fmttClient extends fmtt{
    public fmttClient(IEventBus modEventBus, ModContainer modContainer) {
        super(modEventBus, modContainer);
        modEventBus.addListener(this::registerEntityRenderers);
        modEventBus.addListener(this::onRegisterEntityRendererLayerDefinitions);

        modContainer.registerConfig(ModConfig.Type.CLIENT, new ClientConfig().spec);
        InitModel.init();

    }
    private void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FMTTEntities.TIME_CRYSTAL.get(), CrystalRenderer::new);
        event.registerEntityRenderer(FMTTEntities.TECH_CRYSTAL.get(), CrystalRenderer::new);
        event.registerEntityRenderer(FMTTEntities.COSMOS_CRYSTAL.get(), CrystalRenderer::new);
        event.registerEntityRenderer(FMTTEntities.MAGIC_CRYSTAL.get(), CrystalRenderer::new);
        event.registerEntityRenderer(FMTTEntities.CHANCE_CRYSTAL.get(), CrystalRenderer::new);
    }

    public void onRegisterEntityRendererLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FMTTModelLayers.CRYSTAL, CrystalModelBase::createBodyLayer);
    }


}
