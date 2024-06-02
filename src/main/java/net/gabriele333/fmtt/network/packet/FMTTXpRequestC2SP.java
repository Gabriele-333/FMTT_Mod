/*package net.gabriele333.fmtt.network.packet;

import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXpProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class FMTTXpRequestC2SP {
    public FMTTXpRequestC2SP() {}

    public FMTTXpRequestC2SP(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    public int handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        AtomicInteger fmtt_xp_ = new AtomicInteger();
        if (player != null) {
            context.enqueueWork(() -> {
                player.getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(xp -> {
                    fmtt_xp_.set(xp.getPlayerFMTTXP());
                });
            });
        }
        return fmtt_xp_.get();
    }
}
*/