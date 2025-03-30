package net.gabriele333.fmtt.client.render.crystals;/*
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
 * File created on: 27/03/2025
 */

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.gabriele333.fmtt.client.models.CrystalModelBase;
import net.gabriele333.fmtt.entity.crystals.BaseCrystal;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;



public class CrystalRenderer<T extends BaseCrystal> extends EntityRenderer<T> {
    private final CrystalModelBase<T> model;

    public CrystalRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CrystalModelBase<>(context.bakeLayer(CrystalModelBase.LAYER_LOCATION));
    }

    @Override
    public void render(T crystal, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        float scale = crystal.getScale();
        poseStack.scale(scale, scale, scale);
        poseStack.translate(0.0F, 1.5F, 0.0F);

        crystal.getAnimator().animate(model, crystal, partialTicks);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(
                RenderType.entityTranslucent(getTextureLocation(crystal)));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

        VertexConsumer emissiveConsumer = bufferSource.getBuffer(
                RenderType.eyes(getEmissiveTextureLocation(crystal)));
        model.renderEmissive(poseStack, emissiveConsumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }


    public ResourceLocation getEmissiveTextureLocation(T crystal) {
        return ResourceLocation.fromNamespaceAndPath("fmtt", "textures/entity/crystal_emissive.png");
    }

    @Override
    public ResourceLocation getTextureLocation(T crystal) {
        return crystal.getTexture();
    }
}
