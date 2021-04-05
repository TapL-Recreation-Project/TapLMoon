package dev.barfuzzle99.taplmoon.taplmoon;
import org.bukkit.plugin.java.JavaPlugin;

public final class TaplMoon extends JavaPlugin {

    private static Yml playerLastLocationsYml;
    private static TaplMoon instance;

    @Override
    public void onEnable() {
        instance = this;
        initConfig();
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
