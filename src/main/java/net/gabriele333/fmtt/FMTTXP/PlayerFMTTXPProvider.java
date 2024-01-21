package net.gabriele333.fmtt.FMTTXP;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerFMTTXPProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerFMTTXP> Player_FMTT_XP = CapabilityManager.get(new CapabilityToken<PlayerFMTTXP>() {});
    private PlayerFMTTXP PlayerFMTTXP = null;
    private final LazyOptional<PlayerFMTTXP> optional = LazyOptional.of(this::createPlayerFMTTXP);
    private PlayerFMTTXP createPlayerFMTTXP() {
        if(this.PlayerFMTTXP == null) {
            this.PlayerFMTTXP = new PlayerFMTTXP();
        }
        return this.PlayerFMTTXP;
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == Player_FMTT_XP) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerFMTTXP().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerFMTTXP().loadNBTData(nbt);

    }
}
