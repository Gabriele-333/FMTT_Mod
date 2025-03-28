package net.gabriele333.fmtt.client.models;/*
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
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class CrystalModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("fmtt", "crystal"), "main");
    public final ModelPart maincube;
    public final ModelPart frame1;
    public final ModelPart frame2;

    public CrystalModel(ModelPart root) {
        this.maincube = root.getChild("maincube");
        this.frame1 = root.getChild("frame1");
        this.frame2 = root.getChild("frame2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition maincube = partdefinition.addOrReplaceChild("maincube", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition cube_r1 = maincube.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, -0.7854F, 0.7854F));

        PartDefinition frame1 = partdefinition.addOrReplaceChild("frame1", CubeListBuilder.create().texOffs(0, 42).addBox(-15.0F, -1.0F, 14.0F, 29.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 51).addBox(-14.0F, -1.0F, -14.0F, 29.0F, 2.0F, -1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition cube_r2 = frame1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 48).addBox(-21.0F, -10.0F, -1.0F, 29.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 9.0F, -6.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r3 = frame1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 45).addBox(-19.0F, -10.0F, -1.0F, 29.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, 9.0F, 4.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition frame2 = partdefinition.addOrReplaceChild("frame2", CubeListBuilder.create().texOffs(0, 32).addBox(-18.0F, -1.0F, 17.0F, 35.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 41).addBox(-17.0F, -1.0F, -17.0F, 35.0F, 2.0F, -1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r4 = frame2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 38).addBox(-28.0F, -10.0F, -1.0F, 35.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, 9.0F, -10.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r5 = frame2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 35).addBox(-26.0F, -10.0F, -1.0F, 35.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.0F, 9.0F, 8.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer,
                               int packedLight, int packedOverlay, int packedColor) {


        maincube.render(poseStack, buffer, packedLight, packedOverlay);
        frame1.render(poseStack, buffer, packedLight, packedOverlay);
        frame2.render(poseStack, buffer, packedLight, packedOverlay);
    }
}