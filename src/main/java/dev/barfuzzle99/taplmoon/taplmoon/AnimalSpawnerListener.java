package dev.barfuzzle99.taplmoon.taplmoon;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.Random;

public class AnimalSpawnerListener implements Listener {
    Random rng = new Random();

    @EventHandler
    public void onChunkGen(ChunkLoadEvent event) {
        if (!event.getWorld().getName().equals("moon")) {
            return;
        }
        final int ANIMAL_RARITY = 100;

        EntityType[] allowedAnimals = new EntityType[] {
                EntityType.COW, EntityType.CHICKEN, EntityType.SHEEP, EntityType.HORSE, EntityType.PIG, EntityType.LLAMA, EntityType.RABBIT
        };

        if (event.isNewChunk() && event.getChunk().hashCode() % ANIMAL_RARITY == 0) {
            int chunkXStart = event.getChunk().getX() * 16;
            int chunkZStart = event.getChunk().getZ() * 16;

            int nAnimalsToGen = 3 + rng.nextInt(4);

            EntityType randomType = allowedAnimals[rng.nextInt(allowedAnimals.length)];
            for (int i = 0; i < nAnimalsToGen; i ++) {
                int xOffset = rng.nextInt(16);
                int zOffset = rng.nextInt(16);
                int spawnY = 1 + event.getWorld().getHighestBlockYAt(chunkXStart + xOffset, chunkZStart + zOffset);
                Location spawnLoc = new Location(event.getWorld(), chunkXStart + xOffset, spawnY, chunkZStart + zOffset);
                event.getWorld().spawnEntity(spawnLoc, randomType);
            }
        }
    }
}