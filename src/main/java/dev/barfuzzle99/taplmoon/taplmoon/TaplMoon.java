package dev.barfuzzle99.taplmoon.taplmoon;
import dev.barfuzzle99.taplmoon.taplmoon.runnables.LowGravity;
import dev.barfuzzle99.taplmoon.taplmoon.runnables.ReplaceNearbyBlocks;
import dev.barfuzzle99.taplmoon.taplmoon.runnables.TimeDecrease;
import dev.barfuzzle99.taplmoon.taplmoon.runnables.TimeRunOut;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public final class TaplMoon extends JavaPlugin {

    private static Yml playerLastLocationsYml;
    private static TaplMoon instance;

    @Override
    public void onEnable() {
        instance = this;
        registerCommands();
        registerListeners();
        registerRunnables();
        initConfig();
    }

    public void registerListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PortalListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new AnimalSpawnerListener(), this);
    }

    public void registerRunnables(){
        BukkitTask LowGravity = new LowGravity().runTaskTimer(this, 1, 1);
        BukkitTask ReplaceNearbyBlocks = new ReplaceNearbyBlocks().runTaskTimer(this, 1, 1);
        BukkitTask TimeDecrease = new TimeDecrease().runTaskTimer(this, 20, 20);
        BukkitTask TimeRunOut = new TimeRunOut().runTaskTimer(this, 20, 20);
    }

    public void registerCommands() {
        this.getCommand("taplmoon").setExecutor(new CmdTaplMoon());
        this.getCommand("taplmoon").setTabCompleter(new CmdTaplMoon());
    }

    public void initConfig() {
        playerLastLocationsYml = new Yml(this, "last_locations.yml");
        if (!playerLastLocationsYml.getFile().exists()) {
            playerLastLocationsYml.createFile();
        }
    }

    public static Yml getPlayerLastLocationsYml() {
        return playerLastLocationsYml;
    }

    public static TaplMoon getInstance() {
        return instance;
    }
}
