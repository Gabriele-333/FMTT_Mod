package net.gabriele333.fmtt.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class FMTTCommonConfig {
    public final ForgeConfigSpec spec;

    public FMTTCommonConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.spec = builder.build();
    }
}