package me.diamond.trollplugin.commands.Tab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class MainTabExecutor implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1){
            List<String> availableArgs = new ArrayList<>();
            availableArgs.add("reload");
            availableArgs.add("help");

            return availableArgs;

        }


        return null;
    }
}
