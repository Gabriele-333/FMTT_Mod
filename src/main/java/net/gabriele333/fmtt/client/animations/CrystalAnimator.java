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
 * File created on: 28/03/2025
 */

import net.gabriele333.fmtt.client.models.CrystalModelBase;
import net.minecraft.world.entity.Entity;

public class CrystalAnimator {
    // Variabili di animazione
    private float rotationSpeedMain = 4.0f;
    private float rotationSpeedFrame1 = 8.2f;
    private float rotationSpeedFrame2 = 7.8f;
    private float bobSpeed = 0.1f;
    private float bobHeight = 0.0f;

    public void animate(CrystalModelBase<?> model, Entity entity, float partialTicks) {
        float ageInTicks = entity.tickCount + partialTicks;

        // Calcola le rotazioni
        float mainRotX = ageInTicks * 0.01f * rotationSpeedMain;
        float mainRotY = ageInTicks * 0.02f * rotationSpeedMain;
        float mainRotZ = ageInTicks * 0.015f * rotationSpeedMain;

        float frame1RotX = ageInTicks * 0.03f * rotationSpeedFrame1;
        float frame1RotY = ageInTicks * 0.03f * rotationSpeedFrame1;
        float frame1RotZ = ageInTicks * 0.03f * rotationSpeedFrame1;

        float frame2RotX = -ageInTicks * 0.02f * rotationSpeedFrame2;
        float frame2RotY = -ageInTicks * 0.02f * rotationSpeedFrame2;
        float frame2RotZ = -ageInTicks * 0.02f * rotationSpeedFrame2;

        // Effetto bob (movimento su e giù)
        bobHeight = (float)Math.sin(ageInTicks * 0.1f * bobSpeed) * 0.1f;

        // Applica le trasformazioni
        model.maincube.xRot = mainRotX;
        model.maincube.yRot = mainRotY;
        model.maincube.zRot = mainRotZ;
        model.maincube.y = bobHeight;

        model.frame1.xRot = frame1RotX;
        model.frame1.yRot = frame1RotY;
        model.frame1.zRot = frame1RotZ;
        model.frame1.y = bobHeight;

        model.frame2.xRot = frame2RotX;
        model.frame2.yRot = frame2RotY;
        model.frame2.zRot = frame2RotZ;
        model.frame2.y = bobHeight;
    }

    // Metodi per regolare le velocità
    public void setRotationSpeedMain(float speed) {
        this.rotationSpeedMain = speed;
    }

    public void setRotationSpeedFrame1(float speed) {
        this.rotationSpeedFrame1 = speed;
    }

    public void setRotationSpeedFrame2(float speed) {
        this.rotationSpeedFrame2 = speed;
    }

    public void setBobSpeed(float speed) {
        this.bobSpeed = speed;
    }
}