package me.diamond.trollplugin.commands;

import me.diamond.trollplugin.TrollPlugin;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    private final TrollPlugin plugin;

    public VanishCommand(TrollPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Please Provide a player!");
            }else {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "This player is not online. Please provide a valid player.");
                } else {
                    toggleVanish(target);
                    sender.sendMessage(ChatColor.GREEN + target.getName() + " is Invisible to other players");
                }
            }
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("diatroll.vanish") && !player.hasPermission("diatroll.admin")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to run this command.");
            return true;
        }

        if (args.length == 0) {
            toggleVanish(player);
        } else {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                player.sendMessage(ChatColor.RED + "This player is not online. Please provide a valid player.");
            } else {
                toggleVanish(target);
            }
        }
        return true;
    }

    private void toggleVanish(Player player) {
        if (plugin.playersVansihed.contains(player)) {
            for (Player people : Bukkit.getOnlinePlayers()) {
                people.showPlayer(plugin, player);
                sendJoinLeaveMessage(player, people, "join-message-on-vanish");

            }
            plugin.playersVansihed.remove(player);
            player.sendMessage(ChatColor.AQUA + "You are now " + ChatColor.BLUE + "Visible" + ChatColor.AQUA + " to other " + ChatColor.BLUE + "Players.");
        } else {
            for (Player people : Bukkit.getOnlinePlayers()) {
                people.hidePlayer(plugin, player);
                sendJoinLeaveMessage(player, people, "leave-message-on-vanish");
            }
            plugin.playersVansihed.add(player);
            player.sendMessage(ChatColor.AQUA + "You are now " + ChatColor.BLUE + "Invisible" + ChatColor.AQUA + " to other " + ChatColor.BLUE + "Players.");
        }
    }

    private void sendJoinLeaveMessage(Player player, Player recipient, String configPath) {
        if (plugin.getConfig().getBoolean("join-and-leave-message-on-vanish")) {
            String configMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(configPath));
            String playerName = player.getName();
            String prefix;
            String suffix;
            if (plugin.isLuckPermsInstalled()){
                LuckPerms luckPerms = LuckPermsProvider.get();
                User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
                prefix = user.getCachedData().getMetaData().getPrefix();
                suffix = user.getCachedData().getMetaData().getSuffix();
                String translatedPrefix = prefix != null ? ChatColor.translateAlternateColorCodes('&', prefix) : ""; // Handle null prefix
                String translatedSuffix = suffix != null ? ChatColor.translateAlternateColorCodes('&', suffix) : ""; // Handle null suffix
                configMessage = configMessage
                        .replace("{luckpermsprefix}", translatedPrefix)
                        .replace("{luckpermssuffix}", translatedSuffix);
            }
            configMessage = configMessage.replace("{player}", playerName);
            recipient.sendMessage(configMessage);
        }
    }
}
