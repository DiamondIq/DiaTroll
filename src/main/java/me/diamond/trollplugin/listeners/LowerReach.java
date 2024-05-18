package me.diamond.trollplugin.listeners;

import me.diamond.trollplugin.TrollPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LowerReach implements Listener {


    @EventHandler
    public void onEntityDamageEvent(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            Player target = ((Player) e.getDamager()).getPlayer();
            if (TrollPlugin.getInstance().loweredReach.containsKey(target)){
                if (TrollPlugin.getInstance().loweredReach.get(target) == 2){
                    if (target.getLocation().distance(e.getEntity().getLocation()) > 2.5){
                        e.setCancelled(true);
                    }
                } else if (TrollPlugin.getInstance().loweredReach.get(target) == 1){
                    if (target.getLocation().distance(e.getEntity().getLocation()) > 1.5){
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
