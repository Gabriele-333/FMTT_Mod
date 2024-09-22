package net.gabriele333.fmtt.network.serverbound;/*
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


import net.gabriele333.fmtt.item.FMTTItems;
import net.gabriele333.fmtt.network.CustomFMTTPayload;
import net.gabriele333.fmtt.network.ServerboundPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static net.gabriele333.fmtt.fmtt.LOGGER;
import static net.gabriele333.fmtt.tags.FMTTTags.Items.CRAFTABLE;

public record FMTTRewardPacket() implements ServerboundPacket {

    public static final StreamCodec<RegistryFriendlyByteBuf, FMTTRewardPacket> STREAM_CODEC = StreamCodec
            .ofMember(
                    FMTTRewardPacket::write,
                    FMTTRewardPacket::decode);

    public static final Type<FMTTRewardPacket> TYPE = CustomFMTTPayload.createType("fmtt_reward_packet");


    @Override
    public Type<FMTTRewardPacket> type() {
        return TYPE;
    }

    public static FMTTRewardPacket decode(RegistryFriendlyByteBuf stream) {
        return new FMTTRewardPacket();
    }

    public void write(RegistryFriendlyByteBuf data) {
    }

    public void handleOnServer(ServerPlayer player) {

        Item randomItem;
        Random random = new Random();
        randomItem = BuiltInRegistries.ITEM.byId(random.nextInt(BuiltInRegistries.ITEM.size()));
        do {
            randomItem = BuiltInRegistries.ITEM.byId(random.nextInt(BuiltInRegistries.ITEM.size()));
        } while (!isValid(randomItem));

        assert player != null;
        ItemStack itemStack = player.getMainHandItem();

        ItemStack randomItemStack = new ItemStack(randomItem);
        player.addItem(randomItemStack);
        if (player.getInventory().add(randomItemStack)) {
            if (itemStack.getItem() == FMTTItems.FMTT_REWARD_ITEM.get()) {
                itemStack.shrink(1);
            }
        } else {
            player.sendSystemMessage(Component.literal("Your inventory is full").withStyle(ChatFormatting.RED));
        }

        LOGGER.info("Belin");

    }

    private boolean isValid(Item itemV) {

        Path configFile = Paths.get(System.getProperty("user.dir"), "config", "FMTTRewardItems.txt");
        List<String> validItems;


        try {
            validItems = Files.readAllLines(configFile);
        } catch (IOException e) {
            LOGGER.error("Errore durante la lettura del file di configurazione: {}", configFile, e);
            return false;
        }

        // Ottieni il nome o l'ID dell'item
        String itemName = BuiltInRegistries.ITEM.getKey(itemV).toString();

        // Verifica se l'item è presente nella lista degli item validi
        return validItems.stream().anyMatch(line -> line.trim().equalsIgnoreCase(itemName));
    }

}