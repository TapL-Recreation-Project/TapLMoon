package dev.barfuzzle99.taplmoon.taplmoon;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.StructureType;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class MoonWorldUtil {
    public static List<World> getMoonWorlds() {
        ArrayList<World> ret = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            if (world.getName().contains("moon")) {
                ret.add(world);
            }
        }
        return ret;
    }

    public static boolean isMoonWorld(World world) {
        return world.getName().contains("moon");
    }

    public static Location locateStructure(Location origin, StructureType type, int radius, boolean onlyFindUnexplored) {
        return Bukkit.getWorlds().get(0).locateNearestStructure(origin, type, radius, onlyFindUnexplored);
    }
}
