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

 THIS CODE IS COPIED AND MODIFIED FROM: https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/server/services/compass/CompassRegion.java
 */

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import net.gabriele333.fmtt.data.FMTTSavedData;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;



final class FMTTCompassRegion extends FMTTSavedData {

    private static final SavedData.Factory<FMTTCompassRegion> FACTORY = new Factory<>(
            FMTTCompassRegion::new,
            null);


    private static final int CHUNKS_PER_REGION = 1024;

    private static final int BITMAP_LENGTH = CHUNKS_PER_REGION * CHUNKS_PER_REGION;


    private final Map<Integer, BitSet> sections = new HashMap<>();


    private static String getRegionSaveName(int regionX, int regionZ) {
        return "fmtt_compass_" + regionX + "_" + regionZ;
    }

    /**
     * Retrieve the compass region that serves the given chunk position.
     */
    public static FMTTCompassRegion get(ServerLevel level, ChunkPos chunkPos) {
        Objects.requireNonNull(level, "level");
        Objects.requireNonNull(chunkPos, "chunkPos");

        var regionX = chunkPos.x / CHUNKS_PER_REGION;
        var regionZ = chunkPos.z / CHUNKS_PER_REGION;

        return level.getDataStorage().computeIfAbsent(
                FACTORY,
                getRegionSaveName(regionX, regionZ));
    }



    @Override
    public CompoundTag save(CompoundTag compound, HolderLookup.Provider registries) {
        for (var entry : sections.entrySet()) {
            var key = "section" + entry.getKey();
            if (entry.getValue().isEmpty()) {
                continue;
            }
            compound.putByteArray(key, entry.getValue().toByteArray());
        }
        return compound;
    }

    boolean hasFMTTBlock(int cx, int cz) {
        var bitmapIndex = getBitmapIndex(cx, cz);
        for (BitSet bitmap : sections.values()) {
            if (bitmap.get(bitmapIndex)) {
                return true;
            }
        }
        return false;
    }

    boolean hasFMTTBlock(int cx, int cz, int sectionIndex) {
        var bitmapIndex = getBitmapIndex(cx, cz);
        var section = sections.get(sectionIndex);
        if (section != null) {
            return section.get(bitmapIndex);
        }
        return false;
    }

    void setHasFMTTBlock(int cx, int cz, int sectionIndex, boolean hasFMTTBlock) {
        var bitmapIndex = getBitmapIndex(cx, cz);
        var section = sections.get(sectionIndex);
        if (section == null) {
            if (hasFMTTBlock) {
                section = new BitSet(BITMAP_LENGTH);
                section.set(bitmapIndex);
                sections.put(sectionIndex, section);
                setDirty();
            }
        } else {
            if (section.get(bitmapIndex) != hasFMTTBlock) {
                setDirty();
            }
            // There already was data on this y-section in this region
            if (!hasFMTTBlock) {
                section.clear(bitmapIndex);
                if (section.isEmpty()) {
                    sections.remove(sectionIndex);
                }
                setDirty();
            } else {
                section.set(bitmapIndex);
            }
        }
    }

    private static int getBitmapIndex(int cx, int cz) {
        cx &= CHUNKS_PER_REGION - 1;
        cz &= CHUNKS_PER_REGION - 1;
        return cx + cz * CHUNKS_PER_REGION;
    }

}
