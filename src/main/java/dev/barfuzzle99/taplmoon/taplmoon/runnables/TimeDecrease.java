package dev.barfuzzle99.taplmoon.taplmoon.runnables;

import dev.barfuzzle99.taplmoon.taplmoon.TaplMoon;
import dev.barfuzzle99.taplmoon.taplmoon.stored.PlayerPercentages;
import dev.barfuzzle99.taplmoon.taplmoon.utils.SendTitleBarMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TimeDecrease extends BukkitRunnable {

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
                UUID uuid = player.getUniqueId();
                if (PlayerPercentages.oxygenDecimal.get(uuid) > 0){
                    PlayerPercentages.oxygenDecimal.put(uuid, PlayerPercentages.oxygenDecimal.get(uuid)-1);
                    if (PlayerPercentages.oxygenDecimal.get(uuid) == 0){
                        PlayerPercentages.oxygenDecimal.put(uuid, 99);
                        PlayerPercentages.oxygenPercentage.put(uuid, PlayerPercentages.oxygenPercentage.get(uuid)-1);
                    }
                }
                SendTitleBarMessage.sendMessage(player, ChatColor.RED.toString()+ChatColor.BOLD+"Oxygen Left:", PlayerPercentages.oxygenDecimal.get(uuid), PlayerPercentages.oxygenPercentage.get(uuid));
            }
        }
    }
}
