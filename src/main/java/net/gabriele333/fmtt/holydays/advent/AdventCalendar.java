package net.gabriele333.fmtt.holydays.advent;/*
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



import java.time.LocalDate;
import net.gabriele333.fmtt.util.ftbquest.FTBQuestTrigger;
import net.gabriele333.fmtt.util.ftbquest.QuestID;
import net.minecraft.server.level.ServerPlayer;
import net.gabriele333.fmtt.fmtt;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;


@EventBusSubscriber(modid = fmtt.MOD_ID)
public class AdventCalendar {

    @SubscribeEvent
    public static void onPlayerJoinServer(PlayerEvent.PlayerLoggedInEvent event) {
        if(getCurrentMonthOfMonth() == 12){
            if (event.getEntity() instanceof ServerPlayer player) {
                int day = getCurrentDayOfMonth();
                try {
                    for (int i = 0; i <= (day-1); i++) {
                        FTBQuestTrigger.triggerQuest(player, QuestID.AdventIDS[i]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getCurrentDayOfMonth() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getDayOfMonth();
    }
    public static int getCurrentMonthOfMonth() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getMonthValue();
    }
}
