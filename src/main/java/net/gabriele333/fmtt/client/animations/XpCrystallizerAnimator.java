package net.gabriele333.fmtt.client.animations;/*
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
import com.mojang.math.Axis;

public class XpCrystallizerAnimator {

    private static float getRotationAngle(float partialTick, float speedMultiplier) {
        return ((System.currentTimeMillis() % 36000) / 100f) * speedMultiplier; //TOFIX: Change System... to tick
    }

    public static void rotateRot1(PoseStack poseStack, float partialTick) {
        float angle = getRotationAngle(partialTick, 1.0f);
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));
    }

    public static void rotateRot2(PoseStack poseStack, float partialTick) {
        float angle = getRotationAngle(partialTick, -4.0f); // Opposta direzione
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));
    }

    public static void rotateRing(PoseStack poseStack, float partialTick, float speedMultiplier) {
        float angle = getRotationAngle(partialTick, 1.0f);
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));
    }
}