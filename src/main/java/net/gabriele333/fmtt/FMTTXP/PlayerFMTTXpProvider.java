package net.gabriele333.fmtt.FMTTXP;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerFMTTXpProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerFMTTXp> player_fmtt_xp = CapabilityManager.get(new CapabilityToken<PlayerFMTTXp>() {});
    private PlayerFMTTXp playerfmttxp = null;
    private final LazyOptional<PlayerFMTTXp> optional = LazyOptional.of(this::createplayerfmttxp);
    private PlayerFMTTXp createplayerfmttxp() {
        if(this.playerfmttxp == null) {
            this.playerfmttxp = new PlayerFMTTXp();
        }
        return this.playerfmttxp;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == player_fmtt_xp) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createplayerfmttxp().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createplayerfmttxp().loadNBTData(nbt);

    }
}
