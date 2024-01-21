package net.gabriele333.fmtt.config;

import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec;

public class FMTTClientConfig {

    public final MiscSettings misc;
    public final ForgeConfigSpec spec;

    public FMTTClientConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.misc = new MiscSettings(builder);
        this.spec = builder.build();
    }
    public static class MiscSettings {
        public final BooleanValue NewVersionMsg;
        public static ForgeConfigSpec.ConfigValue<String> Version = null;


        public MiscSettings(ForgeConfigSpec.Builder builder) {
            builder.comment("Misc Settings").push("misc");

            this.NewVersionMsg = builder.comment("Receive a message in chat when a new version of the Modpack is released")
                    .define("NewVersionMsg", true);
            Version = builder.comment("The version of the modpack    !DON'T TOUCH!")
                    .define("Version", "1.0.0");

            builder.pop();
        }
    }

















    /*public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<String> Version;

    static {
        BUILDER.push("FMTT config file");
        Version = BUILDER.comment("This is the version of the modpack").define("Version", "1.2.45");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
*/
}
