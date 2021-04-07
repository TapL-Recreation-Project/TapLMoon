package dev.barfuzzle99.taplmoon.taplmoon.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ReplaceNearbyBlocks extends BukkitRunnable {

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
                List<Block> list = getNearbyBlocks(player, 7);
                for (Block block : list){
                    if (block.getType().equals(Material.STONE) || block.getType().equals(Material.NETHERRACK)){
                        block.setType(Material.END_STONE);
                    }
                }
            }
        }
    }

    public static List<Block> getNearbyBlocks(Player player, int range){
        List<Block> list = new ArrayList<>();

        int firstx = player.getLocation().getBlockX()-range;
        int firsty = player.getLocation().getBlockY()-range;
        int firstz = player.getLocation().getBlockZ()-range;
        int secondx = player.getLocation().getBlockX()+range;
        int secondy = player.getLocation().getBlockY()+range;
        int secondz = player.getLocation().getBlockZ()+range;

        for (int x = firstx; x < secondx;x++){
            for (int y = firsty; y < secondy;y++){
                for (int z = firstz; z < secondz;z++){
                    list.add(player.getWorld().getBlockAt(x,y,z));
                }
            }
        }
        return list;
    }
}
