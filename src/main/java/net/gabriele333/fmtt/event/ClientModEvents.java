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

import net.gabriele333.fmtt.fmtt;
import net.gabriele333.fmtt.item.FMTTItems;
import net.gabriele333.fmtt.network.serverbound.FMTTRewardPacket;
import net.gabriele333.fmtt.network.serverbound.FMTTXpPacket;
import net.gabriele333.fmtt.network.serverbound.SleepDreamPacket;
import net.gabriele333.gabrielecore.network.ServerboundPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;
import net.neoforged.neoforge.network.PacketDistributor;


import static net.gabriele333.fmtt.fmtt.LOGGER;
import static net.gabriele333.fmtt.fmtt.MOD_ID;


public class ClientModEvents {

    private static final ResourceLocation SLEEP_IMAGE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/ui/sleep_image.png");
    private static boolean showSleepImage = false;
    private static int fadeTimer = 0;
    private static final int MAX_FADE_TIME = 25; // 3 secondi (60 tick)
    private static float currentExposure = 0.7f;

    public static void setShowSleepImage(boolean value) {
        showSleepImage = value;
    }

    public static void resetFadeTimer() {
        fadeTimer = 0;
    }

    @EventBusSubscriber(modid = fmtt.MOD_ID, value = Dist.CLIENT)
    public static class ForgeEvents {



        @SubscribeEvent
        public static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
            if (event.getLevel().isClientSide()) {
                ItemStack itemStack = event.getItemStack();

                if (itemStack.getItem() == FMTTItems.FMTT_XP_ITEM.get()) {
                    ServerboundPacket message = new FMTTXpPacket();
                    PacketDistributor.sendToServer(message);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                    return;
                }

                if (itemStack.getItem() == FMTTItems.FMTT_REWARD_ITEM.get()) {
                    ServerboundPacket message = new FMTTRewardPacket();
                    PacketDistributor.sendToServer(message);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                    return;
                }
            }
        }


        @SubscribeEvent
        public static void onPlayerWakeUpEvent(PlayerWakeUpEvent event) {
            if (event.getEntity() instanceof Player player && player.level().isClientSide) {
                triggerSleepImage(player);
            }
        }

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            if (showSleepImage) {
                if (fadeTimer < MAX_FADE_TIME) {
                    fadeTimer++;
                } else {
                    showSleepImage = false;
                    fadeTimer = 0;
                    LOGGER.info("Sleep image fade completed");
                }
            }
        }

        private static int originalChatY = -1;
        @SubscribeEvent
        public static void onRenderChatOverlay(CustomizeGuiOverlayEvent.Chat event) {
            if (showSleepImage) {
                // Salva la posizione originale solo al primo spostamento
                if (originalChatY == -1) {
                    originalChatY = event.getPosY();
                }
                event.setPosY(originalChatY + 500); // Sposta la chat in basso
            } else if (originalChatY != -1) {
                event.setPosY(originalChatY); // Ripristina la posizione originale
                originalChatY = -1; // Resetta il flag
            }
        }
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void onRenderHud(RenderGuiEvent.Post event) {
            if (showSleepImage && !Minecraft.getInstance().options.hideGui) {
                float alpha = calculateAlpha();
                renderSleepImage(event.getGuiGraphics(), Minecraft.getInstance(), alpha);
            }
        }

        private static float calculateAlpha() {
            if (fadeTimer < 20) {
                return (fadeTimer / 20.0f) * currentExposure;
            } else if (fadeTimer > MAX_FADE_TIME - 20) {
                return ((MAX_FADE_TIME - fadeTimer) / 20.0f) * currentExposure;
            }
            return currentExposure;
        }

        private static void renderSleepImage(GuiGraphics guiGraphics, Minecraft mc, float alpha) {
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();

            // Calcola le dimensioni mantenendo le proporzioni originali
            float imageRatio = 2560f / 1369f;
            float screenRatio = (float)screenWidth / (float)screenHeight;

            int renderWidth, renderHeight;
            int offsetX = 0, offsetY = 0;

            if (screenRatio > imageRatio) {
                // Schermo più largo dell'immagine (black bars ai lati)
                renderHeight = screenHeight;
                renderWidth = (int)(renderHeight * imageRatio);
                offsetX = (screenWidth - renderWidth) / 2;
            } else {
                // Schermo più alto dell'immagine (black bars sopra/sotto)
                renderWidth = screenWidth;
                renderHeight = (int)(renderWidth / imageRatio);
                offsetY = (screenHeight - renderHeight) / 2;
            }

            // Renderizza l'immagine mantenendo le proporzioni
            guiGraphics.setColor(1.0f, 1.0f, 1.0f, alpha);
            guiGraphics.blit(SLEEP_IMAGE, offsetX, offsetY, renderWidth, renderHeight, 0, 0, 2560, 1369, 2560, 1369);
            guiGraphics.setColor(1.0f, 1.0f, 1.0f, calculateAlpha());

            // Opzionale: riempire le aree vuote con nero
            if (offsetX > 0) {
                guiGraphics.fill(0, 0, offsetX, screenHeight, 0xFF000000);
                guiGraphics.fill(screenWidth - offsetX, 0, screenWidth, screenHeight, 0xFF000000);
            }
            if (offsetY > 0) {
                guiGraphics.fill(0, 0, screenWidth, offsetY, 0xFF000000);
                guiGraphics.fill(0, screenHeight - offsetY, screenWidth, screenHeight, 0xFF000000);
            }
        }

        private static void triggerSleepImage(Player player) {

            ServerboundPacket message = new SleepDreamPacket();
            PacketDistributor.sendToServer(message);

        }

    }
}