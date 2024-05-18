package me.diamond.trollplugin.listeners;

import me.diamond.trollplugin.TrollPlugin;
import me.diamond.trollplugin.utils.ColorUtils;
import me.diamond.trollplugin.utils.ServerVersionUtils;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;


public class CantBreakBlocksListener implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Player target = e.getPlayer();
        PersistentDataContainer data = target.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(TrollPlugin.getInstance(), "cantbreakblocks");
        if (data.has(key, PersistentDataType.INTEGER)) {
            e.setCancelled(true);
            if (TrollPlugin.getInstance().getConfig().getBoolean("Cancel-Block-Breaks-CustomMessage.enabled")){
                if (TrollPlugin.getInstance().getConfig().getString("Cancel-Block-Breaks-CustomMessage.message") != null){
                    if (ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.15")) {
                        target.sendMessage(ColorUtils.translateColorCodes(TrollPlugin.getInstance().getConfig().getString("Cancel-Block-Breaks-CustomMessage.message")));
                    } else {
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', TrollPlugin.getInstance().getConfig().getString("Cancel-Block-Breaks-CustomMessage.message")));
                    }
                } else {
                    File configFile = new File(TrollPlugin.getInstance().getDataFolder(), "config.yml");
                    FileConfiguration config1 = YamlConfiguration.loadConfiguration(configFile);
                    config1.set("Cancel-Block-Breaks-CustomMessage.enabled", false);
                    try {
                        config1.save(configFile);
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            }
        }

    }
}
