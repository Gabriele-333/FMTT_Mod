package net.gabriele333.fmtt.FMTTXP;

import net.minecraft.nbt.CompoundTag;

public class PlayerFMTTXP {
    private int PlayerFMTTXP;

    public int getPlayerFMTTXP() {
        return PlayerFMTTXP;
    }
    public void addPlayerFMTTXP(int add){
        this.PlayerFMTTXP = PlayerFMTTXP + add;
    }
    public void copyFrom(PlayerFMTTXP source) {
        this.PlayerFMTTXP = source.PlayerFMTTXP;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("PlayerFMTTXP", PlayerFMTTXP);
    }
    public void loadNBTData(CompoundTag nbt) {
        PlayerFMTTXP = nbt.getInt("PlayerFMTTXP");
    }
}
