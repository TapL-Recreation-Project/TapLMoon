package dev.barfuzzle99.taplmoon.taplmoon;

import dev.barfuzzle99.taplmoon.taplmoon.stored.PlayerPercentages;
import dev.barfuzzle99.taplmoon.taplmoon.utils.SuitManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class CmdTaplMoon implements TabExecutor {

    private static final String prefix = "[TaplMoon]";
    private static final String invalidUsageMsg = ChatColor.RED + " invalid usage. Please use /taplmoon help for help";
    private static final String noPermsMsg = ChatColor.RED + " You don't have permission to run this command.";

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(prefix + " " + invalidUsageMsg);
            return false;
        }

        switch (args[0]) {
            case "join":
                return cmdJoin(sender, command, label, args);
            case "create":
                return cmdCreate(sender, command, label, args);
            case "leave":
                return cmdLeave(sender, command, label, args);
            case "delete":
                return cmdDelete(sender, command, label, args);
            case "help":
                return cmdHelp(sender, command, label, args);
            case "forcejoinall":
                return forceJoinAll(sender, command, label, args);
            default:
                sender.sendMessage(prefix + " " + invalidUsageMsg);
                break;
        }
        return false;
    }

    public boolean forceJoinAll(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage(prefix + " " + invalidUsageMsg);
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + ChatColor.YELLOW + " This is a player only command!");
            return false;
        }
        if (!sender.isOp() && !sender.hasPermission("taplmoon.forcejoinall")) {
            sender.sendMessage(noPermsMsg);
            return false;
        }
        Player player = (Player) sender;
        if (MoonWorldUtil.getLoadedMoonWorlds().size() == 0) {
            if (MoonWorldUtil.areThereMoonWorlds()) {
                MoonWorldCreator.forceLoadWorlds();
            } else {
                sender.sendMessage(prefix + ChatColor.YELLOW + " No moon worlds created yet!");
                return false;
            }
        }
        for (Player others : Bukkit.getOnlinePlayers()) {
            if (MoonWorldUtil.isMoonWorld(others.getWorld())) {
                others.sendMessage(prefix + ChatColor.YELLOW + " You're already in a moon world!");
                continue;
            }
            World moonOverworld = Bukkit.getWorld("moon");
            ConfigUtil.savePlayerLastNormalWorldLoc(others, others.getLocation());
            Location lastMoonWorldLoc = ConfigUtil.getPlayerLastNo99WorldLoc(others);
            if (lastMoonWorldLoc != null && lastMoonWorldLoc.getWorld() != null) {
                others.teleport(lastMoonWorldLoc);
            } else {
                others.teleport(moonOverworld.getSpawnLocation());
            }
            PlayerPercentages.oxygenPercentage.put(others.getUniqueId(), 99);
            PlayerPercentages.oxygenDecimal.put(others.getUniqueId(), 99);
            others.setResourcePack("https://www.dropbox.com/s/oltfoub9xywjm1a/MoonPack%20%281%29.zip?dl=1");
            SuitManager.giveSpaceSuit(others);
        }
        return false;
    }

    public boolean cmdJoin(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage(prefix + " " + invalidUsageMsg);
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + ChatColor.YELLOW + " This is a player only command!");
            return false;
        }
        Player player = (Player) sender;
        if (MoonWorldUtil.getLoadedMoonWorlds().size() == 0) {
            if (MoonWorldUtil.areThereMoonWorlds()) {
                MoonWorldCreator.forceLoadWorlds();
            } else {
                sender.sendMessage(prefix + ChatColor.YELLOW + " No moon worlds created yet!");
                return false;
            }
        }
        if (MoonWorldUtil.isMoonWorld(player.getWorld())) {
            sender.sendMessage(prefix + ChatColor.YELLOW + " You're already in a moon world!");
            return false;
        }
        World moonOverworld = Bukkit.getWorld("moon");
        ConfigUtil.savePlayerLastNormalWorldLoc(player, player.getLocation());
        Location lastMoonWorldLoc = ConfigUtil.getPlayerLastNo99WorldLoc(player);
        if (lastMoonWorldLoc != null && lastMoonWorldLoc.getWorld() != null) {
            player.teleport(lastMoonWorldLoc);
        } else {
            player.teleport(moonOverworld.getSpawnLocation());
        }
        PlayerPercentages.oxygenPercentage.put(player.getUniqueId(), 99);
        PlayerPercentages.oxygenDecimal.put(player.getUniqueId(), 99);
        player.setResourcePack("https://www.dropbox.com/s/oltfoub9xywjm1a/MoonPack%20%281%29.zip?dl=1");
        SuitManager.giveSpaceSuit(player);
        return false;
    }

    public boolean cmdCreate(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp() && !sender.hasPermission("taplmoon.create")) {
            sender.sendMessage(noPermsMsg);
            return false;
        }

        /* TODO: NMS check
        if (!TaplMoon.isCompatibleWithCurrentNMSVersion()) {
            sender.sendMessage(prefix + ChatColor.RED + " It looks like the plugin is NOT compatible with your current server version. " +
                    "Please use 1.16.3 - 1.16.5 to ensure compatibility. This command will probably not work.");
        }
        */

        if (MoonWorldUtil.getLoadedMoonWorlds().size() > 0) {
            sender.sendMessage(prefix + ChatColor.YELLOW + " You've already created moon worlds!");
            return false;
        }
        switch (args.length) {
            case 1: // taplmoon create
                sender.sendMessage(prefix + ChatColor.YELLOW + " After creating the worlds, we'll have to kick all players to prevent a certain Minecraft");
                sender.sendMessage(ChatColor.YELLOW + " bug. They will able to rejoin immediately. Do /taplmoon create confirm whenever you're ready.");
                return false;
            case 2: // taplmoon create confirm
                if (args[1].equals("confirm")) {
                    sender.sendMessage(prefix + ChatColor.GREEN + " World creation started");
                    new MoonWorldCreator(sender).createWorlds();
                } else {
                    sender.sendMessage(prefix + " " + invalidUsageMsg);
                }
                return false;
            default:
                sender.sendMessage(prefix + " " + invalidUsageMsg);
                return false;
        }
    }

    public boolean cmdLeave(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isPermissionSet("taplmoon.leave") && !sender.hasPermission("taplmoon.leave")) {
            sender.sendMessage(noPermsMsg);
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + ChatColor.YELLOW + " This is a player only command!");
            return false;
        }
        Player player = (Player) sender;
        if (!MoonWorldUtil.isMoonWorld(player.getWorld())) {
            sender.sendMessage(prefix + ChatColor.YELLOW + " You can only leave from a moon world");
            return false;
        }

        if (args.length == 1) {
            ConfigUtil.savePlayerLastNo99WorldLoc(player, player.getLocation());
            Location loc = ConfigUtil.getPlayerLastNormalWorldLoc(player);
            if (loc == null) {
                TaplMoon.getInstance().getLogger().log(Level.SEVERE, "Could not get last location in normal world for " + player.getName() +
                        ". They'll be teleported to the main world. Is " + TaplMoon.getPlayerLastLocationsYml().getFile().getName() + " damaged?");
                player.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
                return false;
            }
            if (loc.getWorld() == null) {
                TaplMoon.getInstance().getLogger().log(Level.WARNING, "Player " + player.getName() +
                        " was in world that doesn't exist. Was it renamed or deleted?");
                player.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
                return false;
            }
            player.teleport(loc);
        } else {
            sender.sendMessage(prefix + " " + invalidUsageMsg);
        }
        player.setResourcePack("https://www.dropbox.com/s/c56b7jk8ctlaux1/emptyRP.zip?dl=1");
        return false;
    }

    public boolean cmdHelp(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isPermissionSet("taplmoon.help") && !sender.hasPermission("taplmoon.help")) {
            sender.sendMessage(noPermsMsg);
            return false;
        }
        sender.sendMessage(prefix + " Commands: \n" +
                "-/taplmoon help: shows this message\n" +
                "-/taplmoon join: joins the moon world\n" +
                "-/taplmoon leave: leaves that world\n" +
                "-/taplmoon create: creates a moon world\n" +
                "-/taplmoon delete: deletes the moon world\n");
        return false;
    }

    public boolean cmdDelete(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp() && !sender.hasPermission("taplmoon.delete")) {
            sender.sendMessage(noPermsMsg);
            return false;
        }
        if (args.length == 1) {
            sender.sendMessage(prefix + ChatColor.YELLOW + " This is not reversible! Do /taplmoon delete confirm if you're sure");
        } else if (args.length == 2) {
            if (args[1].equals("confirm")) {
                //Teleport players out of the worlds before deleting
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (MoonWorldUtil.isMoonWorld(player.getWorld())) {
                        Location lastNormalWorldLoc = ConfigUtil.getPlayerLastNormalWorldLoc(player);
                        if (lastNormalWorldLoc != null) {
                            player.teleport(lastNormalWorldLoc);
                        } else {
                            player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                        }
                    }
                }
                for (World world : Bukkit.getWorlds()) {
                    if (MoonWorldUtil.isMoonWorld(world)) {
                        try {
                            deleteFolder(world.getWorldFolder());
                            Bukkit.unloadWorld(world, false);
                        } catch (Exception ex) {
                            TaplMoon.getInstance().getLogger().log(Level.WARNING, "Could not delete folder: " + world.getWorldFolder().getAbsolutePath().toString());
                            ex.printStackTrace();
                        }
                    }
                }
                TaplMoon.getPlayerLastLocationsYml().getFile().delete();
                TaplMoon.getPlayerLastLocationsYml().createFile();
                TaplMoon.getPlayerLastLocationsYml().loadYamlFromFile();
                sender.sendMessage(prefix + " Done");
            } else {
                sender.sendMessage(invalidUsageMsg);
            }
        } else {
            sender.sendMessage(prefix + " " + invalidUsageMsg);
        }
        return false;
    }

    static void deleteFolder(File dir) {
        for (File subFile : dir.listFiles()) {
            if (subFile.isDirectory()) {
                deleteFolder(subFile);
            } else {
                subFile.delete();
            }
        }
        dir.delete();
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            suggestions.add("create");
            suggestions.add("join");
            suggestions.add("help");
            suggestions.add("leave");
            suggestions.add("delete");
        }
        return suggestions;
    }
}
