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
            player.setResourcePack("https://cdn.discordapp.com/attachments/812394140577824808/829189970664488980/MoonPack.zip");
        }
    }
}
