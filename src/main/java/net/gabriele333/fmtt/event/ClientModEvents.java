package net.gabriele333.fmtt.event;
//import net.gabriele333.fmtt.FMTTXP.FMTTXPAdvancement;
import net.gabriele333.fmtt.client.ModpackVersion;
import net.gabriele333.fmtt.config.FMTTClientConfig;
import net.gabriele333.fmtt.fmtt;
import net.gabriele333.fmtt.item.FMTTItems;
import net.gabriele333.fmtt.network.FMTTNetwork;
import net.gabriele333.fmtt.network.packet.FMTTXpC2SP;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;


public class ClientModEvents {
    private static boolean versionMessageSent = false;


    @Mod.EventBusSubscriber(modid = fmtt.MOD_ID, value = Dist.CLIENT)
    public static class ForgeEvents {


        @SubscribeEvent(priority = EventPriority.LOW)
        public static void onPlayerJoinClient(PlayerEvent.PlayerLoggedInEvent event) {
            try {
                if (!versionMessageSent && !Objects.equals(ModpackVersion.main(), FMTTClientConfig.MiscSettings.Version.get())) {
                    event.getEntity().sendSystemMessage(Component.literal("§bThere is a newer version of the modpack: " + ModpackVersion.main()));
                    versionMessageSent = true;
                }
            }
            catch (Exception e) {
                return;
            }
        }

        @SubscribeEvent
        public static void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
            if (event.getSide() == LogicalSide.CLIENT) {
                ItemStack itemStack = event.getItemStack();
                if (itemStack.getItem() == FMTTItems.FMTT_XP_ITEM.get()) {
                    FMTTNetwork.sendToServer(new FMTTXpC2SP());
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }




    }
}
