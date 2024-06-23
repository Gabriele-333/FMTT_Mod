package net.gabriele333.fmtt;/*
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


 THIS CODE IS COPIED AND MODIFIED FROM: https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/main/src/main/java/appeng/core/worlddata/AESavedData.java
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.mojang.logging.LogUtils;

import net.minecraft.core.HolderLookup;
import org.slf4j.Logger;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.saveddata.SavedData;



public abstract class FMTTSavedData extends SavedData {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void save(File file, HolderLookup.Provider registries) {
        if (!this.isDirty()) {
            return;
        }

        var targetPath = file.toPath().toAbsolutePath();
        var tempFile = targetPath.getParent().resolve(file.getName() + ".temp");

        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("data", this.save(new CompoundTag(), registries));
        NbtUtils.addCurrentDataVersion(compoundTag);
        try {
            // Write to temp file first.
            NbtIo.writeCompressed(compoundTag, tempFile);
            // Try atomic move
            try {
                Files.move(tempFile, targetPath, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ignored) {
                Files.move(tempFile, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException iOException) {
            LOGGER.error("Could not save data {}", this, iOException);
        }
        this.setDirty(false);
    }
}