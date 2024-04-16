package net.gabriele333.fmtt.item;

import net.gabriele333.fmtt.fmtt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FMTTItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, fmtt.MOD_ID);

    //Crystals items
    public static final RegistryObject<Item> CRY_TIME = ITEMS.register("crystal_of_time",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    public static final RegistryObject<Item> CRY_TECH = ITEMS.register("crystal_of_tech",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    public static final RegistryObject<Item> CRY_MAGIC = ITEMS.register("crystal_of_magic",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    public static final RegistryObject<Item> CRY_CHANCE = ITEMS.register("crystal_of_chance",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));

    public static final RegistryObject<Item> CRY_COSMOS = ITEMS.register("crystal_of_cosmos",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).fireResistant()));






    public static final RegistryObject<Item> FMTT_ITEM = ITEMS.register("fmtt_item",
            ()-> new Item(new Item.Properties())
    );


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
