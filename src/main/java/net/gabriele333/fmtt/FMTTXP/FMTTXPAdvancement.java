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
/*package net.gabriele333.fmtt.FMTTXP;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.world.entity.player.Player;


public class FMTTXPAdvancement {

    public static void FMTTXPTriggerAdvancement(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        serverPlayer.getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(playerfmttxp -> {
            int xp = playerfmttxp.getPlayerFMTTXP();
            ResourceLocation id = null;

            if (xp >= 20) {
                id = new ResourceLocation("fmtt", "20_fmtt_xp");
            } else if (xp >= 10) {
                id = new ResourceLocation("fmtt", "10_fmtt_xp");
            }

            if (id != null) {
                Advancement advancement = serverPlayer.getServer().getAdvancements().getAdvancement(id);
                if (advancement != null) {
                    PlayerAdvancements playerAdvancements = serverPlayer.getAdvancements();
                    if (!playerAdvancements.getOrStartProgress(advancement).isDone()) {
                        for (String criterion : playerAdvancements.getOrStartProgress(advancement).getRemainingCriteria()) {
                            playerAdvancements.award(advancement, criterion);
                        }
                    }
                }
            }
        });
    }
}
*/