package dev.barfuzzle99.taplmoon.taplmoon.runnables;

import dev.barfuzzle99.taplmoon.taplmoon.utils.SuitManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SuitCheck extends BukkitRunnable {

    private static final List<String> worldNames = new ArrayList<>();

    static {
        worldNames.add("moon");
        worldNames.add("moon_nether");
        worldNames.add("moon_the_end");
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (worldNames.contains(player.getWorld().getName())){
                if (!SuitManager.checkForFullSet(player)){
                    player.damage(2);
                }
            }
        }
    }
}
