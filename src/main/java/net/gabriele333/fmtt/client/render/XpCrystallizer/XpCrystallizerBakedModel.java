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
 * File created on: 10/05/2025
 */

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class XpCrystallizerBakedModel implements IDynamicBakedModel {

    private final BakedModel main;
    private final BakedModel rot1;
    private final BakedModel rot2;
    private final BakedModel ring1;
    private final BakedModel ring2;
    private final BakedModel ring3;


    public XpCrystallizerBakedModel(BakedModel main, BakedModel rot1, BakedModel rot2, BakedModel ring1, BakedModel ring2, BakedModel ring3) {
        this.main = main;
        this.rot1 = rot1;
        this.rot2 = rot2;
        this.ring1 = ring1;
        this.ring2 = ring2;
        this.ring3 = ring3;
    }
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType) {
        List<BakedQuad> quads = new ArrayList<>();

        quads.addAll(main.getQuads(state, side, rand, extraData, renderType));
        quads.addAll(rot1.getQuads(state, side, rand, extraData, renderType));
        quads.addAll(rot2.getQuads(state, side, rand, extraData, renderType));
        quads.addAll(ring1.getQuads(state, side, rand, extraData, renderType));
        quads.addAll(ring2.getQuads(state, side, rand, extraData, renderType));
        quads.addAll(ring3.getQuads(state, side, rand, extraData, renderType));
        return quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    public BakedModel getMain() {return main;}
    public BakedModel getRot1() {return rot1;}
    public BakedModel getRot2() { return rot2; }
    public BakedModel getRing1() { return ring1; }
    public BakedModel getRing2() { return ring2; }
    public BakedModel getRing3() { return ring3; }


    @Override
    public boolean isGui3d() {
        return false;
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
    public ItemOverrides getOverrides() {
        return null;
    }
    @Override
    public TextureAtlasSprite getParticleIcon() {
        return main.getParticleIcon();
    }
}
