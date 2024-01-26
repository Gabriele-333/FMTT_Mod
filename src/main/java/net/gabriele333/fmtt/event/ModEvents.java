package net.gabriele333.fmtt.event;

import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXP;
import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXPProvider;
import net.gabriele333.fmtt.fmtt;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;


import java.util.Objects;

public class ModEvents {


    @Mod.EventBusSubscriber(modid = fmtt.MOD_ID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            if(event.getObject() instanceof Player) {
                if(!event.getObject().getCapability(PlayerFMTTXPProvider.Player_FMTT_XP).isPresent()) {
                    event.addCapability(new ResourceLocation(fmtt.MOD_ID, "properties"), new PlayerFMTTXPProvider());
                }
            }
        }
        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(PlayerFMTTXP.class);
        }



    }
}
