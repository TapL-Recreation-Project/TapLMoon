package dev.barfuzzle99.taplmoon.taplmoon.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;


public class LowGravity extends BukkitRunnable {

    private static final List<String> worldNames = new ArrayList<>();

    static {
        worldNames.add("moon");
        worldNames.add("moon_nether");
        worldNames.add("moon_the_end");
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (worldNames.contains(player.getWorld().getName())) {
                List<Entity> entities = player.getWorld().getEntities();
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity) {
                        ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 6, 7, false, false));
                        ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 6, 1, false, false));
                    } else if (entity instanceof Item) {
                        Vector vector = entity.getVelocity();
                        vector.setY(vector.getY() * 0.5);
                        entity.setVelocity(vector);
                    }
                }
            }
        }
    }
}
