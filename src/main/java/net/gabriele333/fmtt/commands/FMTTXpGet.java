package net.gabriele333.fmtt.commands;
import net.gabriele333.fmtt.FMTTXP.PlayerFMTTXpProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class FMTTXpGet {
    public FMTTXpGet(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fmtt")
                .then(Commands.literal("xp")
                        .then(Commands.literal("get")
                                .executes((command) -> {
                                    return getPlayerXp(command.getSource());
                                })
                        )
                )
        );
    }

    private int getPlayerXp(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        player.getCapability(PlayerFMTTXpProvider.player_fmtt_xp).ifPresent(playerFMTTXp -> {
            player.sendSystemMessage(Component.literal("XP: " + playerFMTTXp.getPlayerFMTTXP()));
        });
        return 1;
    }
}