package me.grape.aether.bukkit.commands;

import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.bukkit.executor.BukkitCommandExecutor;
import lombok.RequiredArgsConstructor;
import me.grape.aether.shared.Aether;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author Grape (grape#9876)
 * 10/29/2021 / 10:46 PM
 * Aether / me.grape.aether.bukkit.commands
 */
@RequiredArgsConstructor
public class SyncCommands {

    private final Aether aether;

    @Command(label = "sync")
    public void syncCommand(BukkitCommandExecutor executor) {
        if (!executor.isUser()) {
            executor.sendMessage(ChatColor.RED + "This command is only used for players.");
            return;
        }
        aether.getExecutorService().execute(() -> {
            Player player = executor.getPlayer();
            if (aether.getSyncHandler().hasSynced(player.getUniqueId())) {
                player.sendMessage(ChatColor.GREEN + "You are already synced.");
                return;
            }
            if (aether.getSyncHandler().hasRequested(player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You have already requested to sync your account. Your code is " + aether.getSyncHandler().getCode(player.getUniqueId()) + ".");
                return;
            }
            String code = aether.getSyncHandler().sendRequest(player.getUniqueId(), player.getName());
            player.sendMessage(ChatColor.GREEN + "Successfully sent sync request. Your generated code is " + code + ".");
        });
    }

    @Command(label = "unsync")
    public void unsyncCommand(BukkitCommandExecutor executor) {
        if (!executor.isUser()) {
            executor.sendMessage(ChatColor.RED + "This command is only used for players.");
            return;
        }
        aether.getExecutorService().execute(() -> {
            Player player = executor.getPlayer();
            if (!aether.getSyncHandler().hasSynced(player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "You are not synced.");
                return;
            }
            player.sendMessage(ChatColor.GREEN + "Successfully unsynced your account!.");
            aether.getSyncHandler().unSync(player.getUniqueId());
        });
    }

}
