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



import net.gabriele333.fmtt.commands.FMTTXpGet;
import net.gabriele333.fmtt.fmtt;
import net.gabriele333.fmtt.fmttClient;
import net.gabriele333.fmtt.tags.FMTTBlockTagsProvider;
import net.gabriele333.fmtt.tags.FMTTItemTagsProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static net.gabriele333.fmtt.fmtt.LOGGER;
import static net.gabriele333.fmtt.tags.FMTTTags.Items.CRAFTABLE;
import static net.minecraft.tags.TagEntry.tag;


@EventBusSubscriber(modid = fmtt.MOD_ID)
public class ModEvents {


    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new FMTTXpGet(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }



    @SubscribeEvent
    public static void onRecipesUpdatedEvent(RecipesUpdatedEvent event) {

        RecipeManager recipeManager = event.getRecipeManager();
        Collection<RecipeHolder<?>> recipes = recipeManager.getRecipes();


        FMTTItemTagsProvider itemTagsProvider = fmtt.getItemTagsProvider();

        for (RecipeHolder<?> recipeHolder : recipes) {
            Recipe<?> recipe = recipeHolder.value();


                ItemStack resultItem = recipe.getResultItem(Minecraft.getInstance().level.registryAccess());
                LOGGER.info(String.valueOf(resultItem));

                itemTagsProvider.addItemToTagCraft(resultItem.getItem());

        }

    }





}
