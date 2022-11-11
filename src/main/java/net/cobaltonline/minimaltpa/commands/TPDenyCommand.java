package net.cobaltonline.minimaltpa.commands;

import net.cobaltonline.minimaltpa.MinimalTPA;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class TPDenyCommand implements CommandExecutor {

    private MinimalTPA plugin;
    public TPDenyCommand(MinimalTPA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command cannot be used from the console.");
            return true;
        }

        if (!this.plugin.requests.containsValue(player.getUniqueId())) {
            sender.sendMessage("You have no incoming TPA requests!");
            return true;
        }

        Player tpaSender = null;

        for (UUID u : this.plugin.requests.keySet()) {
            if (this.plugin.requests.get(u) == player.getUniqueId()) {
                tpaSender = Bukkit.getPlayer(u);
                this.plugin.requests.remove(player.getUniqueId());
                break;
            }
        }

        tpaSender.sendMessage(String.format("%s has denied your TPA request!", player.getName()));
        player.sendMessage(String.format("You have denied TPA request from %s!", tpaSender.getName()));
        return true;
    }
}
