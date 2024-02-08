package net.gabriele333.fmtt.event;

import net.gabriele333.fmtt.client.ModpackVersion;
import net.gabriele333.fmtt.config.FMTTClientConfig;
import net.gabriele333.fmtt.fmtt;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.Objects;


public class ClientModEvents {
    private static boolean versionMessageSent = false;


    @Mod.EventBusSubscriber(modid = fmtt.MOD_ID, value = Dist.CLIENT)
    public static class ForgeEvents {

        @SubscribeEvent(priority = EventPriority.LOW)
        public static void onPlayerJoinClient(PlayerEvent.PlayerLoggedInEvent event) {
            if (!versionMessageSent && !Objects.equals(ModpackVersion.main(), FMTTClientConfig.MiscSettings.Version.get())) {
                event.getEntity().sendSystemMessage(Component.literal("§bThere is a newer version of the modpack: " + ModpackVersion.main()));
                versionMessageSent = true;
            }
        }



    }
}
