package dev.barfuzzle99.taplmoon.taplmoon.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SendTitleBarMessage {
    public static void sendMessage(Player player, String words, int decimal, int percentage) {
        String time;
        if (String.valueOf(decimal).length() == 1)
            time = ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + " " + percentage + ".0" + decimal + "%";
        else
            time = ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + " " + percentage + "." + decimal + "%";

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(words + time));
    }
}
