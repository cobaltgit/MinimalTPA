package net.cobaltonline.minimaltpa.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.cobaltonline.minimaltpa.MinimalTPA;

public class PlayerListener implements Listener {
    private MinimalTPA plugin;
    public PlayerListener(MinimalTPA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        this.plugin.backLocations.put(player.getUniqueId(), player.getLocation());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.plugin.requests.remove(player.getUniqueId());
        this.plugin.backLocations.remove(player.getUniqueId());
    }
}
