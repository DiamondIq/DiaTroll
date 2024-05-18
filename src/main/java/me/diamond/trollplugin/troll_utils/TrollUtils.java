package me.diamond.trollplugin.troll_utils;

import me.diamond.trollplugin.TrollPlugin;
import me.diamond.trollplugin.utils.ColorUtils;
import me.diamond.trollplugin.utils.ItemUtils;
import me.diamond.trollplugin.utils.ServerVersionUtils;
import me.diamond.trollplugin.troll_utils.ufo.UFO_Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.net.SocketException;
import java.util.*;

public class TrollUtils {

    public static void setPlugin(TrollPlugin plugin) {
        TrollUtils.plugin = plugin;
    }

    private static BukkitRunnable healthModifierTask;
    private static final NamespacedKey EXPLOSIVE_APPLE_KEY = new NamespacedKey(JavaPlugin.getPlugin(TrollPlugin.class), "explosiveApple");

    private static TrollPlugin plugin = TrollPlugin.getInstance();

    static boolean isEconomyEnabled() {
        if (plugin == null) {
            System.out.println("[Diatroll] Unabled to get config. Error 404");
            return false;
        }

        if (plugin.getConfig().getBoolean("Economy-Enabled") && plugin.isVaultInstalled()) {
            return true;
        } else {
            return false;
        }
    }

