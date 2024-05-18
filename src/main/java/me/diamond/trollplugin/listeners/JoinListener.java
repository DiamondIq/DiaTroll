package me.diamond.trollplugin.listeners;

import me.diamond.trollplugin.TrollPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    TrollPlugin plugin;

    public JoinListener(TrollPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        Player player = e.getPlayer();
        for (int i = 0; i < plugin.playersVansihed.size(); i++)
            player.hidePlayer(plugin, plugin.playersVansihed.get(i));
    }



}
