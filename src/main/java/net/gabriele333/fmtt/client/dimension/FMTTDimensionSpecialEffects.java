package net.gabriele333.fmtt.client.dimension;/*
 * This file is part of From Magic To Tech.
 * Copyright (c) 2025, Gabriele_333. All rights reserved.
 *
 * This file includes code copied and modified from the Ad Astra project,
 * originally licensed under the MIT License.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Original source: https://github.com/terrarium-earth/Ad-Astra
 */

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class FMTTDimensionSpecialEffects extends DimensionSpecialEffects {

    private final PlanetRenderer renderer;
    private final FMTTSkyRenderer skyRenderer;

    public FMTTDimensionSpecialEffects(PlanetRenderer renderer) {
        super(192, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
        this.renderer = renderer;
        this.skyRenderer = new FMTTSkyRenderer(renderer);
    }


    /**
     * Renders the clouds of this dimension.
     *
     * @return true to prevent vanilla cloud rendering
     */

    //if (level.effects().renderClouds(level, ticks, partialTick, poseStack, camX, camY, camZ, frustumMatrix, projectionMatrix))
    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f frustumMatrix, Matrix4f projectionMatrix) {
        return renderer.customClouds();
    }

    /**
     * Renders the sky of this dimension.
     *
     * @return true to prevent vanilla sky rendering
     */
    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, Matrix4f frustumMatrix, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        skyRenderer.render(level, partialTick, frustumMatrix, camera, projectionMatrix, isFoggy, setupFog);
        return renderer.customSky();
    }
    /**
     * Renders the snow and rain effects of this dimension.
     *
     * @return true to prevent vanilla snow and rain rendering
     */
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
        return renderer.customWeather();
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float brightness) {
        if (renderer.hasFog()) {
            return fogColor.multiply(
                    brightness * 0.94 + 0.06,
                    brightness * 0.94 + 0.06,
                    brightness * 0.91 + 0.09);
        }
        return Vec3.ZERO;
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return renderer.hasThickFog();
    }

    @Nullable
    @Override
    public float[] getSunriseColor(float timeOfDay, float partialTicks) {
        // Prevent the FogRenderer from rendering the sunrise if the sun isn't setting in the west.
        if (renderer.sunriseAngle() != 0) return null;
        return getSunriseColor(timeOfDay, partialTicks, renderer.sunriseColor());
    }

    @Nullable
    public static float[] getSunriseColor(float timeOfDay, float partialTicks, int sunColor) {
        float timeCos = Mth.cos(timeOfDay * (float) (Math.PI * 2));
        if (timeCos >= -0.4f && timeCos <= 0.4f) {
            float time = timeCos / 0.4f * 0.5f + 0.5f;
            float alpha = 1 - (1 - Mth.sin(time * (float) Math.PI)) * 0.99F;
            alpha *= alpha;
            var rgba = new float[4];
            rgba[0] = time * 0.3f + FastColor.ARGB32.red(sunColor) / 255f * 0.7f;
            rgba[1] = time * time * 0.7f + FastColor.ARGB32.green(sunColor) / 255f * 0.5f;
            rgba[2] = FastColor.ARGB32.blue(sunColor) / 255f * 0.6f;
            rgba[3] = alpha;
            return rgba;
        } else {
            return null;
        }
    }

    public PlanetRenderer renderer() {
        return renderer;
    }
}