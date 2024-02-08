package net.gabriele333.fmtt.commands;
import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXPProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class FMTTXpRemove {
    public FMTTXpRemove(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fmtt")
                .then(Commands.literal("xp")
                        .then(Commands.literal("remove")
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(command -> {
                                            return removePlayerXp(command.getSource(), IntegerArgumentType.getInteger(command, "amount"));
                                        })))));
    }

    private int removePlayerXp(CommandSourceStack source, int xpToRemove) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        player.getCapability(PlayerFMTTXPProvider.Player_FMTT_XP).ifPresent(playerFMTTXP -> {
            int currentXp = playerFMTTXP.getPlayerFMTTXP();
            if (currentXp > 0 && currentXp >= xpToRemove) {
                playerFMTTXP.removePlayerFMTTXP(xpToRemove);
                player.sendSystemMessage(Component.literal("Removed " + xpToRemove + " FMTT XP."));
            } else {
                player.sendSystemMessage(Component.literal("FMTT XP Cannot be a negative value"));
            }
        });
        return 1;
    }
}
