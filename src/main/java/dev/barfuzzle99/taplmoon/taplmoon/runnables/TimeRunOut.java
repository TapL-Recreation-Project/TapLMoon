package dev.barfuzzle99.taplmoon.taplmoon.runnables;

import dev.barfuzzle99.taplmoon.taplmoon.stored.PlayerPercentages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TimeRunOut extends BukkitRunnable {

    private static final List<String> worldNames = new ArrayList<>();

    static {
        worldNames.add("moon");
        worldNames.add("moon_nether");
        worldNames.add("moon_the_end");
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            if (worldNames.contains(player.getWorld().getName())) {
                UUID uuid = player.getUniqueId();
                if (!PlayerPercentages.oxygenDecimal.containsKey(uuid)){
                    PlayerPercentages.resetOxygen(uuid);
                }
                if (PlayerPercentages.oxygenDecimal.get(uuid) == 0 && PlayerPercentages.oxygenPercentage.get(uuid) == 0){
                    Location location = Bukkit.getWorld("world").getSpawnLocation();
                    player.getInventory().clear();
                    player.teleport(location);
                    player.sendMessage(ChatColor.RED+"You ran out of Oxygen! Rejoin the moon world to try again!");
                    PlayerPercentages.oxygenDecimal.put(uuid, 99);
                    PlayerPercentages.oxygenPercentage.put(uuid, 99);
                }
            }
        }
    }
}
