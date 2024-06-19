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


import net.neoforged.neoforge.common.ModConfigSpec;

public class FMTTConfig {

    public FMTTConfig() {
        final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    }

    public static class MiscSettings {
        public final ModConfigSpec.BooleanValue NewVersionMsg;
        public static ModConfigSpec.ConfigValue<String> Version;

        public MiscSettings(ModConfigSpec.Builder builder) {
            builder.comment("Misc Settings").push("misc");

            this.NewVersionMsg = builder.comment("Receive a message in chat when a new version of the Modpack is released")
                    .define("NewVersionMsg", true);
            Version = builder.comment("The version of the modpack    !DON'T TOUCH!")
                    .define("Version", "1.21"); // Update version to 1.21
        }
    }
}