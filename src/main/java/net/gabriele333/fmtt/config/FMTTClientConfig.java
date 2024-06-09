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
