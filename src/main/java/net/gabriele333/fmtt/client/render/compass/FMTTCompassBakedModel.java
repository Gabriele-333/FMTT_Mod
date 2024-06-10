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
 *
 *
 *
 *
 * THIS CODE IS COPIED AND MODIFIED FROM https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/forge/1.20.1/src/main/java/appeng/client/render/model/MeteoriteCompassBakedModel.java
 */

package net.gabriele333.fmtt.client.render.compass;


import java.util.ArrayList;
import java.util.List;



import appeng.thirdparty.fabric.RenderContext;
import appeng.thirdparty.fabric.MutableQuadView;

import net.gabriele333.fmtt.compass.CompassManager;
import net.gabriele333.fmtt.compass.CompassResult;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;





public class FMTTCompassBakedModel implements IDynamicBakedModel {
    // Rotation is expressed as radians
    public static final ModelProperty<Float> ROTATION = new ModelProperty<>();

    private final BakedModel base;

    private final BakedModel pointer;

    private float fallbackRotation = 0;

    public FMTTCompassBakedModel(BakedModel base, BakedModel pointer) {
        this.base = base;
        this.pointer = pointer;
    }

    public BakedModel getPointer() {
        return pointer;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand,
                                    ModelData extraData, RenderType renderType) {
        float rotation;
        // Get rotation from the special block state
        Float rotationFromData = extraData.get(ROTATION);
        if (rotationFromData != null) {
            rotation = rotationFromData;
        } else {
            // This is used to render a compass pointing in a specific direction when being
            // held in hand
            rotation = this.fallbackRotation;
        }

        // This is used to render a compass pointing in a specific direction when being
        // held in hand
        // Set up the rotation around the Y-axis for the pointe

        RenderContext.QuadTransform transform = quad -> {
            Quaternionf quaternion = new Quaternionf().rotationY(this.fallbackRotation);
            Vector3f pos = new Vector3f();
            for (int i = 0; i < 4; i++) {
                quad.copyPos(i, pos);
                pos.add(-0.5f, -0.5f, -0.5f);
                pos.rotate(quaternion);
                pos.add(0.5f, 0.5f, 0.5f);
                quad.pos(i, pos);
            }
            return true;
        };

        // Pre-compute the quad count to avoid list resizes
        List<BakedQuad> quads = new ArrayList<>(this.base.getQuads(state, side, rand, extraData, renderType));
        // We'll add the pointer as "sideless" to the item rendering when state is null
        if (side == null && state == null) {
            var quadView = MutableQuadView.getInstance();
            for (BakedQuad bakedQuad : this.pointer.getQuads(state, side, rand, extraData, renderType)) {
                quadView.fromVanilla(bakedQuad, null);
                transform.transform(quadView);
                quads.add(quadView.toBlockBakedQuad());
            }
        }

        return quads;

    }

    @Override
    public boolean useAmbientOcclusion() {
        return this.base.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return this.base.getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return this.base.getTransforms();
    }

    @Override
    public ItemOverrides getOverrides() {

        return new ItemOverrides() {
            @Override
            public BakedModel resolve(BakedModel originalModel, ItemStack stack, @Nullable ClientLevel level,
                                      @Nullable LivingEntity entity, int seed) {
                // FIXME: This check prevents compasses being held by OTHERS from getting the
                // rotation, BUT do we actually still need this???
                if (level != null && entity instanceof LocalPlayer) {
                    Player player = (Player) entity;

                    float offRads = (float) (player.getYRot() / 180.0f * (float) Math.PI + Math.PI);

                    FMTTCompassBakedModel.this.fallbackRotation = getAnimatedRotation(player.blockPosition(), true,
                            offRads);
                } else {
                    FMTTCompassBakedModel.this.fallbackRotation = getAnimatedRotation(null, false, 0);
                }

                return originalModel;
            }
        };
    }


    public static float getAnimatedRotation(@Nullable BlockPos pos, boolean prefetch, float playerRotation) {

        // Only query for a meteor position if we know our own position
        if (pos != null) {
            CompassResult cr = CompassManager.INSTANCE.getCompassDirection(0, pos.getX(), pos.getY(), pos.getZ());

            // Prefetch meteor positions from the server for adjacent blocks, so they are
            // available more quickly when
            // we're moving
            if (prefetch) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        CompassManager.INSTANCE.getCompassDirection(0, pos.getX() + i - 1, pos.getY(),
                                pos.getZ() + j - 1);
                    }
                }
            }

            if (cr.isValidResult()) {
                if (cr.isSpin()) {
                    long timeMillis = System.currentTimeMillis();
                    // .5 seconds per full rotation
                    timeMillis %= 500;
                    return timeMillis / 500.f * (float) Math.PI * 2;
                } else {
                    return (float) cr.getRad() + playerRotation;
                }
            }
        }

        long timeMillis = System.currentTimeMillis();
        // 3 seconds per full rotation
        timeMillis %= 3000;
        return timeMillis / 3000.f * (float) Math.PI * 2;
    }


}

