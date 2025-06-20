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




import net.gabriele333.gabrielecore.network.CustomPayload;
import net.gabriele333.gabrielecore.network.ServerboundPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static net.gabriele333.fmtt.fmtt.LOGGER;


public record FMTTRewardPacket() implements ServerboundPacket {

    public static final StreamCodec<RegistryFriendlyByteBuf, FMTTRewardPacket> STREAM_CODEC = StreamCodec
            .ofMember(
                    FMTTRewardPacket::write,
                    FMTTRewardPacket::decode);

    public static final Type<FMTTRewardPacket> TYPE = CustomPayload.createType("fmtt_reward_packet");


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
        ItemStack randomItemStack = new ItemStack(randomItem);


        if (isInventoryFull(player)) {
            player.sendSystemMessage(Component.literal("Your inventory is full").withStyle(ChatFormatting.RED));
        } else {
            ItemStack rewardItem = player.getMainHandItem();
            rewardItem.shrink(1);
            player.addItem(randomItemStack);

        }

        LOGGER.info("Belin");

    }

    private boolean isValid(Item itemV) {

        Path configFile = Paths.get(System.getProperty("user.dir"), "config", "FMTTRewardItems.txt");
        List<String> validItems;


        try {
            validItems = Files.readAllLines(configFile);
        } catch (IOException e) {
            LOGGER.error("Error while reading the file: {}", configFile, e);
            return false;
        }


        String itemName = BuiltInRegistries.ITEM.getKey(itemV).toString();

        return validItems.stream().anyMatch(line -> line.trim().equalsIgnoreCase(itemName));
    }

    public static boolean isInventoryFull(ServerPlayer player) {
        Inventory inventory = player.getInventory();  // Ottieni l'inventario del giocatore

        // Controlla tutti gli slot dell'inventario
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);

            // Se c'è uno slot vuoto, l'inventario non è pieno
            if (stack.isEmpty()) {
                return false;
            }
        }

        // Se nessuno slot è vuoto, l'inventario è pieno
        return true;
    }

}