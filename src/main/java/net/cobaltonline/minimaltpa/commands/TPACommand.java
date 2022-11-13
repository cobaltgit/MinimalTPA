package net.cobaltonline.minimaltpa.commands;

import net.cobaltonline.minimaltpa.MinimalTPA;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class TPACommand implements CommandExecutor {
    private MinimalTPA plugin;
    public TPACommand(MinimalTPA plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command cannot be used from the console.");
            return true;
        } else if (args.length == 0) {
            sender.sendMessage("You must specify a player!");
            return false;
        }

        long keepAlive = this.plugin.getConfig().getInt("keep-alive") * 20;

        int cooldown = this.plugin.getConfig().getInt("cooldown");
        if (this.plugin.tpaCooldowns.containsKey(player.getUniqueId()) && !player.hasPermission("minimaltpa.bypasscooldown")) {
            long diff = (System.currentTimeMillis() - this.plugin.tpaCooldowns.get(player.getUniqueId())) / 1000;
            if (diff < cooldown) {
                player.sendMessage(String.format("Please wait %d seconds before sending another TPA request!", cooldown));
                return true;
            }
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null) {
            player.sendMessage(String.format("Player %s does not exist.", args[0]));
            return true;
        } else if (target == player) {
            player.sendMessage("You cannot send or cancel a TPA request to yourself!");
            return true;
        } else if (this.plugin.requests.containsKey(player.getUniqueId())) {
            Player existingTarget = Bukkit.getPlayer(this.plugin.requests.get(player.getUniqueId()));
            player.sendMessage(String.format("You already have a TPA request to %s!", existingTarget.getName()));
            return true;
        } else if (this.plugin.requests.containsValue(target.getUniqueId())) {
            player.sendMessage(String.format("%s already has an incoming TPA request!", target.getName()));
            return true;
        }

        this.plugin.requests.put(player.getUniqueId(), target.getUniqueId());
        player.sendMessage(String.format("Sent a TPA request to %s.\nIf you wish to cancel, type /tpcancel", args[0], args[0]));
        target.sendMessage(String.format("You have received a TPA request from %s.\nTo allow them to teleport you, type /tpaccept in the console.\n If not, then type /tpdeny", args[0]));

        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
            public void run() {
                onTimeout(player, target);
            }
        }, keepAlive);

        this.plugin.tpaCooldowns.put(player.getUniqueId(), System.currentTimeMillis());

        return true;
    }

    public void onTimeout(Player player, Player target) {
        player.sendMessage(String.format("Your TPA request to %s timed out!", target.getName()));
        target.sendMessage(String.format("TPA request from %s timed out!", player.getName()));
        this.plugin.requests.remove(player.getUniqueId());
        this.plugin.tpaCooldowns.remove(player.getUniqueId());
    }
}
