package net.gabriele333.fmtt.event;

import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXp;
import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXpProvider;
import net.gabriele333.fmtt.commands.FMTTXpAdd;
import net.gabriele333.fmtt.commands.FMTTXpGet;
import net.gabriele333.fmtt.commands.FMTTXpSet;
import net.gabriele333.fmtt.fmtt;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.server.command.ConfigCommand;


import static com.mojang.text2speech.Narrator.LOGGER;

@Mod.EventBusSubscriber(modid = fmtt.MOD_ID)
public class ModEvents {


    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerFMTTXpProvider.player_fmtt_xp).isPresent()) {
                event.addCapability(new ResourceLocation(fmtt.MOD_ID, "properties"), new PlayerFMTTXpProvider());
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerClone(final PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                    event.getOriginal().invalidateCaps();
                });
            });
        }
    }



    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        LOGGER.info("ciao1");
        event.register(PlayerFMTTXp.class);
    }


    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new FMTTXpGet(event.getDispatcher());
        new FMTTXpAdd(event.getDispatcher());
        new FMTTXpSet(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }

}
