package net.cobaltonline.minimaltpa;

import net.cobaltonline.minimaltpa.commands.TPCancelCommand;
import net.cobaltonline.minimaltpa.commands.BackCommand;
import net.cobaltonline.minimaltpa.commands.TPACommand;
import net.cobaltonline.minimaltpa.commands.TPAcceptCommand;
import net.cobaltonline.minimaltpa.commands.TPDenyCommand;
import net.cobaltonline.minimaltpa.listeners.PlayerListener;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public final class MinimalTPA extends JavaPlugin {
    private final Logger logger = getLogger();
    private FileConfiguration config = getConfig();

    // Hash maps o'plenty!
    public HashMap<UUID, UUID> requests = new HashMap<UUID, UUID>();
    public HashMap<UUID, Location> backLocations = new HashMap<UUID, Location>();
    public HashMap<UUID, Long> backCooldowns = new HashMap<UUID, Long>();
    public HashMap<UUID, Long> tpaCooldowns = new HashMap<UUID, Long>();

    @Override
    public void onEnable() {
        this.config.options().copyDefaults(true);
        saveConfig();
        this.getCommand("tpa").setExecutor(new TPACommand(this));
        this.getCommand("tpaccept").setExecutor(new TPAcceptCommand(this));
        this.getCommand("tpdeny").setExecutor(new TPDenyCommand(this));
        this.getCommand("tpcancel").setExecutor(new TPCancelCommand(this));
        this.getCommand("back").setExecutor(new BackCommand(this));
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        logger.info("MinimalTPA is now listening for requests!");
    }

    @Override
    public void onDisable() {
        this.requests.clear();
        this.tpaCooldowns.clear();
        this.backCooldowns.clear();
        this.backLocations.clear();
    }
}
