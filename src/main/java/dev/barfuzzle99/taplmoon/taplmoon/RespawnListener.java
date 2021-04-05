package dev.barfuzzle99.taplmoon.taplmoon;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (!MoonWorldUtil.isMoonWorld(event.getPlayer().getWorld())) {
            return;
        }

        if (event.getRespawnLocation().getWorld() == null) {
            return;
        }

        if (!MoonWorldUtil.isMoonWorld(event.getRespawnLocation().getWorld())) {
            for (World world : MoonWorldUtil.getMoonWorlds()) {
                if (world.getEnvironment() == World.Environment.NORMAL) {
                    event.setRespawnLocation(world.getSpawnLocation());
                    return;
                }
            }
        }
    }
}
