package dev.barfuzzle99.taplmoon.taplmoon.stored;

import java.util.HashMap;
import java.util.UUID;

public class PlayerPercentages {

    public static HashMap<UUID, Integer> oxygenPercentage = new HashMap<>();
    public static HashMap<UUID, Integer> oxygenDecimal = new HashMap<>();

    public static void resetOxygen(UUID uuid){
        oxygenPercentage.put(uuid, 99);
        oxygenDecimal.put(uuid, 99);
    }
}
