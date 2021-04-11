package dev.barfuzzle99.taplmoon.taplmoon;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (player.getWorld().getName().contains("moon")){
            player.setResourcePack("https://www.dropbox.com/s/oltfoub9xywjm1a/MoonPack%20%281%29.zip?dl=1");
        }
    }
}
