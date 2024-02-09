package net.gabriele333.fmtt;

import net.gabriele333.fmtt.item.FMTTItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FMTTCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, fmtt.MOD_ID);
    public static final RegistryObject<CreativeModeTab> fmtt_creative_tab = CREATIVE_MODE_TABS.register("fmtt_creative_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(FMTTItems.FMTT_ITEM.get()))
                    //.title(Component.translatable("creativetab.fmtt_creative_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(FMTTItems.CRY_TIME.get());
                        pOutput.accept(FMTTItems.CRY_TECH.get());
                        pOutput.accept(FMTTItems.CRY_MAGIC.get());
                        pOutput.accept(FMTTItems.CRY_CHANCE.get());
                        pOutput.accept(FMTTItems.CRY_COSMUS.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
