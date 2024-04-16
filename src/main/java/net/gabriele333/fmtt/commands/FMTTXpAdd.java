package net.gabriele333.fmtt.commands;
import net.gabriele333.fmtt.FMTTXP.FMTTXPAdvancement;
import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXpProvider;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.commands.arguments.EntityArgument;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import java.util.Collection;

public class FMTTXpAdd {
    public FMTTXpAdd(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fmtt")
                .then(Commands.literal("xp")
                        .then(Commands.literal("add")
                                .then(Commands.argument("player", EntityArgument.players())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                                .executes(command -> {
                                                    return addPlayerXp(command.getSource(), EntityArgument.getPlayers(command, "player"), IntegerArgumentType.getInteger(command, "amount"));
                                                }))))));
    }

    private int addPlayerXp(CommandSourceStack source, Collection<ServerPlayer> players, int xpToAdd) throws CommandSyntaxException {
        ServerPlayer commandExecutor = source.getPlayerOrException();
        if (commandExecutor.gameMode.getGameModeForPlayer() == net.minecraft.world.level.GameType.CREATIVE) {
            for (ServerPlayer player : players) {
                player.getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(playerfmttxp -> {
                    playerfmttxp.addPlayerFMTTXP(xpToAdd);
                    player.sendSystemMessage(Component.literal("Added " + xpToAdd + " XP FMTT."));
                    FMTTXPAdvancement.FMTTXPTriggerAdvancement(player);

                });

            }
        } else {
            commandExecutor.sendSystemMessage(Component.literal("This command is only available in Creative mode."));
        }

        return 1;
    }
}
