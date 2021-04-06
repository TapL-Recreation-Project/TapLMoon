package dev.barfuzzle99.taplmoon.taplmoon;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.logging.Level;

public class PortalListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPortalUse(PlayerPortalEvent event) {
        if (!MoonWorldUtil.isMoonWorld(event.getPlayer().getWorld())) {
            return;
        }

        switch (event.getCause()) {
            case END_PORTAL:
                World end = Bukkit.getWorld("moon_the_end");
                if (end == null) {
                    TaplMoon.getInstance().getLogger().log(Level.SEVERE, "could not find moon_the_end, did you rename or delete the world?");
                    return;
                }
                event.setTo(Bukkit.getWorld("moon_the_end").getSpawnLocation());
                break;
            case NETHER_PORTAL:
                World nether = Bukkit.getWorld("moon_nether");
                if (nether == null) {
                    TaplMoon.getInstance().getLogger().log(Level.SEVERE, "could not find moon_nether, did you rename or delete the world?");
                    return;
                }
                Location netherLoc = event.getPlayer().getLocation().clone();
                netherLoc.setX(netherLoc.getX() / 8);
                netherLoc.setZ(netherLoc.getZ() / 8);
                netherLoc.setWorld(nether);
                event.setTo(netherLoc);
                break;
            default:
                break;
        }
    }
}
