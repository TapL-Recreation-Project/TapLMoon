package dev.barfuzzle99.taplmoon.taplmoon.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

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
        for (Player player : Bukkit.getOnlinePlayers()){
            if (worldNames.contains(player.getWorld().getName())){
                List<Entity> entities = player.getWorld().getEntities();
                for (Entity entity : entities){
                    if (entity instanceof LivingEntity){
                        ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 6, 7));
                        ((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 6, 2));
                    }
                    else if (entity instanceof Item){
                        if (entity.getVelocity().getY() < 0){
                            entity.getVelocity().setY(0);
                        }
                    }
                }
            }
        }
    }
}
