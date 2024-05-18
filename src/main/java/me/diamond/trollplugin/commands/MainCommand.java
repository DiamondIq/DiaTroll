package me.diamond.trollplugin.commands;

import me.diamond.trollplugin.TrollPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class MainCommand implements CommandExecutor {


    Plugin plugin;

    public MainCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player p = (Player) sender;


        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Please Provide An argument");
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (p.hasPermission("diatroll.reload") || p.hasPermission("diatroll.admin")) {
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.DARK_GREEN + "-------" + ChatColor.YELLOW + "" + ChatColor.BOLD + "DIATROLL" + ChatColor.DARK_GREEN + "-------");
                sender.sendMessage(ChatColor.GREEN + "Config Reloaded!");
                sender.sendMessage(ChatColor.DARK_GREEN + "-----------------------");
                if (!TrollPlugin.getInstance().isVaultInstalled() && plugin.getConfig().getBoolean("Economy-Enabled")){
                    File configFile = new File(plugin.getDataFolder(), "config.yml");
                    FileConfiguration config1 = YamlConfiguration.loadConfiguration(configFile);
                    config1.set("Economy-Enabled", false);
                    Bukkit.getLogger().info("Can't enable Economy since Vault isn't installed");
                    try {
                        config1.save(configFile);
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
            }

        }else if (args[0].equalsIgnoreCase("help")){
            if (p.hasPermission("diatroll.help") || p.hasPermission("diatroll.admin")) {
                sender.sendMessage(ChatColor.DARK_GREEN + "-------" + ChatColor.YELLOW + "" + ChatColor.BOLD + "DIATROLL's COMMANDS" + ChatColor.DARK_GREEN + "-------");
                if (p.hasPermission("diatroll.troll") || p.hasPermission("diatroll.admin")){
                    sender.sendMessage(ChatColor.YELLOW + "/troll <player> " + ChatColor.GREEN + "This is the command that you troll players with");
                }
                if (sender.hasPermission("diatroll.vanish") || p.hasPermission("diatroll.admin")){
                    sender.sendMessage(ChatColor.YELLOW + "/vanish <player> " + ChatColor.GREEN + "Become Completely Invisible to other players (Commands, playerList etc.)");
                }
                sender.sendMessage(ChatColor.DARK_GREEN + "-----------------------------------");
            }else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
            }
        }


        return false;
    }
}
