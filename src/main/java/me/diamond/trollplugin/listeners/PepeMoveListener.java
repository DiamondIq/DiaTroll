package me.diamond.trollplugin.listeners;

import me.diamond.trollplugin.TrollPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Location;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;

public class PepeMoveListener implements Listener {
    @EventHandler
    public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock) {
            FallingBlock fallingBlock = (FallingBlock) event.getEntity();
            PersistentDataContainer dataContainer = fallingBlock.getPersistentDataContainer();
            if (dataContainer.has(new NamespacedKey(TrollPlugin.getInstance(), "pepe"), PersistentDataType.INTEGER)) {
                Location landingLocation = event.getBlock().getLocation();
                if (TrollPlugin.getInstance().getConfig().getBoolean("pp-disappear.enabled")){
                    Long delay = TrollPlugin.getInstance().getConfig().getLong("pp-disappear.delay");
                    Long multipliedValue = delay * 20;
                    BukkitScheduler scheduler = Bukkit.getScheduler();
                    scheduler.runTaskLater(TrollPlugin.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            landingLocation.getBlock().setType(Material.AIR);
                        }
                    }, multipliedValue);
                }
            }
        }
    }
}