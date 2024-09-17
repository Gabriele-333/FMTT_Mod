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
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;


import java.util.Random;


import static net.gabriele333.fmtt.fmtt.LOGGER;

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

        RecipeManager recipeManager = player.getServer().getRecipeManager();

        HolderLookup.Provider registries = player.getServer().registryAccess();

        Item randomItem;
        Random random = new Random();


        do {
            randomItem = BuiltInRegistries.ITEM.byId(random.nextInt(BuiltInRegistries.ITEM.size()));
        } while (!hasRecipe(randomItem, recipeManager, registries));


        assert player != null;
        ItemStack itemStack = player.getMainHandItem();

        ItemStack randomItemStack = new ItemStack(randomItem);


        if (player.getInventory().add(randomItemStack)) {
            if (itemStack.getItem() == FMTTItems.FMTT_REWARD_ITEM.get()) {
                itemStack.shrink(1);
            }
        } else {
            player.sendSystemMessage(Component.literal("Your inventory is full").withStyle(ChatFormatting.RED));
        }

        // Log di debug
        LOGGER.info("Belin");
    }



    private boolean hasRecipe(Item item, RecipeManager recipeManager, HolderLookup.Provider registries) {

        for (RecipeHolder<?> recipeHolder : recipeManager.getRecipes()) {
            Recipe<?> recipe = recipeHolder.value();

            if (recipe.getResultItem(registries).getItem() == item) {
                return true;
            }
        }
        return false;
    }
}