    public static void teleport(Player p, Player target) {
        Location targetLocation = target.getLocation();
        p.teleport(targetLocation);
        if (ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.15")) {
            String title = ColorUtils.translateColorCodes("&#fbad00T&#f9bf00e&#f7d100l&#f5e400e&#f3f600p&#f3f600o&#f5e400r&#f7d100t&#f9bf00e&#fbad00d\n");
            int fadeIn = 10;
            int stay = 40;
            int fadeOut = 10;
            p.sendTitle(title, null, fadeIn, stay, fadeOut);
        } else {
            p.sendTitle(ChatColor.GOLD + "Teleported", null);
        }
        p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 500.0f, 1.0f);
        p.setGameMode(GameMode.SPECTATOR);
    }

    public static void launch(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.setVelocity(target.getLocation().getDirection().multiply(0).setY(plugin.getConfig().getInt("launch-power")));
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("launch-cost"));
            if (response.transactionSuccess()) {
                target.setVelocity(target.getLocation().getDirection().multiply(0).setY(plugin.getConfig().getInt("launch-power")));
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }

        }
    }


    public static void lightning(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.getWorld().strikeLightning(target.getLocation());
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("lightning-cost"));
            if (response.transactionSuccess()) {
                target.getWorld().strikeLightning(target.getLocation());
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void noInternet(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.kickPlayer("Internal Exception: java.net.SocketException: Connection reset");
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("no-Internet-cost"));
            if (response.transactionSuccess()) {
                target.kickPlayer("Internal Exception: java.net.SocketException: Connection reset");
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void fire(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.setFireTicks(1000);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("set-on-fire-cost"));
            if (response.transactionSuccess()) {
                target.setFireTicks(1000);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void nausea(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1200, 0, false, false, false));
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("nausea-cost"));
            if (response.transactionSuccess()) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1200, 0, false, false, false));
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void slow(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.setWalkSpeed(0.1f);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("slow-cost"));
            if (response.transactionSuccess()) {
                target.setWalkSpeed(0.1f);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void resetslow(Player p, Player target) {
        target.setWalkSpeed(0.2f);
        p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " untrolled successfully.");

    }

    public static void wardenSound(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            float floatValue = (float) plugin.getConfig().getDouble("sound-pitch", 0.0);
            float floatValue1 = (float) plugin.getConfig().getDouble("sound-volume", 0.0);
            target.playSound(target.getLocation(), Sound.ENTITY_WARDEN_ANGRY, floatValue1, floatValue);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("fake-sound-cost"));
            if (response.transactionSuccess()) {
                float floatValue = (float) plugin.getConfig().getDouble("sound-pitch", 0.0);
                float floatValue1 = (float) plugin.getConfig().getDouble("sound-volume", 0.0);
                target.playSound(target.getLocation(), Sound.ENTITY_WARDEN_ANGRY, floatValue1, floatValue);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void fakeBan(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            String string = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fakeban-message"));

            if (string.contains("{player}")) {
                String name = p.getDisplayName();
                String reason = string.replace("{player}", name);
                target.kickPlayer(reason);
            } else {
                target.kickPlayer(string);
            }

            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("fake-ban-cost"));
            if (response.transactionSuccess()) {
                String string = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fakeban-message"));

                if (string.contains("{player}")) {
                    String name = p.getDisplayName();
                    String reason = string.replace("{player}", name);
                    target.kickPlayer(reason);
                } else {
                    target.kickPlayer(string);
                }

                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void noGravity(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.setGravity(false);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("no-gravity-cost"));
            if (response.transactionSuccess()) {
                target.setGravity(false);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void gravity(Player p, Player target) {
        target.setGravity(true);
        p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " untrolled successfully.");

    }

    public static void creeperSound(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            float floatValue = (float) plugin.getConfig().getDouble("sound-pitch", 0.0);
            float floatValue1 = (float) plugin.getConfig().getDouble("sound-volume", 0.0);
            target.playSound(target.getLocation(), Sound.ENTITY_CREEPER_PRIMED, floatValue1, floatValue);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("fake-sound-cost"));
            if (response.transactionSuccess()) {
                float floatValue = (float) plugin.getConfig().getDouble("sound-pitch", 0.0);
                float floatValue1 = (float) plugin.getConfig().getDouble("sound-volume", 0.0);
                target.playSound(target.getLocation(), Sound.ENTITY_CREEPER_PRIMED, floatValue1, floatValue);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void cobweb(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            Long delay = plugin.getConfig().getLong("cobweb-time-after-disappear");
            Long multipliedValue = delay * 20;

            int x = target.getLocation().getBlockX();
            int y = target.getLocation().getBlockY();
            int z = target.getLocation().getBlockZ();
            Location loc = new Location(target.getWorld(), x + 1, y, z + 1);
            Location loc1 = new Location(target.getWorld(), x - 1, y, z + 1);
            Location loc2 = new Location(target.getWorld(), x - 1, y, z);
            Location loc3 = new Location(target.getWorld(), x + 1, y, z);
            Location loc4 = new Location(target.getWorld(), x, y, z);
            Location loc5 = new Location(target.getWorld(), x, y, z + 1);
            Location loc6 = new Location(target.getWorld(), x - 1, y, z - 1);
            Location loc7 = new Location(target.getWorld(), x + 1, y, z - 1);
            Location loc8 = new Location(target.getWorld(), x, y, z - 1);
            target.playSound(target.getLocation(), Sound.ENTITY_SKELETON_SHOOT, 500.0f, 1.0f);

            y = loc.getWorld().getHighestBlockYAt(loc);
            y = loc1.getWorld().getHighestBlockYAt(loc1);
            y = loc2.getWorld().getHighestBlockYAt(loc2);
            y = loc3.getWorld().getHighestBlockYAt(loc3);
            y = loc4.getWorld().getHighestBlockYAt(loc4);
            y = loc5.getWorld().getHighestBlockYAt(loc5);
            y = loc6.getWorld().getHighestBlockYAt(loc6);
            y = loc7.getWorld().getHighestBlockYAt(loc7);
            y = loc8.getWorld().getHighestBlockYAt(loc8);
            Location[] locations = {loc, loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8};

            for (Location location : locations) {
                int highestY = location.getWorld().getHighestBlockYAt(location);
                location.setY(highestY + 1);
                // Additional operations if needed
            }

            // Further operations with individual locs
            loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 1);
            loc1.setY(loc1.getWorld().getHighestBlockYAt(loc1) + 1);
            loc2.setY(loc2.getWorld().getHighestBlockYAt(loc2) + 1);
            loc3.setY(loc3.getWorld().getHighestBlockYAt(loc3) + 1);
            loc4.setY(loc4.getWorld().getHighestBlockYAt(loc4) + 1);
            loc5.setY(loc5.getWorld().getHighestBlockYAt(loc5) + 1);
            loc6.setY(loc6.getWorld().getHighestBlockYAt(loc6) + 1);
            loc7.setY(loc7.getWorld().getHighestBlockYAt(loc7) + 1);
            loc8.setY(loc8.getWorld().getHighestBlockYAt(loc8) + 1);


            loc.getBlock().setType(Material.COBWEB);
            loc1.getBlock().setType(Material.COBWEB);
            loc2.getBlock().setType(Material.COBWEB);
            loc3.getBlock().setType(Material.COBWEB);
            loc4.getBlock().setType(Material.COBWEB);
            loc5.getBlock().setType(Material.COBWEB);
            loc6.getBlock().setType(Material.COBWEB);
            loc7.getBlock().setType(Material.COBWEB);
            loc8.getBlock().setType(Material.COBWEB);


            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    loc.getBlock().setType(Material.AIR);
                    loc1.getBlock().setType(Material.AIR);
                    loc2.getBlock().setType(Material.AIR);
                    loc3.getBlock().setType(Material.AIR);
                    loc4.getBlock().setType(Material.AIR);
                    loc5.getBlock().setType(Material.AIR);
                    loc6.getBlock().setType(Material.AIR);
                    loc7.getBlock().setType(Material.AIR);
                    loc8.getBlock().setType(Material.AIR);

                }
            }, multipliedValue);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("cobweb-cost"));
            if (response.transactionSuccess()) {
                int x = target.getLocation().getBlockX();
                int y = target.getLocation().getBlockY();
                int z = target.getLocation().getBlockZ();
                Location loc = new Location(target.getWorld(), x + 1, y, z + 1);
                Location loc1 = new Location(target.getWorld(), x - 1, y, z + 1);
                Location loc2 = new Location(target.getWorld(), x - 1, y, z);
                Location loc3 = new Location(target.getWorld(), x + 1, y, z);
                Location loc4 = new Location(target.getWorld(), x, y, z);
                Location loc5 = new Location(target.getWorld(), x, y, z + 1);
                Location loc6 = new Location(target.getWorld(), x - 1, y, z - 1);
                Location loc7 = new Location(target.getWorld(), x + 1, y, z - 1);
                Location loc8 = new Location(target.getWorld(), x, y, z - 1);
                target.playSound(target.getLocation(), Sound.ENTITY_SKELETON_SHOOT, 500.0f, 1.0f);

                y = loc.getWorld().getHighestBlockYAt(loc);
                y = loc1.getWorld().getHighestBlockYAt(loc1);
                y = loc2.getWorld().getHighestBlockYAt(loc2);
                y = loc3.getWorld().getHighestBlockYAt(loc3);
                y = loc4.getWorld().getHighestBlockYAt(loc4);
                y = loc5.getWorld().getHighestBlockYAt(loc5);
                y = loc6.getWorld().getHighestBlockYAt(loc6);
                y = loc7.getWorld().getHighestBlockYAt(loc7);
                y = loc8.getWorld().getHighestBlockYAt(loc8);
                Location[] locations = {loc, loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8};

                for (Location location : locations) {
                    int highestY = location.getWorld().getHighestBlockYAt(location);
                    location.setY(highestY + 1);
                    // Additional operations if needed
                }

                // Further operations with individual locs
                loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 1);
                loc1.setY(loc1.getWorld().getHighestBlockYAt(loc1) + 1);
                loc2.setY(loc2.getWorld().getHighestBlockYAt(loc2) + 1);
                loc3.setY(loc3.getWorld().getHighestBlockYAt(loc3) + 1);
                loc4.setY(loc4.getWorld().getHighestBlockYAt(loc4) + 1);
                loc5.setY(loc5.getWorld().getHighestBlockYAt(loc5) + 1);
                loc6.setY(loc6.getWorld().getHighestBlockYAt(loc6) + 1);
                loc7.setY(loc7.getWorld().getHighestBlockYAt(loc7) + 1);
                loc8.setY(loc8.getWorld().getHighestBlockYAt(loc8) + 1);


                loc.getBlock().setType(Material.COBWEB);
                loc1.getBlock().setType(Material.COBWEB);
                loc2.getBlock().setType(Material.COBWEB);
                loc3.getBlock().setType(Material.COBWEB);
                loc4.getBlock().setType(Material.COBWEB);
                loc5.getBlock().setType(Material.COBWEB);
                loc6.getBlock().setType(Material.COBWEB);
                loc7.getBlock().setType(Material.COBWEB);
                loc8.getBlock().setType(Material.COBWEB);

                long delay = plugin.getConfig().getLong("cobweb-time-after-disappear");
                long multipliedValue = delay * 20;


                BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                scheduler.runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        loc.getBlock().setType(Material.AIR);
                        loc1.getBlock().setType(Material.AIR);
                        loc2.getBlock().setType(Material.AIR);
                        loc3.getBlock().setType(Material.AIR);
                        loc4.getBlock().setType(Material.AIR);
                        loc5.getBlock().setType(Material.AIR);
                        loc6.getBlock().setType(Material.AIR);
                        loc7.getBlock().setType(Material.AIR);
                        loc8.getBlock().setType(Material.AIR);

                    }
                }, multipliedValue);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void timeWarp(Player p, Player target) {
        Long delay = plugin.getConfig().getLong("time-warp-time");
        Long multipliedValue = delay * 20;
        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            Location previousLocation = target.getLocation();
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    target.teleport(previousLocation);
                }
            }, multipliedValue);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("time-warp-cost"));
            if (response.transactionSuccess()) {
                Location previousLocation = target.getLocation();
                BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                scheduler.runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        target.teleport(previousLocation);
                    }
                }, multipliedValue);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void explosiveApple(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            ItemStack goldenApple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
            ItemMeta meta = goldenApple.getItemMeta();
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            dataContainer.set(EXPLOSIVE_APPLE_KEY, PersistentDataType.BYTE, (byte) 1);
            goldenApple.setItemMeta(meta);

            target.getInventory().addItem(goldenApple);

            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("explosixe-apple-cost"));
            if (response.transactionSuccess()) {
                ItemStack goldenApple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
                ItemMeta meta = goldenApple.getItemMeta();
                PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
                dataContainer.set(EXPLOSIVE_APPLE_KEY, PersistentDataType.BYTE, (byte) 1);
                goldenApple.setItemMeta(meta);

                target.getInventory().addItem(goldenApple);

                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void meteor(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {

            Location location = target.getLocation().add(0, 20, 0);

            Fireball fireball = target.getWorld().spawn(location, Fireball.class);
            fireball.setDirection(target.getLocation().subtract(location).toVector());
            fireball.setYield(plugin.getConfig().getInt("meteor-explosion-power"));
            fireball.setIsIncendiary(true);
            fireball.setFireTicks(0);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("meteor-cost"));
            if (response.transactionSuccess()) {
                Location location = target.getLocation().add(0, 20, 0);
                Fireball fireball = target.getWorld().spawn(location, Fireball.class);
                fireball.setDirection(target.getLocation().subtract(location).toVector());
                fireball.setYield(plugin.getConfig().getInt("meteor-explosion-power"));
                fireball.setIsIncendiary(true);
                fireball.setFireTicks(0);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void patata(Player p, Player target) {
        Long delay = plugin.getConfig().getLong("patata-time");
        Long multipliedValue = delay * 20;

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            ItemStack[] originalInventory = target.getInventory().getContents();

            // Replace all items with potatoes
            ItemStack potato = new ItemStack(Material.POTATO, 1);
            ItemMeta potato_meta = potato.getItemMeta();
            potato_meta.setDisplayName(ChatColor.DARK_AQUA + "Potato");
            potato.setItemMeta(potato_meta);
            ItemStack[] newInventory = new ItemStack[36];
            for (int i = 0; i < newInventory.length; i++) {
                newInventory[i] = potato.clone();
            }
            target.getInventory().setContents(newInventory);

            // Schedule a task to restore the original inventory after time
            new BukkitRunnable() {
                @Override
                public void run() {
                    target.getInventory().setContents(originalInventory);
                }
            }.runTaskLater(plugin, multipliedValue);

            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("patata-cost"));
            if (response.transactionSuccess()) {
                ItemStack[] originalInventory = target.getInventory().getContents();

                // Replace all items with potatoes
                ItemStack potato = new ItemStack(Material.POTATO, 1);
                ItemMeta potato_meta = potato.getItemMeta();
                potato_meta.setDisplayName(ChatColor.DARK_AQUA + "Potato");
                potato.setItemMeta(potato_meta);
                ItemStack[] newInventory = new ItemStack[36];
                for (int i = 0; i < newInventory.length; i++) {
                    newInventory[i] = potato.clone();
                }
                target.getInventory().setContents(newInventory);

                // Schedule a task to restore the original inventory after time
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        target.getInventory().setContents(originalInventory);
                    }
                }.runTaskLater(plugin, multipliedValue);

                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void fakeMessage(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {

            List<String> messages = new ArrayList<>();
            if (plugin.getConfig().isList("messages")) {
                List<String> configMessages = plugin.getConfig().getStringList("messages");
                messages.addAll(configMessages);
            }

            int randomIndex = new Random().nextInt(messages.size());
            String randomMessage = messages.get(randomIndex);

            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                String chatFormat = plugin.getConfig().getString("chat-format");
                String playerColorCode = plugin.getConfig().getString("player-color", "&r"); // Default to red if not specified
                String messageColorCode = plugin.getConfig().getString("message-color", "&r"); // Default to gold if not specified
                String playerName = target.getName() != null ? target.getName() : ""; // Handle null player name

                String formattedMessage = chatFormat
                        .replace("{player}", ChatColor.translateAlternateColorCodes('&', playerColorCode) + playerName + ChatColor.RESET)
                        .replace("{message}", ChatColor.translateAlternateColorCodes('&', messageColorCode) + randomMessage + ChatColor.RESET);

                if (plugin.isLuckPermsInstalled()) {
                    LuckPerms luckPerms = LuckPermsProvider.get();
                    User user = luckPerms.getPlayerAdapter(Player.class).getUser(target);
                    String prefix = user.getCachedData().getMetaData().getPrefix();
                    String suffix = user.getCachedData().getMetaData().getSuffix();
                    String translatedPrefix = prefix != null ? ChatColor.translateAlternateColorCodes('&', prefix) : ""; // Handle null prefix
                    String translatedSuffix = suffix != null ? ChatColor.translateAlternateColorCodes('&', suffix) : ""; // Handle null suffix
                    formattedMessage = formattedMessage
                            .replace("{luckpermsprefix}", translatedPrefix)
                            .replace("{luckpermssuffix}", translatedSuffix);
                    player.sendMessage(formattedMessage);
                } else {
                    player.sendMessage(formattedMessage);
                }
            }
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("fake-messages-cost"));
            if (response.transactionSuccess()) {

                List<String> messages = new ArrayList<>();
                if (plugin.getConfig().isList("messages")) {
                    List<String> configMessages = plugin.getConfig().getStringList("messages");
                    messages.addAll(configMessages);
                }

                int randomIndex = new Random().nextInt(messages.size());
                String randomMessage = messages.get(randomIndex);

                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    String chatFormat = plugin.getConfig().getString("chat-format");
                    String playerColorCode = plugin.getConfig().getString("player-color", "&r"); // Default to red if not specified
                    String messageColorCode = plugin.getConfig().getString("message-color", "&r"); // Default to gold if not specified
                    String playerName = target.getName() != null ? target.getName() : ""; // Handle null player name

                    String formattedMessage = chatFormat
                            .replace("{player}", ChatColor.translateAlternateColorCodes('&', playerColorCode) + playerName + ChatColor.RESET)
                            .replace("{message}", ChatColor.translateAlternateColorCodes('&', messageColorCode) + randomMessage + ChatColor.RESET);

                    if (plugin.isLuckPermsInstalled()) {
                        LuckPerms luckPerms = LuckPermsProvider.get();
                        User user = luckPerms.getPlayerAdapter(Player.class).getUser(target);
                        String prefix = user.getCachedData().getMetaData().getPrefix();
                        String suffix = user.getCachedData().getMetaData().getSuffix();
                        String translatedPrefix = prefix != null ? ChatColor.translateAlternateColorCodes('&', prefix) : ""; // Handle null prefix
                        String translatedSuffix = suffix != null ? ChatColor.translateAlternateColorCodes('&', suffix) : ""; // Handle null suffix
                        formattedMessage = formattedMessage
                                .replace("{luckpermsprefix}", translatedPrefix)
                                .replace("{luckpermssuffix}", translatedSuffix);
                        player.sendMessage(formattedMessage);
                    } else {
                        player.sendMessage(formattedMessage);
                    }
                }
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void oneHeart(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            if (healthModifierTask != null) {
                // Player already has a health modifier task running, cancel it
                healthModifierTask.cancel();
                healthModifierTask = null;
            } else {
                // Create a new health modifier task
                healthModifierTask = new BukkitRunnable() {
                    double maxHealth = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                    double currentHealth = target.getHealth();

                    @Override
                    public void run() {
                        if (currentHealth <= 2) {
                            // Player has reached 1 heart, stop reducing health and start increasing health
                            cancel();
                            healthModifierTask = new BukkitRunnable() {
                                double maxHealth = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                                double currentHealth = target.getHealth();

                                @Override
                                public void run() {
                                    if (currentHealth >= maxHealth) {
                                        // Player has reached max health, stop increasing health
                                        cancel();
                                        healthModifierTask = null;
                                        return;
                                    }

                                    // Add 1 heart to the player's max health
                                    maxHealth += 2;
                                    target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
                                    target.setHealth(maxHealth);
                                    // Set the player's health to the new max health
                                    currentHealth = maxHealth;
                                    target.setHealth(currentHealth);
                                }
                            };
                            // Schedule the health modifier task to run every minute (1200 ticks)
                            healthModifierTask.runTaskTimer(plugin, 600L, 1200L);
                            return;
                        }

                        // Subtract 1 heart from the player's max health
                        maxHealth -= 2;
                        target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);

                        // Set the player's health to the new max health
                        currentHealth = maxHealth;
                        target.setHealth(currentHealth);
                    }
                };
                // Schedule the health modifier task to run every half second (10 ticks)
                healthModifierTask.runTaskTimer(plugin, 0L, 10L);
            }

            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    target.setMaxHealth(20);
                    target.setHealth(20);
                }
            }, 1200L);


            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("meteor-cost"));
            if (response.transactionSuccess()) {
                if (healthModifierTask != null) {
                    // Player already has a health modifier task running, cancel it
                    healthModifierTask.cancel();
                    healthModifierTask = null;
                } else {
                    // Create a new health modifier task
                    healthModifierTask = new BukkitRunnable() {
                        double maxHealth = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                        double currentHealth = target.getHealth();

                        @Override
                        public void run() {
                            if (currentHealth <= 2) {
                                // Player has reached 1 heart, stop reducing health and start increasing health
                                cancel();
                                healthModifierTask = new BukkitRunnable() {
                                    double maxHealth = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                                    double currentHealth = target.getHealth();

                                    @Override
                                    public void run() {
                                        if (currentHealth >= maxHealth) {
                                            // Player has reached max health, stop increasing health
                                            cancel();
                                            healthModifierTask = null;
                                            return;
                                        }

                                        // Add 1 heart to the player's max health
                                        maxHealth += 2;
                                        target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
                                        target.setHealth(maxHealth);
                                        // Set the player's health to the new max health
                                        currentHealth = maxHealth;
                                        target.setHealth(currentHealth);
                                    }
                                };
                                // Schedule the health modifier task to run every minute (1200 ticks)
                                healthModifierTask.runTaskTimer(plugin, 600L, 1200L);
                                return;
                            }

                            // Subtract 1 heart from the player's max health
                            maxHealth -= 2;
                            target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);

                            // Set the player's health to the new max health
                            currentHealth = maxHealth;
                            target.setHealth(currentHealth);
                        }
                    };
                    // Schedule the health modifier task to run every half second (10 ticks)
                    healthModifierTask.runTaskTimer(plugin, 0L, 10L);
                }

                BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                scheduler.runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        target.setMaxHealth(20);
                        target.setHealth(20);
                    }
                }, 1200L);

                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void pepe(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            Location loc1 = location.clone().add(1, 15, 0);
            Location loc2 = location.clone().add(-1, 15, 0);
            Location loc3 = location.clone().add(0, 16, 0);
            Location loc4 = location.clone().add(0, 17, 0);
            Location loc5 = location.clone().add(0, 18, 0);
            Location[] locations = {loc1, loc2, loc3, loc4, loc5};

            for (Location location1 : locations) {
                FallingBlock fallingBlock;
                Material material = Material.valueOf(plugin.getConfig().getString("pp-block"));
                if (material != null) {
                    fallingBlock = p.getWorld().spawnFallingBlock(location1, material.createBlockData());
                } else {
                    fallingBlock = p.getWorld().spawnFallingBlock(location1, Material.STONE.createBlockData());
                }
                PersistentDataContainer dataContainer = fallingBlock.getPersistentDataContainer();
                dataContainer.set(new NamespacedKey(plugin, "pepe"), PersistentDataType.INTEGER, 1);
                final FallingBlock finalFallingBlock = fallingBlock;
                finalFallingBlock.setDropItem(false);

                finalFallingBlock.setHurtEntities(false);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        finalFallingBlock.setHurtEntities(true);
                    }
                }, 1L);

            }
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("pp.cost"));
            if (response.transactionSuccess()) {
                Location loc1 = location.clone().add(1, 15, 0);
                Location loc2 = location.clone().add(-1, 15, 0);
                Location loc3 = location.clone().add(0, 16, 0);
                Location loc4 = location.clone().add(0, 17, 0);
                Location loc5 = location.clone().add(0, 18, 0);
                Location[] locations = {loc1, loc2, loc3, loc4, loc5};

                for (Location location1 : locations) {
                    FallingBlock fallingBlock;
                    Material material = Material.valueOf(plugin.getConfig().getString("pp-block"));
                    if (material != null) {
                        fallingBlock = p.getWorld().spawnFallingBlock(location1, material.createBlockData());
                    } else {
                        fallingBlock = p.getWorld().spawnFallingBlock(location1, Material.STONE.createBlockData());
                    }
                    PersistentDataContainer dataContainer = fallingBlock.getPersistentDataContainer();
                    dataContainer.set(new NamespacedKey(plugin, "pepe"), PersistentDataType.INTEGER, 1);
                    final FallingBlock finalFallingBlock = fallingBlock;
                    finalFallingBlock.setDropItem(false);

                    finalFallingBlock.setHurtEntities(false);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            finalFallingBlock.setHurtEntities(true);
                        }
                    }, 1L);

                }
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void cancelBreakBLocks(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            Long time = plugin.getConfig().getLong("Cancel-Block-Breaks-time");
            PersistentDataContainer data = target.getPersistentDataContainer();
            NamespacedKey namespacedKey = new NamespacedKey(plugin, "cantbreakblocks");
            data.set(namespacedKey, PersistentDataType.INTEGER, 69);
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    data.remove(namespacedKey);
                }
            }, time * 20);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Zombie.cost"));
            if (response.transactionSuccess()) {
                Long time = plugin.getConfig().getLong("Cancel-Block-Breaks-time");
                PersistentDataContainer data = target.getPersistentDataContainer();
                NamespacedKey namespacedKey = new NamespacedKey(plugin, "cantbreakblocks");
                data.set(namespacedKey, PersistentDataType.INTEGER, 69);
                BukkitScheduler scheduler = Bukkit.getScheduler();
                scheduler.runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        data.remove(namespacedKey);
                    }
                }, time * 20);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void cage(Player p, Player target) {
        Location location = target.getLocation();
        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            PersistentDataContainer data = target.getPersistentDataContainer();
            NamespacedKey namespacedKey = new NamespacedKey(plugin, "istrappeddiatroll");
            data.set(namespacedKey, PersistentDataType.INTEGER, 699420);
            NamespacedKey namespaced = new NamespacedKey(plugin, "cantwalkcagebecause");
            data.set(namespaced, PersistentDataType.INTEGER, 699420);

            Location loc1 = location.clone().add(0, 2, 0);
            loc1.getBlock().setType(Material.OBSIDIAN);
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    loc1.getBlock().setType(Material.AIR);

                }
            }, plugin.getConfig().getLong("cage-disappear-time") * 20);
            Location loc2 = location.clone().add(1, 22, 0);
            Location loc3 = location.clone().add(-1, 22, 0);
            Location loc4 = location.clone().add(0, 22, 1);
            Location loc5 = location.clone().add(0, 22, -1);
            Location loc6 = location.clone().add(1, 22, 1);
            Location loc7 = location.clone().add(-1, 22, 1);
            Location loc8 = location.clone().add(-1, 22, -1);
            Location loc9 = location.clone().add(1, 22, -1);
            Location loc11 = location.clone().add(-1, 21, 0);
            Location loc12 = location.clone().add(0, 21, 1);
            Location loc13 = location.clone().add(0, 21, -1);
            Location loc14 = location.clone().add(1, 21, 1);
            Location loc15 = location.clone().add(-1, 21, 1);
            Location loc16 = location.clone().add(-1, 21, -1);
            Location loc17 = location.clone().add(1, 21, -1);

            Location[] allLocations = {loc2, loc3, loc4, loc5, loc6, loc7, loc8, loc9, loc11, loc12, loc13, loc14, loc15, loc16, loc17};
            for (Location allLocation : allLocations) {
                Location[] bars = {loc2, loc3, loc4, loc5, loc11, loc12};
                for (Location barLocation : bars) {
                    if (barLocation.getBlock().getType() == Material.AIR) {
                        FallingBlock fallingIronBar = p.getWorld().spawnFallingBlock(barLocation, Material.IRON_BARS.createBlockData());
                        fallingIronBar.setDropItem(false);
                        fallingIronBar.setHurtEntities(false);
                        PersistentDataContainer fallingdata = fallingIronBar.getPersistentDataContainer();
                        NamespacedKey namespacedKeyKey2 = new NamespacedKey(plugin, "istrapblock");
                        fallingdata.set(namespacedKeyKey2, PersistentDataType.INTEGER, 699420);
                    }
                }

                Location[] obbyLocations = {loc6, loc7, loc8, loc9, loc14, loc15, loc16, loc17};
                for (Location obbyLocation : obbyLocations) {
                    if (obbyLocation.getBlock().getType() == Material.AIR) {
                        FallingBlock fallingObby = p.getWorld().spawnFallingBlock(obbyLocation, Material.OBSIDIAN.createBlockData());
                        fallingObby.setDropItem(false);
                        fallingObby.setHurtEntities(false);
                        PersistentDataContainer fallingdata = fallingObby.getPersistentDataContainer();
                        NamespacedKey namespacedKeyKey2 = new NamespacedKey(plugin, "istrapblock");
                        fallingdata.set(namespacedKeyKey2, PersistentDataType.INTEGER, 699420);
                    }
                }
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        data.remove(namespaced);
                    }
                }, plugin.getConfig().getLong("cage-disappear-time") * 3);
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        data.remove(namespacedKey);
                    }
                }, plugin.getConfig().getLong("cage-disappear-time") * 20);
            }
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    FallingBlock fallingObby = p.getWorld().spawnFallingBlock(loc2, Material.IRON_BARS.createBlockData());
                    fallingObby.setDropItem(false);
                    fallingObby.setHurtEntities(false);
                    PersistentDataContainer fallingdata = fallingObby.getPersistentDataContainer();
                    NamespacedKey namespacedKeyKey2 = new NamespacedKey(plugin, "istrapblock");
                    fallingdata.set(namespacedKeyKey2, PersistentDataType.INTEGER, 699420);

                    FallingBlock fallingObby2 = p.getWorld().spawnFallingBlock(loc5, Material.IRON_BARS.createBlockData());
                    fallingObby2.setDropItem(false);
                    fallingObby2.setHurtEntities(false);
                    PersistentDataContainer fallingdata2 = fallingObby2.getPersistentDataContainer();
                    fallingdata2.set(namespacedKeyKey2, PersistentDataType.INTEGER, 699420);
                }
            }, 2L);


            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");

        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("cage-cost"));
            if (response.transactionSuccess()) {
                PersistentDataContainer data = target.getPersistentDataContainer();
                NamespacedKey namespacedKey = new NamespacedKey(plugin, "istrappeddiatroll");
                data.set(namespacedKey, PersistentDataType.INTEGER, 699420);
                NamespacedKey namespaced = new NamespacedKey(plugin, "cantwalkcagebecause");
                data.set(namespaced, PersistentDataType.INTEGER, 699420);

                Location loc1 = location.clone().add(0, 2, 0);
                loc1.getBlock().setType(Material.OBSIDIAN);
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        loc1.getBlock().setType(Material.AIR);

                    }
                }, plugin.getConfig().getLong("cage-disappear-time") * 20);
                Location loc2 = location.clone().add(1, 22, 0);
                Location loc3 = location.clone().add(-1, 22, 0);
                Location loc4 = location.clone().add(0, 22, 1);
                Location loc5 = location.clone().add(0, 22, -1);
                Location loc6 = location.clone().add(1, 22, 1);
                Location loc7 = location.clone().add(-1, 22, 1);
                Location loc8 = location.clone().add(-1, 22, -1);
                Location loc9 = location.clone().add(1, 22, -1);
                Location loc11 = location.clone().add(-1, 21, 0);
                Location loc12 = location.clone().add(0, 21, 1);
                Location loc13 = location.clone().add(0, 21, -1);
                Location loc14 = location.clone().add(1, 21, 1);
                Location loc15 = location.clone().add(-1, 21, 1);
                Location loc16 = location.clone().add(-1, 21, -1);
                Location loc17 = location.clone().add(1, 21, -1);

                Location[] allLocations = {loc2, loc3, loc4, loc5, loc6, loc7, loc8, loc9, loc11, loc12, loc13, loc14, loc15, loc16, loc17};
                for (Location allLocation : allLocations) {
                    Location[] bars = {loc2, loc3, loc4, loc5, loc11, loc12};
                    for (Location barLocation : bars) {
                        if (barLocation.getBlock().getType() == Material.AIR) {
                            FallingBlock fallingIronBar = p.getWorld().spawnFallingBlock(barLocation, Material.IRON_BARS.createBlockData());
                            fallingIronBar.setDropItem(false);
                            fallingIronBar.setHurtEntities(false);
                            PersistentDataContainer fallingdata = fallingIronBar.getPersistentDataContainer();
                            NamespacedKey namespacedKeyKey2 = new NamespacedKey(plugin, "istrapblock");
                            fallingdata.set(namespacedKeyKey2, PersistentDataType.INTEGER, 699420);
                        }
                    }

                    Location[] obbyLocations = {loc6, loc7, loc8, loc9, loc14, loc15, loc16, loc17};
                    for (Location obbyLocation : obbyLocations) {
                        if (obbyLocation.getBlock().getType() == Material.AIR) {
                            FallingBlock fallingObby = p.getWorld().spawnFallingBlock(obbyLocation, Material.OBSIDIAN.createBlockData());
                            fallingObby.setDropItem(false);
                            fallingObby.setHurtEntities(false);
                            PersistentDataContainer fallingdata = fallingObby.getPersistentDataContainer();
                            NamespacedKey namespacedKeyKey2 = new NamespacedKey(plugin, "istrapblock");
                            fallingdata.set(namespacedKeyKey2, PersistentDataType.INTEGER, 699420);
                        }
                    }
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            data.remove(namespaced);
                        }
                    }, plugin.getConfig().getLong("cage-disappear-time"));
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            data.remove(namespacedKey);
                        }
                    }, plugin.getConfig().getLong("cage-disappear-time") * 20);
                }
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        FallingBlock fallingObby = p.getWorld().spawnFallingBlock(loc2, Material.IRON_BARS.createBlockData());
                        fallingObby.setDropItem(false);
                        fallingObby.setHurtEntities(false);
                        PersistentDataContainer fallingdata = fallingObby.getPersistentDataContainer();
                        NamespacedKey namespacedKeyKey2 = new NamespacedKey(plugin, "istrapblock");
                        fallingdata.set(namespacedKeyKey2, PersistentDataType.INTEGER, 699420);

                        FallingBlock fallingObby2 = p.getWorld().spawnFallingBlock(loc5, Material.IRON_BARS.createBlockData());
                        fallingObby2.setDropItem(false);
                        fallingObby2.setHurtEntities(false);
                        PersistentDataContainer fallingdata2 = fallingObby2.getPersistentDataContainer();
                        fallingdata2.set(namespacedKeyKey2, PersistentDataType.INTEGER, 699420);
                    }
                }, 2L);


                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void swapLavaWater(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            if (!plugin.lavaSwapped.contains(target)) {
                plugin.lavaSwapped.add(target);
            } else {
                plugin.lavaSwapped.remove(target);
            }
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("swapLavaWater-cost"));
            if (response.transactionSuccess()) {
                if (!plugin.lavaSwapped.contains(target)) {
                    plugin.lavaSwapped.add(target);
                } else {
                    plugin.lavaSwapped.remove(target);
                }
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void hotPotato(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.getInventory().addItem(ItemUtils.hotPotato());
            target.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You just got a HOT POTATO");
            target.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Give it to someone else or it will burn");
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    if (target.getInventory().contains(ItemUtils.hotPotato())) {
                        target.damage(10);
                        target.setFireTicks(1000);
                        target.getInventory().remove(ItemUtils.hotPotato());
                        target.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "THE HOT POTATO just burned");
                    }
                }
            }, plugin.getConfig().getLong("hotpotato-burn-time") * 20);

            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("hotpotato-cost"));
            if (response.transactionSuccess()) {
                target.getInventory().addItem(ItemUtils.hotPotato());
                target.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You just got a HOT POTATO");
                target.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Give it to someone else or it will burn");
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (target.getInventory().contains(ItemUtils.hotPotato())) {
                            target.damage(10);
                            target.setFireTicks(1000);
                            target.getInventory().remove(ItemUtils.hotPotato());
                            target.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "THE HOT POTATO just burned");
                        }
                    }
                }, plugin.getConfig().getLong("hotpotato-burn-time"));

                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void reach(Player p, Player target) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            if (!plugin.loweredReach.containsKey(target)) {
                plugin.loweredReach.put(target, 2);
            } else {
                if (plugin.loweredReach.get(target) == 2) {
                    plugin.loweredReach.remove(target);
                    plugin.loweredReach.put(target, 1);
                } else if (plugin.loweredReach.get(target) == 1) {
                    plugin.loweredReach.remove(target);
                }

            }
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Lower-Reach-cost"));
            if (response.transactionSuccess()) {
                if (!plugin.loweredReach.containsKey(target)) {
                    plugin.loweredReach.put(target, 2);
                } else {
                    if (plugin.loweredReach.get(target) == 2) {
                        plugin.loweredReach.remove(target);
                        plugin.loweredReach.put(target, 1);
                    } else if (plugin.loweredReach.get(target) == 1) {
                        plugin.loweredReach.remove(target);
                    }

                }
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void ufo(Player p, Player target) {
        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            UFO_Utils.buildUFO(target);
            UFO_Utils.getPlayerToUFO(target);

            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Lower-Reach-cost"));
            if (response.transactionSuccess()) {
                UFO_Utils.buildUFO(target);
                UFO_Utils.getPlayerToUFO(target);

                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void zombie(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.getWorld().spawnEntity(location, EntityType.ZOMBIE);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Zombie.cost"));
            if (response.transactionSuccess()) {
                target.getWorld().spawnEntity(location, EntityType.ZOMBIE);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void skeleton(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.getWorld().spawnEntity(location, EntityType.SKELETON);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Skeleton.cost"));
            if (response.transactionSuccess()) {
                target.getWorld().spawnEntity(location, EntityType.SKELETON);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void spider(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.getWorld().spawnEntity(location, EntityType.SPIDER);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Spider.cost"));
            if (response.transactionSuccess()) {
                target.getWorld().spawnEntity(location, EntityType.SPIDER);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void chickenJokey(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            Chicken chicken = target.getWorld().spawn(location, Chicken.class);

            ZombieVillager zombieVillager = (ZombieVillager) location.getWorld().spawnEntity(location, EntityType.ZOMBIE_VILLAGER);
            zombieVillager.setBaby(true);
            EntityEquipment equipment = zombieVillager.getEquipment();
            equipment.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            equipment.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            equipment.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            equipment.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            equipment.setItemInMainHand(new ItemStack(Material.IRON_SWORD));
            equipment.getItemInMainHand().addEnchantment(Enchantment.DAMAGE_ALL, 1);

            chicken.addPassenger(zombieVillager);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Chicken-Jokey.cost"));
            if (response.transactionSuccess()) {
                Chicken chicken = target.getWorld().spawn(location, Chicken.class);

                ZombieVillager zombieVillager = (ZombieVillager) location.getWorld().spawnEntity(location, EntityType.ZOMBIE_VILLAGER);
                zombieVillager.setBaby(true);
                EntityEquipment equipment = zombieVillager.getEquipment();
                equipment.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                equipment.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                equipment.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                equipment.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                equipment.setItemInMainHand(new ItemStack(Material.IRON_SWORD));
                equipment.getItemInMainHand().addEnchantment(Enchantment.DAMAGE_ALL, 1);

                chicken.addPassenger(zombieVillager);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void ravager(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.getWorld().spawnEntity(location, EntityType.RAVAGER);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Ravager.cost"));
            if (response.transactionSuccess()) {
                target.getWorld().spawnEntity(location, EntityType.RAVAGER);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void enderman(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.getWorld().spawnEntity(location, EntityType.ENDERMAN);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Enderman.cost"));
            if (response.transactionSuccess()) {
                target.getWorld().spawnEntity(location, EntityType.ENDERMAN);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void warden(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            target.getWorld().spawnEntity(location, EntityType.WARDEN);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Warden.cost"));
            if (response.transactionSuccess()) {
                target.getWorld().spawnEntity(location, EntityType.WARDEN);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void ironGolem(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            IronGolem ironGolem = target.getWorld().spawn(location, IronGolem.class);
            String configPath = "Mob-options.Iron-Golem.is-angry";
            if (plugin.getConfig().getBoolean(configPath)) {
                ironGolem.setTarget(target);
            }
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Iron-Golem.cost"));
            if (response.transactionSuccess()) {
                IronGolem ironGolem = target.getWorld().spawn(location, IronGolem.class);
                String configPath = "Mob-options.Iron-Golem.is-angry";
                if (plugin.getConfig().getBoolean(configPath)) {
                    ironGolem.setTarget(target);
                }
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void creeper(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            Creeper creeper = (Creeper) target.getWorld().spawnEntity(location, EntityType.CREEPER);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Creeper.cost"));
            if (response.transactionSuccess()) {
                Creeper creeper = (Creeper) target.getWorld().spawnEntity(location, EntityType.CREEPER);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void chargedCreeper(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            Creeper chargedCreeper = target.getWorld().spawn(location, Creeper.class);
            chargedCreeper.setPowered(true);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Charged-Creeper.cost"));
            if (response.transactionSuccess()) {
                Creeper chargedCreeper = target.getWorld().spawn(location, Creeper.class);
                chargedCreeper.setPowered(true);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void snowMan(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            Snowman snowman = (Snowman) target.getWorld().spawnEntity(location, EntityType.SNOWMAN);
            String configPath = "Mob-options.Snowman.is-angry";
            if (configPath != null) {
                if (plugin.getConfig().getBoolean(configPath)) {
                    snowman.setTarget(target);
                    PersistentDataContainer snowManData = snowman.getPersistentDataContainer();
                    snowManData.set(new NamespacedKey(plugin, "isFromTroll"), PersistentDataType.INTEGER, 1);
                }
            } else {
                snowman.setTarget(target);
            }

            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Snowman.cost"));
            if (response.transactionSuccess()) {
                Snowman snowman = (Snowman) target.getWorld().spawnEntity(location, EntityType.SNOWMAN);
                String configPath = "Mob-options.Snowman.is-angry";
                if (configPath != null) {
                    if (plugin.getConfig().getBoolean(configPath)) {
                        snowman.setTarget(target);
                        PersistentDataContainer snowManData = snowman.getPersistentDataContainer();
                        snowManData.set(new NamespacedKey(plugin, "isFromTroll"), PersistentDataType.INTEGER, 1);
                    }
                } else {
                    snowman.setTarget(target);
                }

                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void blaze(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            Blaze blaze = (Blaze) target.getWorld().spawnEntity(location, EntityType.BLAZE);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Blaze.cost"));
            if (response.transactionSuccess()) {
                Blaze blaze = (Blaze) target.getWorld().spawnEntity(location, EntityType.BLAZE);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void axolotl(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            Axolotl axolotl = (Axolotl) target.getWorld().spawnEntity(location, EntityType.AXOLOTL);
            if (plugin.getConfig().getString("mob-options.Axolotl.Variant") != null) {
                axolotl.setVariant(Axolotl.Variant.valueOf(plugin.getConfig().getString("mob-options.Axolotl.Variant")));
            } else {
                axolotl.setVariant(Axolotl.Variant.BLUE);
            }
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Axolotl.cost"));
            if (response.transactionSuccess()) {
                Axolotl axolotl = (Axolotl) target.getWorld().spawnEntity(location, EntityType.AXOLOTL);
                if (plugin.getConfig().getString("mob-options.Axolotl.Variant") != null) {
                    axolotl.setVariant(Axolotl.Variant.valueOf(plugin.getConfig().getString("Mob-options.Axolotl.Variant")));
                } else {
                    axolotl.setVariant(Axolotl.Variant.BLUE);
                }
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void allay(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            Allay allay = (Allay) target.getWorld().spawnEntity(location, EntityType.ALLAY);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Allay.cost"));
            if (response.transactionSuccess()) {
                Allay allay = (Allay) target.getWorld().spawnEntity(location, EntityType.ALLAY);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void sniffer(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            Sniffer sniffer = (Sniffer) target.getWorld().spawnEntity(location, EntityType.SNIFFER);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Sniffer.cost"));
            if (response.transactionSuccess()) {
                Sniffer sniffer = (Sniffer) target.getWorld().spawnEntity(location, EntityType.SNIFFER);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

    public static void camel(Player p, Player target, Location location) {

        if (!isEconomyEnabled() || p.hasPermission("diatroll.admin")) {
            Camel camel = (Camel) target.getWorld().spawnEntity(location, EntityType.CAMEL);
            p.sendMessage(ChatColor.YELLOW + target.getDisplayName() + ChatColor.GREEN + " trolled successfully.");
        } else {
            Economy economy = TrollPlugin.getEconomy();
            EconomyResponse response = economy.withdrawPlayer(p, plugin.getConfig().getDouble("Mob-options.Camel.cost"));
            if (response.transactionSuccess()) {
                Camel camel = (Camel) target.getWorld().spawnEntity(location, EntityType.CAMEL);
                p.sendMessage(ChatColor.RED + "-" + economy.format((response.amount)));
            } else {
                p.sendMessage(ChatColor.RED + "You Don't have enough money");
            }
        }
    }

}