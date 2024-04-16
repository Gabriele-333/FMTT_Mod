package net.gabriele333.fmtt;

import com.mojang.logging.LogUtils;
import net.gabriele333.fmtt.config.FMTTClientConfig;
import net.gabriele333.fmtt.config.FMTTCommonConfig;
import net.gabriele333.fmtt.item.FMTTItems;
import net.gabriele333.fmtt.network.FMTTNetwork;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod(fmtt.MOD_ID)
public class fmtt {
    public static final String MOD_ID = "fmtt";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static fmtt INSTANCE;
    public static final FMTTCommonConfig COMMON_CONFIG = new FMTTCommonConfig();
    public static final FMTTClientConfig CLIENT_CONFIG = new FMTTClientConfig();



    public fmtt() {
        INSTANCE = this;
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG.spec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG.spec);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        LOGGER.info("ciao");
        FMTTItems.register(modEventBus);
        FMTTCreativeTabs.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

    }
    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(FMTTNetwork::register);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
