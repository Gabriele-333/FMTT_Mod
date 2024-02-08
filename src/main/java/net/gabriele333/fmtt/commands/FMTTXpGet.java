package net.gabriele333.fmtt.commands;
import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXP;
import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXPProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class FMTTXpGet {
    public FMTTXpGet(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fmtt").then(Commands.literal("xp").executes((command) -> {
            return getPlayerXp(command.getSource());
        })));
    }

    private int getPlayerXp(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        player.getCapability(PlayerFMTTXPProvider.Player_FMTT_XP).ifPresent(playerFMTTXP -> {
            int xp = playerFMTTXP.getPlayerFMTTXP();
            player.sendSystemMessage(Component.literal("You have " + xp + " Xp"));
        });
        return 1;
    }
}
