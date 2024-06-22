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
package net.gabriele333.fmtt.event;
import net.gabriele333.fmtt.client.ModpackVersion;
import net.gabriele333.fmtt.config.FMTTConfig;
import net.gabriele333.fmtt.fmtt;
import net.gabriele333.fmtt.item.FMTTItems;
import net.gabriele333.fmtt.network.ServerboundPacket;
import net.gabriele333.fmtt.network.serverbound.FMTTXpPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;


import java.util.Objects;


public class ClientModEvents {
    private static boolean versionMessageSent = false;


    @EventBusSubscriber(modid = fmtt.MOD_ID, value = Dist.CLIENT)
    public static class ForgeEvents {



        @SubscribeEvent(priority = EventPriority.LOW)
        public static void onPlayerJoinClient(PlayerEvent.PlayerLoggedInEvent event) {
            try {
                if (!versionMessageSent && !Objects.equals(ModpackVersion.main(), FMTTConfig.MiscSettings.Version.get())) {
                    event.getEntity().sendSystemMessage(Component.literal("§bThere is a newer version of the modpack: " + ModpackVersion.main()));
                    versionMessageSent = true;
                }
            }
            catch (Exception e) {
                return;
            }
        }

        @SubscribeEvent
        public static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
            if (event.getSide() == LogicalSide.CLIENT) {
                ItemStack itemStack = event.getItemStack();
                if (itemStack.getItem() == FMTTItems.FMTT_XP_ITEM.get()) {
                    ServerboundPacket message = new FMTTXpPacket();
                    PacketDistributor.sendToServer(message);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                }
            }


        }
    }
}
