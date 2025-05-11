package net.gabriele333.fmtt.client.render.XpCrystallizer;/*
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
 * File created on: 11/05/2025
 */

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.gabriele333.fmtt.block.XpCrystallizer.XpCrystallizerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;



public class XpCrystallizerRenderer implements BlockEntityRenderer<XpCrystallizerEntity> {
    private final RandomSource random = RandomSource.create();

    public XpCrystallizerRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(XpCrystallizerEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState state = entity.getBlockState();
        BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);

        if (model instanceof XpCrystallizerBakedModel bakedModel) {
            poseStack.pushPose();
            poseStack.translate(0.5, 0.5, 0.5);

            // Animazione rotazione
            float rotationAngle = (System.currentTimeMillis() % 36000) / 100f;
            poseStack.mulPose(Axis.YP.rotationDegrees(rotationAngle));
            poseStack.translate(-0.5, -0.5, -0.5);

            // Rendering
            VertexConsumer consumer = buffer.getBuffer(RenderType.solid());
            renderBakedModel(poseStack, consumer, state, bakedModel, packedLight, packedOverlay);

            poseStack.popPose();
        }
    }

    private void renderBakedModel(PoseStack poseStack, VertexConsumer consumer, BlockState state, XpCrystallizerBakedModel model, int packedLight, int packedOverlay) {
        PoseStack.Pose pose = poseStack.last();
        int[] lightmap = {packedLight, packedLight, packedLight, packedLight};
        float[] brightness = {1.0F, 1.0F, 1.0F, 1.0F};

        // Render direzioni
        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            for (BakedQuad quad : model.getQuads(state, direction, random, ModelData.EMPTY, null)) {
                consumer.putBulkData(pose, quad, brightness, 1.0f, 1.0f, 1.0f, 1.0f, lightmap, packedOverlay, false);
            }
        }

        // Render quads generali
        random.setSeed(42L);
        for (BakedQuad quad : model.getQuads(state, null, random, ModelData.EMPTY, null)) {
            consumer.putBulkData(pose, quad, brightness, 1.0f, 1.0f, 1.0f, 1.0f, lightmap, packedOverlay, false);
        }
    }

    @Override
    public boolean shouldRenderOffScreen(XpCrystallizerEntity entity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 64;
    }
}