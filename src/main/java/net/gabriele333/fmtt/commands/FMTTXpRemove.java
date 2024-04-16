/*package net.gabriele333.fmtt.commands;
import net.gabriele333.fmtt.FMTTXP.FMTTXPAdvancement;
import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXpProvider;
import net.gabriele333.fmtt.network.FMTTNetwork;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.commands.arguments.EntityArgument;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import java.util.Collection;

public class FMTTXpRemove {
    public FMTTXpRemove(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fmtt")
                .then(Commands.literal("xp")
                        .then(Commands.literal("remove")
                                .then(Commands.argument("player", EntityArgument.players())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(command -> {
                                                    return removePlayerXp(command.getSource(), EntityArgument.getPlayers(command, "player"), IntegerArgumentType.getInteger(command, "amount"));
                                                }))))));}

    private int removePlayerXp(CommandSourceStack source, Collection<ServerPlayer> players, int xpToRemove) throws CommandSyntaxException {
        ServerPlayer commandExecutor = source.getPlayerOrException();
        if (commandExecutor.gameMode.getGameModeForPlayer() == net.minecraft.world.level.GameType.CREATIVE) {
            for (ServerPlayer player : players) {
                player.getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(playerfmttxp -> {
                    int currentXp = playerfmttxp.getPlayerFMTTXP();
                    if (currentXp > 0 && currentXp >= xpToRemove) {
                        playerfmttxp.removePlayerFMTTXP(xpToRemove);
                        player.sendSystemMessage(Component.literal("Removed " + xpToRemove + " FMTT XP."));
                        FMTTXPAdvancement.FMTTXPTriggerAdvancement(player);

                    } else {
                        player.sendSystemMessage(Component.literal("FMTT XP Cannot be a negative value."));
                    }
                });
            }
        } else {commandExecutor.sendSystemMessage(Component.literal("This command is only available in Creative mode."));}
        return 1;
    }
}
*/