package dev.barfuzzle99.taplmoon.taplmoon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TaplMoon extends JavaPlugin {

    private static Yml playerLastLocationsYml;
    private static TaplMoon instance;

    @Override
    public void onEnable() {
        instance = this;
        registerCommands();
        registerListeners();
        initConfig();
    }

    public void registerListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PortalListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new AnimalSpawnerListener(), this);
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
