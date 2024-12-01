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

import net.gabriele333.fmtt.fmtt;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = fmtt.MOD_ID)
public class AdventCalendar {

    @SubscribeEvent
    public static void onPlayerJoinServer(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getPlayer();
        int day = getCurrentDayOfMonth();
        for (int i = 1; i <= day; i++) {
            long qID = QuestID.IDS[i];
            triggerQuest(player, qID);

        }
    }



    public static int getCurrentDayOfMonth() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getDayOfMonth();
    }

    public static void triggerQuest(ServerPlayer player, long questID) {
        //Shit to trigger the quest
    }

}
