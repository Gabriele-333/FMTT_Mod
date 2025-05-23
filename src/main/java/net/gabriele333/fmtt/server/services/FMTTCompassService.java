package net.gabriele333.fmtt.server.services;/*
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


 THIS CODE IS COPIED AND MODIFIED FROM: https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/server/services/compass/CompassService.java
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import net.gabriele333.fmtt.block.FMTTBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;


public final class FMTTCompassService {

    public record DirectionQuery(ServerLevel level, int cx, int cz, int maxRange) {
    }

    /**
     * @param hasResult true if found a target
     * @param spin      true if should spin
     * @param radians
     * @param distance
     */
    public record Result(boolean hasResult, boolean spin, double radians, double distance) {
        public static final Result ON_THE_SPOT = new Result(true, true, -999, 0);
        public static final Result INDETERMINATE = new Result(false, true, -999, 999);
    }

    // We use this basic cache to prevent client-side spamming, although the client can request arbitrary
    // chunk positions and range, if malicious.
    private static final LoadingCache<DirectionQuery, Result> DIRECTION_CACHE = CacheBuilder.newBuilder()
            .maximumSize(100)
            .weakKeys()
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<>() {
                @Override
                public Result load(DirectionQuery directionQuery) {
                    return calculateCompassDirection(directionQuery);
                }
            });

    private static final int CHUNK_SIZE = 16;

    public static Result getDirection(ServerLevel level, ChunkPos chunkPos, int maxRange) {
        return DIRECTION_CACHE.getUnchecked(new DirectionQuery(level, chunkPos.x, chunkPos.z, maxRange));
    }

    private static Result calculateCompassDirection(DirectionQuery query) {
        var cr = FMTTCompassRegion.get(query.level(), new ChunkPos(query.cx(), query.cz()));
        var cx = query.cx();
        var cz = query.cz();

        // Am I standing on it?
        if (cr.hasFMTTBlock(cx, cz)) {
            return Result.ON_THE_SPOT;
        }

        // spiral outward...
        for (int offset = 1; offset < query.maxRange(); offset++) {
            final int minX = cx - offset;
            final int minZ = cz - offset;
            final int maxX = cx + offset;
            final int maxZ = cz + offset;

            int closest = Integer.MAX_VALUE;
            int chosen_x = cx;
            int chosen_z = cz;

            for (int z = minZ; z <= maxZ; z++) {
                if (cr.hasFMTTBlock(minX, z)) {
                    final int closeness = dist(cx, cz, minX, z);
                    if (closeness < closest) {
                        closest = closeness;
                        chosen_x = minX;
                        chosen_z = z;
                    }
                }

                if (cr.hasFMTTBlock(maxX, z)) {
                    final int closeness = dist(cx, cz, maxX, z);
                    if (closeness < closest) {
                        closest = closeness;
                        chosen_x = maxX;
                        chosen_z = z;
                    }
                }
            }

            for (int x = minX + 1; x < maxX; x++) {
                if (cr.hasFMTTBlock(x, minZ)) {
                    final int closeness = dist(cx, cz, x, minZ);
                    if (closeness < closest) {
                        closest = closeness;
                        chosen_x = x;
                        chosen_z = minZ;
                    }
                }

                if (cr.hasFMTTBlock(x, maxZ)) {
                    final int closeness = dist(cx, cz, x, maxZ);
                    if (closeness < closest) {
                        closest = closeness;
                        chosen_x = x;
                        chosen_z = maxZ;
                    }
                }
            }

            if (closest < Integer.MAX_VALUE) {
                return new Result(true, false, rad(cx, cz, chosen_x, chosen_z),
                        dist(cx, cz, chosen_x, chosen_z));
            }
        }

        // didn't find shit...
        return Result.INDETERMINATE;
    }

    public static void updateArea(ServerLevel level, ChunkAccess chunk) {
        var compassRegion = FMTTCompassRegion.get(level, chunk.getPos());

        for (var i = 0; i < level.getSectionsCount(); i++) {
            updateArea(compassRegion, chunk, i);
        }
    }


    public static void notifyBlockChange(ServerLevel level, BlockPos pos) {
        ChunkAccess chunk = level.getChunk(pos);
        var compassRegion = FMTTCompassRegion.get(level, chunk.getPos());
        updateArea(compassRegion, chunk, level.getSectionIndex(pos.getY()));
    }

    private static void updateArea(FMTTCompassRegion compassRegion, ChunkAccess chunk, int sectionIndex) {
        int cx = chunk.getPos().x;
        int cz = chunk.getPos().z;

        var section = chunk.getSections()[sectionIndex];
        if (section.hasOnlyAir()) {
            compassRegion.setHasFMTTBlock(cx, cz, sectionIndex, false);
            return;
        }


        var desiredState = FMTTBlock.XP_CRYSTALLIZER.get().defaultBlockState();
        var blockCount = new AtomicInteger(0);
        section.getStates().count((state, count) -> {
            if (state == desiredState) {
                blockCount.getAndIncrement();
            }
        });
        compassRegion.setHasFMTTBlock(cx, cz, sectionIndex, blockCount.get() > 0);
    }

    private static int dist(int ax, int az, int bx, int bz) {
        final int up = (bz - az) * CHUNK_SIZE;
        final int side = (bx - ax) * CHUNK_SIZE;

        return up * up + side * side;
    }

    private static double rad(int ax, int az, int bx, int bz) {
        final int up = bz - az;
        final int side = bx - ax;

        return Math.atan2(-up, side) - Math.PI / 2.0;
    }

}