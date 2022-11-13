package net.cobaltonline.minimaltpa.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.cobaltonline.minimaltpa.MinimalTPA;

public final class BackCommand implements CommandExecutor {
    private MinimalTPA plugin;
    public BackCommand(MinimalTPA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command cannot be used from the console.");
            return true;
        }

        if (!this.plugin.backLocations.containsKey(player.getUniqueId())) {
            player.sendMessage("You have no back locations!");
            return true;
        }

        int cooldown = this.plugin.getConfig().getInt("back-cooldown");
        if (this.plugin.backCooldowns.containsKey(player.getUniqueId()) && !player.hasPermission("minimaltpa.bypasscooldown")) {
            long diff = (System.currentTimeMillis() - this.plugin.backCooldowns.get(player.getUniqueId())) / 1000;
            if (diff < cooldown) {
                player.sendMessage(String.format("Please wait %d seconds before going back to your previous location!", cooldown));
                return true;
            }
        }

        Location currentLocation = player.getLocation();
        Location backLocation = this.plugin.backLocations.get(player.getUniqueId());
        this.plugin.backLocations.put(player.getUniqueId(), currentLocation);
        player.teleport(backLocation);
        this.plugin.backCooldowns.put(player.getUniqueId(), System.currentTimeMillis());
        player.sendMessage("Teleported to previous location!");
        return true;
    }
}
