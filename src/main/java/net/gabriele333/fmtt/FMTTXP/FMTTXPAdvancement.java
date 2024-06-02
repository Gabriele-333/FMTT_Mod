/*package net.gabriele333.fmtt.FMTTXP;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.world.entity.player.Player;


public class FMTTXPAdvancement {

    public static void FMTTXPTriggerAdvancement(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        serverPlayer.getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(playerfmttxp -> {
            int xp = playerfmttxp.getPlayerFMTTXP();
            ResourceLocation id = null;

            if (xp >= 20) {
                id = new ResourceLocation("fmtt", "20_fmtt_xp");
            } else if (xp >= 10) {
                id = new ResourceLocation("fmtt", "10_fmtt_xp");
            }

            if (id != null) {
                Advancement advancement = serverPlayer.getServer().getAdvancements().getAdvancement(id);
                if (advancement != null) {
                    PlayerAdvancements playerAdvancements = serverPlayer.getAdvancements();
                    if (!playerAdvancements.getOrStartProgress(advancement).isDone()) {
                        for (String criterion : playerAdvancements.getOrStartProgress(advancement).getRemainingCriteria()) {
                            playerAdvancements.award(advancement, criterion);
                        }
                    }
                }
            }
        });
    }
}
*/