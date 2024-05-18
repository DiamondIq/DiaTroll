package me.diamond.trollplugin.listeners;

import me.diamond.trollplugin.TrollPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;

public class CageTroll implements Listener {
    @EventHandler
    public void onEntityChangeBlockEvent(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock) {
            FallingBlock fallingBlock = (FallingBlock) event.getEntity();
            PersistentDataContainer dataContainer = fallingBlock.getPersistentDataContainer();
            if (dataContainer.has(new NamespacedKey(TrollPlugin.getInstance(), "istrapblock"), PersistentDataType.INTEGER)) {
                Location landingLocation = event.getBlock().getLocation();
                    Long delay = TrollPlugin.getInstance().getConfig().getLong("cage-disappear-time");
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
    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(TrollPlugin.getInstance(), "istrappeddiatroll");
        if (data.has(namespacedKey, PersistentDataType.INTEGER)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerCageDestroy(BlockBreakEvent e){
        Player p = e.getPlayer();
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(TrollPlugin.getInstance(), "istrappeddiatroll");
        if (data.has(namespacedKey, PersistentDataType.INTEGER)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerEscape(PlayerMoveEvent e){
        Player p = e.getPlayer();
        PersistentDataContainer data = p.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(TrollPlugin.getInstance(), "cantwalkcagebecause");
        if (data.has(namespacedKey, PersistentDataType.INTEGER)){
            e.setCancelled(true);
        }
    }
}
