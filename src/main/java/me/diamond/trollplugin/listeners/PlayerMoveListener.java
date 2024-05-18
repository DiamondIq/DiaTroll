package me.diamond.trollplugin.listeners;

import me.diamond.trollplugin.TrollPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataType;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if (e.getPlayer().getPersistentDataContainer().has(new NamespacedKey(TrollPlugin.getInstance(), "cantMove"), PersistentDataType.INTEGER)){
            e.setCancelled(true);
        }
    }
}
