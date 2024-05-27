package net.gabriele333.fmtt.network.packet;

import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXpProvider;
import net.gabriele333.fmtt.item.FMTTItems;
import net.gabriele333.fmtt.network.FMTTNetwork;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FMTTXpC2SP {
    public FMTTXpC2SP() {

    }
    public FMTTXpC2SP(FriendlyByteBuf buf) {
    }
    public  void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->{
            //FMTTNetwork.register();
            ServerPlayer player = context.getSender();
            assert player != null;
            ItemStack itemStack = player.getMainHandItem();
            if (itemStack.getItem() == FMTTItems.FMTT_XP_ITEM.get()) {
                itemStack.shrink(1);
            }
            player.getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(playerfmttxp -> {
                playerfmttxp.addPlayerFMTTXP(1);
            });

            player.getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(playerfmttxp -> {
                int xp = playerfmttxp.getPlayerFMTTXP();
                ResourceLocation id = null;

                if (xp >= 20) {
                    id = new ResourceLocation("fmtt", "20_fmtt_xp");
                } else if (xp >= 10) {
                    id = new ResourceLocation("fmtt", "10_fmtt_xp");
                }

                if (id != null) {
                    Advancement advancement = player.getServer().getAdvancements().getAdvancement(id);
                    if (advancement != null) {
                        PlayerAdvancements playerAdvancements = player.getAdvancements();
                        if (!playerAdvancements.getOrStartProgress(advancement).isDone()) {
                            for (String criterion : playerAdvancements.getOrStartProgress(advancement).getRemainingCriteria()) {
                                playerAdvancements.award(advancement, criterion);
                            }
                        }
                    }
                }
            });


        });
        return true;
    }
}