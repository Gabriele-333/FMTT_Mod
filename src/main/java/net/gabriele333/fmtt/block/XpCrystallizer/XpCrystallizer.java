package net.gabriele333.fmtt.block.XpCrystallizer;/*
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



import appeng.client.render.effects.ParticleTypes;
import com.mojang.serialization.MapCodec;
import net.gabriele333.fmtt.server.services.FMTTCompassService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class XpCrystallizer extends BaseEntityBlock {
    public static final MapCodec<XpCrystallizer> CODEC = simpleCodec(XpCrystallizer::new);
    private static final VoxelShape SHAPE = Block.box(
            0.0D, 0.0D, 0.0D,  // minX, minY, minZ
            16.0D, 22.0D, 16.0D // maxX, maxY, maxZ (in pixel units)
    );

    public XpCrystallizer(Properties props) {
        super(props);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (level instanceof ServerLevel serverLevel) {
            FMTTCompassService.notifyBlockChange(serverLevel, pos);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (newState.getBlock() == state.getBlock()) {
            return; // Just a block state change
        }

        super.onRemove(state, level, pos, newState, isMoving);

        if (level instanceof ServerLevel serverLevel) {
            FMTTCompassService.notifyBlockChange(serverLevel, pos);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new XpCrystallizerEntity(pos, state);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!(level instanceof ClientLevel clientLevel)) return;

        double x = pos.getX() + 0.5;
        double y = pos.getY() + 1.1875;
        double z = pos.getZ() + 0.5;

        // Aggiungi una leggera variazione casuale
        double offsetX = (random.nextDouble() - 0.5) * 0.1;
        double offsetZ = (random.nextDouble() - 0.5) * 0.1;

        // Particella custom: LIGHTNING
        Minecraft.getInstance().particleEngine.createParticle(
                ParticleTypes.LIGHTNING, x + offsetX, y, z + offsetZ, 0.0D, 0.01D, 0.0D
        );
    }
}
