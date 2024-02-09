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

public class FMTTXpAdd {
    public FMTTXpAdd(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fmtt")
                .then(Commands.literal("xp")
                        .then(Commands.literal("add")
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(command -> {
                                            return addPlayerXp(command.getSource(), IntegerArgumentType.getInteger(command, "amount"));
                                        })))));
    }
    private int addPlayerXp(CommandSourceStack source, int xpToAdd) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        if (player.gameMode.getGameModeForPlayer() == net.minecraft.world.level.GameType.CREATIVE) {
            player.getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(playerfmttxp -> {
                playerfmttxp.addPlayerFMTTXP(xpToAdd);
                player.sendSystemMessage(Component.literal("Added " + xpToAdd + " XP FMTT."));
                FMTTXPAdvancement.FMTTXPTriggerAdvancement(player);
            });
            return 1;

        } else {
            player.sendSystemMessage(Component.literal("This command is only available in Creative mode."));
            return 0;
        }
    }
}
