package dev.barfuzzle99.taplmoon.taplmoon;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MoonWorldUtil {
    public static List<World> getLoadedMoonWorlds() {
        ArrayList<World> ret = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            if (isMoonWorld(world)) {
                ret.add(world);
            }
        }
        return ret;
    }

    public static boolean areThereMoonWorlds() {
        for (File file: Bukkit.getServer().getWorldContainer().listFiles()){
            if (file.isDirectory() && file.getName().contains("moon")){
                return true;
            }
        }
        return false;
    }

    public static boolean isMoonWorld(World world) {
        return world.getName().contains("moon");
    }
}
