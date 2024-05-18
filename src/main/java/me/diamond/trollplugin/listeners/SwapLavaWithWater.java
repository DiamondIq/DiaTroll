package me.diamond.trollplugin.listeners;

import me.diamond.trollplugin.TrollPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class SwapLavaWithWater implements Listener {
    private Map<Player, BukkitTask> playerTasks;

    public SwapLavaWithWater() {
        playerTasks = new HashMap<>();
    }

    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent e) {
        Player target = e.getPlayer();
        Block block = target.getLocation().getBlock();

        if (block.getType() == Material.WATER) {
            if (TrollPlugin.getInstance().lavaSwapped.contains(target)) {
                // Start the task if the player is on water and lavaSwapped contains the player
                if (!playerTasks.containsKey(target)) {
                    BukkitTask task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!target.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                                target.damage(4);
                                target.playSound(target, Sound.ENTITY_PLAYER_HURT_ON_FIRE, 500.0f, 1.0f);
                                target.setFireTicks(1000);
                            }
                        }
                    }.runTaskTimer(TrollPlugin.getInstance(), 0L, 10L);

                    // Store the task associated with the player
                    playerTasks.put(target, task);
                }
            }
        } else {
            // Check if the player has a task and cancel it when the player is not on water
            BukkitTask task = playerTasks.remove(target);
            if (task != null) {
                task.cancel();
            }
        }

        if (block.getType() == Material.LAVA) {
            if (TrollPlugin.getInstance().lavaSwapped.contains(target)) {
                target.setFireTicks(0);
                target.setNoDamageTicks(20);
            }
        }
    }
}
