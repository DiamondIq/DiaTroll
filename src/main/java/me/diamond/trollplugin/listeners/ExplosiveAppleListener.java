package me.diamond.trollplugin.listeners;

import me.diamond.trollplugin.TrollPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ExplosiveAppleListener implements Listener {

    Plugin plugin;

    public ExplosiveAppleListener(Plugin plugin) {
        this.plugin = plugin;
    }

    private static final NamespacedKey EXPLOSIVE_APPLE_KEY = new NamespacedKey(JavaPlugin.getPlugin(TrollPlugin.class), "explosiveApple");

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        float floatValue = (float) plugin.getConfig().getDouble("apple-explosion-power", 0.0);
        if (item.getType() == Material.ENCHANTED_GOLDEN_APPLE && hasCustomData(item)) {
            player.getWorld().createExplosion(player.getLocation(), floatValue);
            event.setCancelled(true); // Prevents the player from receiving the golden apple's regular effects

            item.setAmount(item.getAmount() - 1);
        }
    }

    private boolean hasCustomData(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }

        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        return dataContainer.has(EXPLOSIVE_APPLE_KEY, PersistentDataType.BYTE);
    }
}
