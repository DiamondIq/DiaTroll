package me.diamond.trollplugin.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class ItemUtils {

    public static Vector getOppositeDirection(Player p) {
        double yaw = p.getLocation().getYaw();
        yaw = (yaw + 180) % 360; // Adding 180 degrees and wrapping within 360 degrees

        // Converting the yaw angle to radians
        double radianYaw = Math.toRadians(yaw);

        // Calculating the opposite direction vector based on yaw
        double x = -Math.sin(radianYaw);
        double z = Math.cos(radianYaw);

        return new Vector(x, 0, z);
    }
    public static ItemStack hotPotato(){
        ItemStack p = new ItemStack(Material.BAKED_POTATO);
        ItemMeta m = p.getItemMeta();
        m.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "HOT POTATO");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_AQUA + "It will burn if you don't give it to someone else");
        m.setLore(lore);
        p.setItemMeta(m);

        return p;
    }

    public static ItemStack getAlienHead(String textureValue, String textureSignature) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setDisplayName(ChatColor.GREEN + "UFO");

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GREEN + "An alien kidnaps player to UFO and");
        lore.add(ChatColor.DARK_GREEN + "Then flies away");
        skullMeta.setLore(lore);

//        GameProfile profile = new GameProfile(UUID.randomUUID(), "Alien");
//        profile.getProperties().put("textures", new Property("textures", textureValue, textureSignature));
//
//        try {
//            Field profileField = skullMeta.getClass().getDeclaredField("profile");
//            profileField.setAccessible(true);
//            profileField.set(skullMeta, profile);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

        skull.setItemMeta(skullMeta);
        return skull;
    }

}

