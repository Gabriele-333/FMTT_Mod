package net.gabriele333.fmtt.commands;
import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXPProvider;
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
        player.getCapability(PlayerFMTTXPProvider.Player_FMTT_XP).ifPresent(playerFMTTXP -> {
            playerFMTTXP.addPlayerFMTTXP(xpToAdd);
            player.sendSystemMessage(Component.literal("Added " + xpToAdd + " XP FMTT."));
        });
        return 1;
    }
}
