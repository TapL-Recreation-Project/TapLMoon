package dev.barfuzzle99.taplmoon.taplmoon.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class SuitManager {

    @SuppressWarnings("deprecation")
    public static ItemStack getSpaceHelmet() {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner("0kek");
        skullMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Astronaut Helmet");
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }

    public static Boolean checkForFullSet(Player player){
        boolean bool = false;
        try {
            if (player.getEquipment().getHelmet().getItemMeta().getDisplayName().toLowerCase().contains("astronaut") &&
                    player.getEquipment().getLeggings().getItemMeta().getDisplayName().toLowerCase().contains("astronaut") &&
                    player.getEquipment().getChestplate().getItemMeta().getDisplayName().toLowerCase().contains("astronaut") &&
                    player.getEquipment().getBoots().getItemMeta().getDisplayName().toLowerCase().contains("astronaut")){
                bool = true;
            }
        }
        catch (NullPointerException exception){
            // handled
        }
        return bool;
    }

    public static void giveSpaceSuit(Player player){

        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);

        ItemMeta chestmeta = chestplate.getItemMeta();
        ItemMeta legsmeta = leggings.getItemMeta();
        ItemMeta bootsmeta = boots.getItemMeta();

        chestmeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Astronaut Chestplate");
        legsmeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Astronaut Leggings");
        bootsmeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Astronaut Boots");

        chestplate.setItemMeta(chestmeta);
        leggings.setItemMeta(legsmeta);
        boots.setItemMeta(bootsmeta);

        player.getEquipment().setHelmet(getSpaceHelmet());
        player.getEquipment().setChestplate(chestplate);
        player.getEquipment().setLeggings(leggings);
        player.getEquipment().setBoots(boots);

    }
}
