package me.diamond.trollplugin.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CustomTrollUtil {
    private final String name;
    private final ArrayList<String> commands;
    private final ItemStack item;
    private final Player author;
    public CustomTrollUtil(String name, ArrayList<String> commands, ItemStack item, Player author){
        this.name = name;
        this.commands = commands;
        this.item = item;
        this.author = author;
    }

    private void createTroll(){

    }

}
