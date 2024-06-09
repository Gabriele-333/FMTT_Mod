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
 */
package net.gabriele333.fmtt.FMTTXP;

import net.minecraft.nbt.CompoundTag;

public class PlayerFMTTXp {
    private int playerfmttxp;

    public int getPlayerFMTTXP() {
        return playerfmttxp;
    }
    public void addPlayerFMTTXP(int add){
        this.playerfmttxp = playerfmttxp + add;
    }
    public void removePlayerFMTTXP(int remove){
        this.playerfmttxp = playerfmttxp - remove;

    }
    public void setPlayerFMTTXP(int set){
        this.playerfmttxp = set;
    }

    public void copyFrom(PlayerFMTTXp source) {
        this.playerfmttxp = source.playerfmttxp;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("playerfmttxp", playerfmttxp);
    }
    public void loadNBTData(CompoundTag nbt) {
        playerfmttxp = nbt.getInt("playerfmttxp");
    }
}
