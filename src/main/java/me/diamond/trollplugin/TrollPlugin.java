package me.diamond.trollplugin;

import me.diamond.trollplugin.commands.MainCommand;
import me.diamond.trollplugin.commands.Tab.MainTabExecutor;
import me.diamond.trollplugin.commands.Tab.TrollsTabComplete;
import me.diamond.trollplugin.commands.TrollCommand;
import me.diamond.trollplugin.commands.VanishCommand;
import me.diamond.trollplugin.listeners.*;
import me.diamond.trollplugin.troll_utils.ufo.UFO;
import me.diamond.trollplugin.utils.GuiUtils;
import me.diamond.trollplugin.troll_utils.TrollUtils;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public final class TrollPlugin extends JavaPlugin {

    private static TrollPlugin instance;

    public static TrollPlugin getInstance() {
        return instance;
    }
    private boolean vaultInstalled;
    private boolean luckPermsInstalled;
    public ArrayList<Player> playersVansihed = new ArrayList<>();

    public ArrayList<Player> lavaSwapped = new ArrayList<>();

    public HashMap<Player, Integer> loweredReach = new HashMap<Player, Integer>();
    public static HashMap<Player, UFO> UFOs = new HashMap<>();

    private static Economy econ = null;

    @Override
    public void onEnable() {


        TrollUtils.setPlugin(this);


        TrollUtils trollUtils = new TrollUtils();

        GuiUtils guiUtils = new GuiUtils(this);

        instance = this;

        //Welcome Message
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "-----------------------");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[DiaTroll] Enabled");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Current version: 1.0v");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Author: @Diamond_Iq");
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "-----------------------");

        //Commands
        getCommand("troll").setExecutor(new TrollCommand(this));
        getCommand("troll").setTabCompleter(new TrollsTabComplete());
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("diatroll").setExecutor(new MainCommand(this));
        getCommand("diatroll").setTabCompleter(new MainTabExecutor());


        //Events
        getServer().getPluginManager().registerEvents(new TrollplayerListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new ExplosiveAppleListener(this), this);
        getServer().getPluginManager().registerEvents(new PepeMoveListener(), this);
        getServer().getPluginManager().registerEvents(new CantBreakBlocksListener(), this);
        getServer().getPluginManager().registerEvents(new CageTroll(), this);
        getServer().getPluginManager().registerEvents(new SwapLavaWithWater(), this);
        getServer().getPluginManager().registerEvents(new LowerReach(), this);
        getServer().getPluginManager().registerEvents(new HOTPOTATOListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);


        //Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //Vault APi
        if (getServer().getPluginManager().isPluginEnabled("Vault")) {
            vaultInstalled = true;
        } else {
            vaultInstalled = false;
        }

        if (!setupEconomy()) {

        }

        if (vaultInstalled){
            getLogger().info("Vault Detected, there will be additional features.");
        }

        //LuckPerms APi
        if (getServer().getPluginManager().isPluginEnabled("LuckPerms")) {
            luckPermsInstalled = true;
        } else {
            luckPermsInstalled = false;
        }
        if (luckPermsInstalled) {
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                LuckPerms luckPerms = provider.getProvider();
                getLogger().info("LuckPerms Detected, there will be additional features.");
            } else {
                getLogger().severe("[DiaTroll] Failed to retrieve LuckPerms provider.");
            }
        } else {
        }
        if (!vaultInstalled && getConfig().getBoolean("Economy-Enabled")){
            File configFile = new File(getDataFolder(), "config.yml");
            FileConfiguration config1 = YamlConfiguration.loadConfiguration(configFile);
            config1.set("Economy-Enabled", false);
            getLogger().info("Can't enable Economy since Vault isn't installed");
            try {
                config1.save(configFile);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }

    }

    //Vault Economy
    private boolean setupEconomy() {
        if (!vaultInstalled) {
            return false;
        } else {
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;

    }

    public static Economy getEconomy() {
        return econ;
    }


    //Luckperms installed?
    public boolean isLuckPermsInstalled() {
        return luckPermsInstalled;
    }


    //Vault Installed?
    public boolean isVaultInstalled() {
        return vaultInstalled;
    }
}
