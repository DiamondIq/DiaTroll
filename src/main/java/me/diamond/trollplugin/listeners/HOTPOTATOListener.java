package me.diamond.trollplugin.listeners;

import me.diamond.trollplugin.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HOTPOTATOListener implements Listener {

    private final JavaPlugin plugin;

    public HOTPOTATOListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPotatoDrop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if (e.getItemDrop().getItemStack().equals(ItemUtils.hotPotato())){
            e.setCancelled(true);
            p.sendMessage(ChatColor.RED + "" +  ChatColor.BOLD + "Right Click someone to pass them the HOT POTATO");
        }
    }

    @EventHandler
    public void onPlayerRightCLick(PlayerInteractEntityEvent e){
        Entity entity =  e.getRightClicked();
        Player p = e.getPlayer();
        if (p.getInventory().contains(ItemUtils.hotPotato())){
            if (entity instanceof Player target){
                target.getInventory().addItem(ItemUtils.hotPotato());
                target.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You just got a HOT POTATO from " + p.getName());
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
                },plugin.getConfig().getLong("hotpotato-burn-time") * 20);
            }
        }
    }
}
