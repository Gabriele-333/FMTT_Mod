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
package net.gabriele333.fmtt.network.packet;

import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXp;
import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXpProvider;
import net.gabriele333.fmtt.item.FMTTItems;
import net.gabriele333.fmtt.network.FMTTNetwork;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FMTTXpC2SP {
    public FMTTXpC2SP() {

    }
    public FMTTXpC2SP(FriendlyByteBuf buf) {
    }
    public  void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->{
            //FMTTNetwork.register();
            ServerPlayer player = context.getSender();
            assert player != null;
            ItemStack itemStack = player.getMainHandItem();
            if (itemStack.getItem() == FMTTItems.FMTT_XP_ITEM.get()) {
                itemStack.shrink(1);
            }
            player.getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(playerfmttxp -> {
                playerfmttxp.addPlayerFMTTXP(1);
            });

            player.getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(playerfmttxp -> {
                ResourceLocation id = getResourceLocation(playerfmttxp);

                if (id != null) {
                    Advancement advancement = player.getServer().getAdvancements().getAdvancement(id);
                    if (advancement != null) {
                        PlayerAdvancements playerAdvancements = player.getAdvancements();
                        if (!playerAdvancements.getOrStartProgress(advancement).isDone()) {
                            for (String criterion : playerAdvancements.getOrStartProgress(advancement).getRemainingCriteria()) {
                                playerAdvancements.award(advancement, criterion);
                            }
                        }
                    }
                }
            });


        });
        return true;
    }

    @Nullable
    private static ResourceLocation getResourceLocation(PlayerFMTTXp playerfmttxp) {
        int xp = playerfmttxp.getPlayerFMTTXP();
        ResourceLocation id = null;

        if (xp >= 30){
            id = new ResourceLocation("fmtt", "30_fmtt_xp");
        } else if (xp >= 20) {
            id = new ResourceLocation("fmtt", "20_fmtt_xp");
        } else if (xp >= 10) {
            id = new ResourceLocation("fmtt", "10_fmtt_xp");
        } else if (xp >= 5) {
            id = new ResourceLocation("fmtt", "5_fmtt_xp");
        } else if (xp >= 1) {
            id = new ResourceLocation("fmtt", "fmtt_xp");
        }
        return id;
    }
}
