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
