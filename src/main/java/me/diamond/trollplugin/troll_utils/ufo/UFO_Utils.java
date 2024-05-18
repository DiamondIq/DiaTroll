package me.diamond.trollplugin.troll_utils.ufo;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.netty.buffer.Unpooled;
import me.diamond.trollplugin.TrollPlugin;
import net.milkbowl.vault.chat.Chat;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.level.GameType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scheduler.BukkitRunnable;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.util.*;

public class UFO_Utils {
    public static void npc(Player p) {
        CraftPlayer craftPlayer = (CraftPlayer) p;
        ServerPlayer serverPlayer = craftPlayer.getHandle();

        if (serverPlayer == null || serverPlayer.connection == null) {
            p.sendMessage(ChatColor.RED + "Unable to create NPC: Player connection is not available.");
            return;
        }

        MinecraftServer server = serverPlayer.server;
        ServerLevel level = serverPlayer.serverLevel();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "Alien");
        gameProfile.getProperties().put("textures", new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTU5NjQwNjUyNDgyNiwKICAicHJvZmlsZUlkIiA6ICJmMjc0YzRkNjI1MDQ0ZTQxOGVmYmYwNmM3NWIyMDIxMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJIeXBpZ3NlbCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81NDRjNTk0ZWI2MTBkMDAzNjQxODQ0OTBkZTVkNjg4N2Q0ZmVmZTc2ODAwYzEyNjA3MGIwN2EyMTBmNDEyNTY2IgogICAgfQogIH0KfQ==", "LK5HbVn5lMXgEFxhvVN60g3Smpa6tC60ji+66mOaY/T3/WPPB/0fPE8Ea0m3OVKBd8U8/pnfmqaNGgfXw3EpJXX0nmQkoA0GroQ/1l3fzB+OMuEjahtaiEw0hwjq/GWN4fXog9QZeb79bzKPLIxz6bN3M+DJVxien86ZAJd8fr6+BqJCy01WAXElbCQrBA7oLxjhMIhhoevlB7NmQ/ikadYXQ4eqtpLtY/En7nr0LMPdEzz/ixxD76mGN0KJORY7ab5+nRkABNhDLf31uOZ/woE0mXkeIq5jJ8HoAAG1gEbzXoSHNRfreUfgY5fYsWGXBbD4vd6oZHAHES9MZlocnMtcaWEBpero2sHMI3LiaXRyvFBz3/QoMBsXA4CcvEEhNKHW8RJTKKi2E7KKMENhOUF7D9e6piHWJQKCNgahVIbbVMzf/dlWCrGOAbIlCqvV7ZoeoMyBheCGDlM5THl5HUMMfu+T8JhSFCxNsgzFG8hLnwQW8iusD49MuQtLTFnjfOK2LxJ25agawXaRnPwonCasjtZfHcGU9C6u4edRHDTEhrPHAZKkvMy1GeagpPd9NFGIL4Q2ypO/OIMSGKaBTqitrTPbhPXe80As/GYekXC4tUkufnh/QqbhSFZFskXQDYlRLGZujMFC58LDkwoqbrqhvgtJyY5Z9aEjJ0plZRs="));

        ServerPlayer npc = new ServerPlayer(server, level, gameProfile, serverPlayer.clientInformation());

        npc.setPos(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().add(0, 0, -1).getZ());
        TrollPlugin.UFOs.get(p).setNpc(npc);
//        Entry(ServerPlayer p, boolean listed) {
//            this(player.getUUID(), p.getGameProfile(), listed, p.connection.latency(), p.gameMode.getGameModeForPlayer(), p.getTabListDisplayName(), Optionull.map(p.getChatSession(), RemoteChatSession::asData));
//            // Paper end - add listed
//        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            CraftPlayer craftPlayer2 = (CraftPlayer) player;
            ServerPlayer sp2 = craftPlayer2.getHandle();

            if (sp2 == null || sp2.connection == null) {
                p.sendMessage(ChatColor.RED + "Unable to create NPC: Player connection is not available.");
                return;
            }
            ClientboundPlayerInfoUpdatePacket packet = new ClientboundPlayerInfoUpdatePacket(null);
            ClientboundAddEntityPacket packet1 = new ClientboundAddEntityPacket(npc);

            npc.setPos(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().add(0, 0, -1).getZ());

        }



    }

    public static void getPlayerToUFO(final Player p) {
        Location l = p.getLocation().add(0, 80, 0);
        World world = p.getWorld();
        Block block = world.getBlockAt(p.getLocation());
        p.getPersistentDataContainer().set(new NamespacedKey(TrollPlugin.getInstance(), "cantMove"), PersistentDataType.INTEGER, 1);
        p.teleport(block.getLocation().add(0.5, 0, 0.5).setDirection(p.getLocation().getDirection()));
        new BukkitRunnable() {
            @Override
            public void run() {
                UFO ufo = TrollPlugin.UFOs.get(p);
                Location trapdoorLocation = ufo.getTrapdoor().getLocation();
                if (trapdoorLocation.getY() < p.getLocation().getY()) {
                    cancel();
                    p.getPersistentDataContainer().remove(new NamespacedKey(TrollPlugin.getInstance(), "cantMove"));
                    npc(p);
                    return;
                }
                p.teleport(p.getLocation().add(0, 1, 0).setDirection(p.getLocation().getDirection()));
                p.playSound(p, Sound.BLOCK_DISPENSER_FAIL, 0.5f, 0.7f);
                Particle.DustOptions particle = new Particle.DustOptions(Color.LIME, 2.0f);
                p.spawnParticle(Particle.REDSTONE, p.getEyeLocation(), 50, particle);
            }
        }.runTaskTimer(TrollPlugin.getInstance(), 0L, 3L);


    }

    public static void buildUFO(Player target) {
        World world = target.getWorld(); // Get the first loaded world, you might need to adjust this

        Location l = target.getLocation().add(0, 80, 0);

        UFO ufo = new UFO(target, null, null, null, null, null);

        // Place the trapdoor 100 blocks above the player
        Location trapdoorLocation = l.clone().add(0, 0, 0);

// Place the trapdoor at the top half of the block
        Block highestBlock = trapdoorLocation.getBlock();
        highestBlock.setType(Material.IRON_TRAPDOOR);
        BlockData trapDoorData = highestBlock.getBlockData();
        ufo.setTrapdoor(highestBlock);
        if (trapDoorData instanceof TrapDoor) {
            TrapDoor trapDoor = (TrapDoor) trapDoorData;
            trapDoor.setFacing(BlockFace.NORTH);
            trapDoor.setHalf(Bisected.Half.TOP); // Set the trapdoor to the top half of the block
            highestBlock.setBlockData(trapDoor);
        }

        // Define locations for smooth stone blocks
        Location[] smoothStoneBlockLocations = {
                l.clone().add(0, 0, 1), l.clone().add(0, 0, 2), l.clone().add(0, 0, 3), l.clone().add(0, 0, 4), l.clone().add(0, 0, 5), l.clone().add(0, 0, 6),
                l.clone().add(0, 0, -1), l.clone().add(0, 0, -2), l.clone().add(0, 0, -3), l.clone().add(0, 0, -4), l.clone().add(0, 0, -5), l.clone().add(0, 0, -6),
                l.clone().add(-1, 0, 0), l.clone().add(-2, 0, 0), l.clone().add(-3, 0, 0), l.clone().add(-4, 0, 0), l.clone().add(-5, 0, 0), l.clone().add(-6, 0, 0),
                l.clone().add(1, 0, 0), l.clone().add(2, 0, 0), l.clone().add(3, 0, 0), l.clone().add(4, 0, 0), l.clone().add(5, 0, 0), l.clone().add(6, 0, 0),
                l.clone().add(1, 0, 6), l.clone().add(1, 0, 5), l.clone().add(1, 0, 4), l.clone().add(1, 0, 3), l.clone().add(1, 0, 2), l.clone().add(1, 0, 1),
                l.clone().add(1, 0, -6), l.clone().add(1, 0, -5), l.clone().add(1, 0, -4), l.clone().add(1, 0, -3), l.clone().add(1, 0, -2), l.clone().add(1, 0, -1),
                l.clone().add(-1, 0, 1), l.clone().add(-1, 0, 2), l.clone().add(-1, 0, 3), l.clone().add(-1, 0, 4), l.clone().add(-1, 0, 5), l.clone().add(-1, 0, 6),
                l.clone().add(-1, 0, -1), l.clone().add(-1, 0, -2), l.clone().add(-1, 0, -3), l.clone().add(-1, 0, -4), l.clone().add(-1, 0, -5), l.clone().add(-1, 0, -6),
                l.clone().add(-2, 0, 1), l.clone().add(-2, 0, 2), l.clone().add(-2, 0, 3), l.clone().add(-2, 0, 4), l.clone().add(-2, 0, 5), l.clone().add(-2, 0, 6),
                l.clone().add(-2, 0, -1), l.clone().add(-2, 0, -2), l.clone().add(-2, 0, -3), l.clone().add(-2, 0, -4), l.clone().add(-2, 0, -5), l.clone().add(-2, 0, -6),
                l.clone().add(-3, 0, 1), l.clone().add(-3, 0, 2), l.clone().add(-3, 0, 3), l.clone().add(-3, 0, 4), l.clone().add(-3, 0, 5),
                l.clone().add(-3, 0, -1), l.clone().add(-3, 0, -2), l.clone().add(-3, 0, -3), l.clone().add(-3, 0, -4), l.clone().add(-3, 0, -5),
                l.clone().add(-4, 0, 1), l.clone().add(-4, 0, 2), l.clone().add(-4, 0, 3), l.clone().add(-4, 0, 4),
                l.clone().add(-4, 0, -1), l.clone().add(-4, 0, -2), l.clone().add(-4, 0, -3), l.clone().add(-4, 0, -4),
                l.clone().add(-5, 0, 1), l.clone().add(-5, 0, 2), l.clone().add(-5, 0, 3),
                l.clone().add(-5, 0, -1), l.clone().add(-5, 0, -2), l.clone().add(-5, 0, -3),
                l.clone().add(-6, 0, 1), l.clone().add(-6, 0, 2),
                l.clone().add(-6, 0, -1), l.clone().add(-6, 0, -2),
                l.clone().add(2, 0, 1), l.clone().add(2, 0, 2), l.clone().add(2, 0, 3), l.clone().add(2, 0, 4), l.clone().add(2, 0, 5), l.clone().add(2, 0, 6),
                l.clone().add(2, 0, -1), l.clone().add(2, 0, -2), l.clone().add(2, 0, -3), l.clone().add(2, 0, -4), l.clone().add(2, 0, -5), l.clone().add(2, 0, -6),
                l.clone().add(3, 0, 1), l.clone().add(3, 0, 2), l.clone().add(3, 0, 3), l.clone().add(3, 0, 4), l.clone().add(3, 0, 5),
                l.clone().add(3, 0, -1), l.clone().add(3, 0, -2), l.clone().add(3, 0, -3), l.clone().add(3, 0, -4), l.clone().add(3, 0, -5),
                l.clone().add(4, 0, 1), l.clone().add(4, 0, 2), l.clone().add(4, 0, 3), l.clone().add(4, 0, 4),
                l.clone().add(4, 0, -1), l.clone().add(4, 0, -2), l.clone().add(4, 0, -3), l.clone().add(4, 0, -4),
                l.clone().add(5, 0, 1), l.clone().add(5, 0, 2), l.clone().add(5, 0, 3),
                l.clone().add(5, 0, -1), l.clone().add(5, 0, -2), l.clone().add(5, 0, -3),
                l.clone().add(6, 0, 1), l.clone().add(6, 0, 2),
                l.clone().add(6, 0, -1), l.clone().add(6, 0, -2),
                l.clone().add(1, -1, 0), l.clone().add(2, -1, 0), l.clone().add(3, -1, 0), l.clone().add(4, -1, 0),
                l.clone().add(-1, -1, 0), l.clone().add(-2, -1, 0), l.clone().add(-3, -1, 0), l.clone().add(-4, -1, 0),
                l.clone().add(0, -1, 1), l.clone().add(0, -1, 2), l.clone().add(0, -1, 3), l.clone().add(0, -1, 4),
                l.clone().add(0, -1, -1), l.clone().add(0, -1, -2), l.clone().add(0, -1, -3), l.clone().add(0, -1, -4),
                l.clone().add(-1, -1, 1), l.clone().add(-1, -1, 2), l.clone().add(-1, -1, 3), l.clone().add(-1, -1, 4),
                l.clone().add(-1, -1, -1), l.clone().add(-1, -1, -2), l.clone().add(-1, -1, -3), l.clone().add(-1, -1, -4),
                l.clone().add(1, -1, 1), l.clone().add(1, -1, 2), l.clone().add(1, -1, 3), l.clone().add(1, -1, 4),
                l.clone().add(1, -1, -1), l.clone().add(1, -1, -2), l.clone().add(1, -1, -3), l.clone().add(1, -1, -4),
                l.clone().add(2, -1, 1), l.clone().add(2, -1, 2), l.clone().add(2, -1, 3), l.clone().add(2, -1, 4),
                l.clone().add(2, -1, -1), l.clone().add(2, -1, -2), l.clone().add(2, -1, -3), l.clone().add(2, -1, -4),
                l.clone().add(-2, -1, 1), l.clone().add(-2, -1, 2), l.clone().add(-2, -1, 3), l.clone().add(-2, -1, 4),
                l.clone().add(-2, -1, -1), l.clone().add(-2, -1, -2), l.clone().add(-2, -1, -3), l.clone().add(-2, -1, -4),
                l.clone().add(-2, -1, 1), l.clone().add(-2, -1, 2), l.clone().add(-2, -1, 3),
                l.clone().add(-2, -1, -1), l.clone().add(-2, -1, -2), l.clone().add(-2, -1, -3),
                l.clone().add(-3, -1, 1), l.clone().add(-3, -1, 2), l.clone().add(-3, -1, 3),
                l.clone().add(-3, -1, -1), l.clone().add(-3, -1, -2), l.clone().add(-3, -1, -3),
                l.clone().add(-4, -1, 1), l.clone().add(-4, -1, 2),
                l.clone().add(-4, -1, -1), l.clone().add(-4, -1, -2),
                l.clone().add(3, -1, 1), l.clone().add(3, -1, 2), l.clone().add(3, -1, 3),
                l.clone().add(3, -1, -1), l.clone().add(3, -1, -2), l.clone().add(3, -1, -3),
                l.clone().add(4, -1, 1), l.clone().add(4, -1, 2),
                l.clone().add(4, -1, -1), l.clone().add(4, -1, -2),
                l.clone().add(1, -2, 0), l.clone().add(2, -2, 0),
                l.clone().add(-1, -2, 0), l.clone().add(-2, -2, 0),
                l.clone().add(0, -2, 1), l.clone().add(0, -2, 2),
                l.clone().add(0, -2, -1), l.clone().add(0, -2, -2),
                l.clone().add(1, -2, 1), l.clone().add(1, -2, 2),
                l.clone().add(1, -2, -1), l.clone().add(1, -2, -2),
                l.clone().add(-1, -2, 1), l.clone().add(-1, -2, 2),
                l.clone().add(-1, -2, -1), l.clone().add(-1, -2, -2),
                l.clone().add(-2, -2, 1),
                l.clone().add(-2, -2, -1),
                l.clone().add(2, -2, 1),
                l.clone().add(2, -2, -1),
                l.clone().add(-1, 1, 5), l.clone().add(-1, 1, 4), l.clone().add(-1, 1, -5), l.clone().add(-1, 1, -4), //Layer 1 Walls
                l.clone().add(-2, 1, 5), l.clone().add(-2, 1, 4), l.clone().add(-2, 1, -5), l.clone().add(-2, 1, -4),
                l.clone().add(1, 1, 5), l.clone().add(1, 1, 4), l.clone().add(1, 1, -5), l.clone().add(1, 1, -4),
                l.clone().add(2, 1, 5), l.clone().add(2, 1, 4), l.clone().add(2, 1, -5), l.clone().add(2, 1, -4),
                l.clone().add(0, 1, 5), l.clone().add(0, 1, 4), l.clone().add(0, 1, -5), l.clone().add(0, 1, -4),
                l.clone().add(0, 1, 5), l.clone().add(0, 1, 4), l.clone().add(0, 1, -5), l.clone().add(0, 1, -4),
                l.clone().add(3, 1, 3), l.clone().add(3, 1, 4), l.clone().add(3, 1, -3), l.clone().add(3, 1, -4),
                l.clone().add(-3, 1, 3), l.clone().add(-3, 1, 4), l.clone().add(-3, 1, -3), l.clone().add(-3, 1, -4),
                l.clone().add(4, 1, 3), l.clone().add(4, 1, 2), l.clone().add(4, 1, 1), l.clone().add(4, 1, 0), l.clone().add(4, 1, -3), l.clone().add(4, 1, -2), l.clone().add(4, 1, -1),
                l.clone().add(-4, 1, 3), l.clone().add(-4, 1, 2), l.clone().add(-4, 1, 1), l.clone().add(-4, 1, 0), l.clone().add(-4, 1, -3), l.clone().add(-4, 1, -2), l.clone().add(-4, 1, -1),
                l.clone().add(5, 1, 2), l.clone().add(5, 1, 1), l.clone().add(5, 1, 0), l.clone().add(5, 1, -2), l.clone().add(5, 1, -1),
                l.clone().add(-5, 1, 2), l.clone().add(-5, 1, 1), l.clone().add(-5, 1, 0), l.clone().add(-5, 1, -2), l.clone().add(-5, 1, -1),

        };

        Location[] smoothStoneSlabLocations = {
                l.clone().add(-2, 1.5, -3), l.clone().add(-1, 1.5, -3), l.clone().add(0, 1.5, -3), l.clone().add(1, 1.5, -3), l.clone().add(2, 1.5, -3),
                l.clone().add(-3, 1.5, -2), l.clone().add(-2, 1.5, -2), l.clone().add(-1, 1.5, -2), l.clone().add(0, 1.5, -2), l.clone().add(1, 1.5, -2), l.clone().add(2, 1.5, -2), l.clone().add(3, 1.5, -2),
                l.clone().add(-1, 4.5, 1), l.clone().add(-1, 4.5, 0), l.clone().add(-1, 4.5, -1),
                l.clone().add(0, 4.5, 1), l.clone().add(0, 4.5, 0), l.clone().add(0, 4.5, -1),
                l.clone().add(1, 4.5, 1), l.clone().add(1, 4.5, 0), l.clone().add(1, 4.5, -1),
        };

        Location[] glassBlockLocations = {
                l.clone().add(0, 2, 4), l.clone().add(0, 3, 3), l.clone().add(0, 4, 2), l.clone().add(0, 2, -4), l.clone().add(0, 3, -3), l.clone().add(0, 4, -2),
                l.clone().add(1, 2, 4), l.clone().add(1, 3, 3), l.clone().add(1, 4, 2), l.clone().add(1, 2, -4), l.clone().add(1, 3, -3), l.clone().add(1, 4, -2),
                l.clone().add(-1, 2, 4), l.clone().add(-1, 3, 3), l.clone().add(-1, 4, 2), l.clone().add(-1, 2, -4), l.clone().add(-1, 3, -3), l.clone().add(-1, 4, -2),
                l.clone().add(2, 2, 4), l.clone().add(2, 3, 3), l.clone().add(2, 4, 2), l.clone().add(2, 4, 1), l.clone().add(2, 4, 0), l.clone().add(2, 4, -1), l.clone().add(2, 2, -4), l.clone().add(2, 3, -3), l.clone().add(2, 4, -2),
                l.clone().add(-2, 2, 4), l.clone().add(-2, 3, 3), l.clone().add(-2, 4, 2), l.clone().add(-2, 4, 1), l.clone().add(-2, 4, 0), l.clone().add(-2, 4, -1), l.clone().add(-2, 2, -4), l.clone().add(-2, 3, -3), l.clone().add(-2, 4, -2),
                l.clone().add(3, 2, 3), l.clone().add(3, 3, 2), l.clone().add(3, 3, 1), l.clone().add(3, 3, 0), l.clone().add(3, 3, -1), l.clone().add(3, 3, -2), l.clone().add(3, 2, -3),
                l.clone().add(-3, 2, 3), l.clone().add(-3, 3, 2), l.clone().add(-3, 3, 1), l.clone().add(-3, 3, 0), l.clone().add(-3, 3, -1), l.clone().add(-3, 3, -2), l.clone().add(-3, 2, -3),
                l.clone().add(-4, 2, 2), l.clone().add(-4, 2, 1), l.clone().add(-4, 2, 0), l.clone().add(-4, 2, -1), l.clone().add(-4, 2, -2),
                l.clone().add(4, 2, 2), l.clone().add(4, 2, 1), l.clone().add(4, 2, 0), l.clone().add(4, 2, -1), l.clone().add(4, 2, -2),
        };

        Location[] leverLocations = {
                l.clone().add(-3, 2, -2), l.clone().add(-2, 2, -2), l.clone().add(-1, 2, -2), l.clone().add(1, 2, -2), l.clone().add(2, 2, -2), l.clone().add(3, 2, -2),
        };


        ArrayList<Block> a = new ArrayList<>();
        for (Location loc : smoothStoneBlockLocations) {
            Block block = world.getBlockAt(loc);
            block.setType(Material.SMOOTH_STONE);
            a.add(block);
        }

        for (Location loc : smoothStoneSlabLocations) {
            Block block = world.getBlockAt(loc.clone().add(0, -0.5, 0));
            block.setType(Material.SMOOTH_STONE_SLAB);
            BlockData blockData = Material.SMOOTH_STONE_SLAB.createBlockData();
            // Cast to Slab
            Slab slab = (Slab) blockData;
            // Set the slab type and half
            slab.setType(Slab.Type.TOP);
            block.setBlockData(slab);
            a.add(block);
        }
        ufo.setSmooth_Stone_Blocks(a);

        ArrayList<Block> b = new ArrayList<>();
        for (Location loc : glassBlockLocations) {
            Block block = world.getBlockAt(loc);
            block.setType(Material.LIME_STAINED_GLASS);
            b.add(block);
        }
        ufo.setGlass_Blocks(b);

        ArrayList<Block> c = new ArrayList<>();
        for (Location loc : leverLocations) {
            Block block = world.getBlockAt(loc);
            block.setType(Material.LEVER);
            BlockData blockData = Material.LEVER.createBlockData("[face=floor]");
            if (blockData instanceof Directional) {
                Directional directional = (Directional) blockData;
//                    directional.setFacing(BlockFace.WEST);
                block.setBlockData(directional);
            }
            c.add(block);
        }
        ufo.setLevers(c);

        Block comparatorBlock = world.getBlockAt(l.clone().add(0, 2, -2));
        comparatorBlock.setType(Material.COMPARATOR);
//        BlockData comparatorData = Material.COMPARATOR.createBlockData("[face=floor]");
        BlockData comparatorData = Material.COMPARATOR.createBlockData();
        if (comparatorData instanceof Directional) {
            Directional directional = (Directional) comparatorData;
            directional.setFacing(BlockFace.EAST);
            comparatorBlock.setBlockData(directional);
        }
        ufo.setComparator(comparatorBlock);
        TrollPlugin.UFOs.put(target, ufo);
    }

    public static void autoDestructionModeOn(UFO ufo) {
        ServerPlayer npc = ufo.getNpc();
        Location loc = npc.getBukkitEntity().getLocation();
        for (Player player : Bukkit.getOnlinePlayers()) {
            CraftPlayer craftPlayer2 = (CraftPlayer) player;
            ServerPlayer sp2 = craftPlayer2.getHandle();

            ServerGamePacketListenerImpl connection = sp2.connection;

            ClientboundPlayerInfoUpdatePacket packet0 = new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, npc);

            connection.send(packet0);
        }
        Player p = ufo.getP();
        p.spawnParticle(Particle.ASH, loc, 20);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', TrollPlugin.getInstance().getConfig().getString("auto-destruction.warning-message1")));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', TrollPlugin.getInstance().getConfig().getString("auto-destruction.warning-message2")));
        Location tntLoc = ufo.getTrapdoor().getLocation().add(0, 1, 0);
        new BukkitRunnable() {
            @Override
            public void run() {
                int type = TrollPlugin.getInstance().getConfig().getInt("auto-destruction.warning-message1");
                //Type 1
                if (type == 1 || type >= 5) {
                    TNTPrimed tnt1 = (TNTPrimed) p.getWorld().spawnEntity(tntLoc, EntityType.PRIMED_TNT);
                    tnt1.setFuseTicks(20 * 10);
                    TNTPrimed tnt2 = (TNTPrimed) p.getWorld().spawnEntity(tntLoc, EntityType.PRIMED_TNT);
                    tnt2.setFuseTicks(20 * 10);
                    TNTPrimed tnt3 = (TNTPrimed) p.getWorld().spawnEntity(tntLoc, EntityType.PRIMED_TNT);
                    tnt3.setFuseTicks(20 * 10);
                    TNTPrimed tnt4 = (TNTPrimed) p.getWorld().spawnEntity(tntLoc, EntityType.PRIMED_TNT);
                    tnt4.setFuseTicks(20 * 10);
                    TNTPrimed tnt5 = (TNTPrimed) p.getWorld().spawnEntity(tntLoc, EntityType.PRIMED_TNT);
                    tnt5.setFuseTicks(20 * 10);

                    //Type 2
                } else if (type == 2) {
                    for (Block b : ufo.getBlocks()) {
                        b.setType(Material.AIR);
                    }

                    //Type 3
                } else if (type == 3) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (ufo.getBlocks().isEmpty()) {
                                cancel();
                                return;
                            }
                            for (Block block : ufo.getBlocks()) {
                                Random random = new Random();
                                int r = random.nextInt(ufo.getBlocks().size());
                                Block b = ufo.getBlocks().get(r);
                                b.setType(Material.AIR);
                                ufo.getBlocks().remove(b);
                            }
                        }
                    }.runTaskTimer(TrollPlugin.getInstance(), 0, 20);
                } else if (type == 4) {
                    //Todo - Create type 4 destruction - crash into ground
                }
            }


        }.runTaskLater(TrollPlugin.getInstance(), 20);
    }

}
