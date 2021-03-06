package dev.barfuzzle99.taplmoon.taplmoon;
import dev.barfuzzle99.taplmoon.taplmoon.runnables.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
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
        registerCobbleRecipe(this);
        initConfig();
        initWorlds();
    }

    // TODO: not always do all worlds need to be initialized
    private void initWorlds() {
        if (MoonWorldUtil.areThereMoonWorlds()) {
            MoonWorldCreator.forceLoadWorlds();
        }
    }

    private void registerListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PortalListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new AnimalSpawnerListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    private void registerRunnables(){
        BukkitTask LowGravity = new LowGravity().runTaskTimer(this, 1, 1);
        BukkitTask ReplaceNearbyBlocks = new ReplaceNearbyBlocks().runTaskTimer(this, 1, 1);
        BukkitTask TimeDecrease = new TimeDecrease().runTaskTimer(this, 10, 10);
        BukkitTask TimeRunOut = new TimeRunOut().runTaskTimer(this, 10, 10);
        BukkitTask SuitCheck = new SuitCheck().runTaskTimer(this, 10, 10);
    }

    private static void registerCobbleRecipe(TaplMoon plugin) {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "moon_cobblestone"), new ItemStack(Material.COBBLESTONE, 4))
                .shape("AAA", "AAA", "AAA")
                .setIngredient('A', Material.END_STONE);
        Bukkit.addRecipe(recipe);
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
