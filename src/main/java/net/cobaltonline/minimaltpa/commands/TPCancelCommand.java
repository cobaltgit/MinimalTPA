package net.cobaltonline.minimaltpa.commands;

import net.cobaltonline.minimaltpa.MinimalTPA;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class TPCancelCommand implements CommandExecutor {
    private MinimalTPA plugin;
    public TPCancelCommand(MinimalTPA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command cannot be used from the console.");
            return true;
        }

        if (!this.plugin.requests.containsKey(player.getUniqueId())) {
            player.sendMessage("You have no outgoing TPA requests!");
            return true;
        }

        Player target = Bukkit.getPlayer(this.plugin.requests.get(player.getUniqueId()));

        this.plugin.requests.remove(player.getUniqueId());
        player.sendMessage(String.format("Cancelled TPA request to %s!", target.getName()));
        target.sendMessage(String.format("%s has cancelled their TPA request to you!", player.getName()));
        return true;
    }
}
