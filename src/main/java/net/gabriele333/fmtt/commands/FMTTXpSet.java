package net.gabriele333.fmtt.commands;
import net.gabriele333.fmtt.FMTTXP.FMTTXPAdvancement;
import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXpProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class FMTTXpSet {
    public FMTTXpSet(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fmtt")
                .then(Commands.literal("xp")
                        .then(Commands.literal("set")
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(command -> {
                                            return removePlayerXp(command.getSource(), IntegerArgumentType.getInteger(command, "amount"));
                                        })))));
    }

    private int removePlayerXp(CommandSourceStack source, int xpToSet) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        if (player.gameMode.getGameModeForPlayer() == net.minecraft.world.level.GameType.CREATIVE) {
            player.getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(playerfmttxp -> {
                if (xpToSet >= 0) {
                    playerfmttxp.setPlayerFMTTXP(xpToSet);
                    player.sendSystemMessage(Component.literal( "FMTT XP now is "+xpToSet ));
                    FMTTXPAdvancement.FMTTXPTriggerAdvancement(player);
                } else {
                    player.sendSystemMessage(Component.literal("FMTT XP Cannot be a negative value"));
                }

            });
            return 1;
        } else {
            player.sendSystemMessage(Component.literal("This command is only available in Creative mode."));
            return 0;
        }

    }
}
