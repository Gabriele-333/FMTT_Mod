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



import appeng.core.definitions.AEBlocks;
import net.gabriele333.fmtt.commands.FMTTXpGet;
import net.gabriele333.fmtt.fmtt;
import net.gabriele333.fmtt.util.ftbquest.QuestID;
import net.gabriele333.gabrielecore.utils.ftbquest.FTBQuestTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.command.ConfigCommand;
import org.checkerframework.checker.units.qual.A;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


import static net.gabriele333.fmtt.fmtt.LOGGER;



@EventBusSubscriber(modid = fmtt.MOD_ID)
public class ModEvents {


    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new FMTTXpGet(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player && event.getState().getBlock() == AEBlocks.MYSTERIOUS_CUBE.block()) {
            FTBQuestTrigger.triggerQuest(player, QuestID.AE2IDS[0]);
        }
    }



    @SubscribeEvent
    public static void onServerAboutToStart(final RecipesUpdatedEvent event) {
        RecipeManager recipeManager = event.getRecipeManager();
        Collection<RecipeHolder<?>> recipes = recipeManager.getRecipes();
        Path configFile = Paths.get(Minecraft.getInstance().gameDirectory.getAbsolutePath(), "config", "FMTTRewardItems.txt");

        Set<String> writtenItems = new HashSet<>();

        try (BufferedWriter writer = Files.newBufferedWriter(configFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (RecipeHolder<?> recipeHolder : recipes) {
                Recipe<?> recipe = recipeHolder.value();
                assert Minecraft.getInstance().level != null;
                ItemStack resultItem = recipe.getResultItem(Minecraft.getInstance().level.registryAccess());

                if (resultItem == null || resultItem.isEmpty()) { continue; }
                    ResourceLocation itemResourceLocation = BuiltInRegistries.ITEM.getKey(resultItem.getItem());

                    if (!itemResourceLocation.equals("minecraft:air")) {
                        String itemString = itemResourceLocation.toString();

                        if (writtenItems.add(itemString)) {
                            writer.write(itemString);
                            writer.newLine();
                        }
                    }

            }
            LOGGER.info("FMTTRewardItems.txt updated.");
        } catch (IOException e) {
            LOGGER.error("Error while writing in FMTTRewardItems.txt", e);
        }
    }

}
