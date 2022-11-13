package net.cobaltonline.minimaltpa.commands;

import net.cobaltonline.minimaltpa.MinimalTPA;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class TPAcceptCommand implements CommandExecutor {
    private MinimalTPA plugin;
    public TPAcceptCommand(MinimalTPA plugin) {
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
                this.plugin.requests.remove(tpaSender.getUniqueId());
                break;
            }
        }
        
        this.plugin.backLocations.put(tpaSender.getUniqueId(), tpaSender.getLocation());
        tpaSender.teleport(player.getLocation());
        int timeoutTaskId = this.plugin.timeouts.remove(player.getUniqueId());
        Bukkit.getScheduler().cancelTask(timeoutTaskId);
        tpaSender.sendMessage(String.format("Teleported to %s!", player.getName()));
        player.sendMessage(String.format("%s has teleported to you!", tpaSender.getName()));
        return true;
    }
}
