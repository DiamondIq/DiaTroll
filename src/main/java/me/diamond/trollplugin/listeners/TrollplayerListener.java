package me.diamond.trollplugin.listeners;

import me.diamond.trollplugin.TrollPlugin;
import me.diamond.trollplugin.utils.GuiUtils;
import me.diamond.trollplugin.utils.ItemUtils;
import me.diamond.trollplugin.utils.ServerVersionUtils;
import me.diamond.trollplugin.troll_utils.TrollUtils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Base64;

import static me.diamond.trollplugin.utils.ItemUtils.getAlienHead;


public class TrollplayerListener implements Listener {

    private Player target;


    TrollPlugin plugin;


    public TrollplayerListener(TrollPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnMenuClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Material lavaSwapMaterial;
        if (!plugin.lavaSwapped.contains(target)) {
            lavaSwapMaterial = Material.LAVA_BUCKET;
        } else {
            lavaSwapMaterial = Material.WATER_BUCKET;
        }
        Material lightningMaterial;
        if (ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.17")) {
            lightningMaterial = Material.LIGHTNING_ROD;
        } else {
            lightningMaterial = Material.SPECTRAL_ARROW;
        }
        Material reachMaterial = null;
        if (!plugin.loweredReach.containsKey(target)) {
            reachMaterial = Material.GREEN_CONCRETE;
        } else {
            if (plugin.loweredReach.get(target) == 2) {
                reachMaterial = Material.YELLOW_CONCRETE;
            } else if (plugin.loweredReach.get(target) == 1) {
                reachMaterial = Material.RED_CONCRETE;
            }
        }
        if (e.getCurrentItem() == null) {
            return;
        }

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "" + ChatColor.BOLD + "Troll Gui")) {
            target = (Player) p.getServer().getPlayerExact(ChatColor.stripColor((e.getClickedInventory().getItem(4).getItemMeta().getDisplayName())));
            if (e.getCurrentItem() == null) {
                return;
            } else {
                if (e.getCurrentItem().getType() == Material.SLIME_BALL) {
                    if (target != null) {
                        TrollUtils.launch(p, target);
                    } else {
                        return;
                    }

                } else if (e.getCurrentItem().getType() == lightningMaterial) {
                    if (target != null) {
                    } else {
                        return;
                    }
                    TrollUtils.lightning(p, target);
                } else if (e.getCurrentItem().getType() == Material.BARRIER) {
                    if (target != null) {
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_RED + "No wifi")) {
                            TrollUtils.noInternet(p, target);
                        } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Close")) {
                            p.closeInventory();
                        }
                    } else {
                        return;
                    }


                } else if (e.getCurrentItem().getType() == Material.FLINT_AND_STEEL) {
                    if (target != null) {
                        TrollUtils.fire(p, target);
                    } else {
                        return;
                    }


                } else if (e.getCurrentItem().getType() == Material.GREEN_CANDLE) {
                    if (target != null) {
                        TrollUtils.nausea(p, target);
                    } else {
                        return;
                    }


                } else if (e.getCurrentItem().getType() == Material.TURTLE_SPAWN_EGG) {
                    if (target != null) {
                        TrollUtils.slow(p, target);
                    } else {
                        return;
                    }

                } else if (e.getCurrentItem().getType() == Material.GRAY_DYE) {
                    if (target != null) {
                        TrollUtils.resetslow(p, target);
                    } else {
                        return;
                    }

                } else if (e.getCurrentItem().getType() == Material.WARDEN_SPAWN_EGG) {
                    if (target != null) {
                        TrollUtils.wardenSound(p, target);
                    } else {
                        return;
                    }


                } else if (e.getCurrentItem().getType() == Material.ANVIL) {
                    if (target != null) {
                        TrollUtils.fakeBan(p, target);
                    } else {
                        return;
                    }


                } else if (e.getCurrentItem().getType() == Material.FEATHER) {
                    if (target != null) {
                        TrollUtils.noGravity(p, target);
                    } else {
                        return;
                    }


                } else if (e.getCurrentItem().getType() == Material.GREEN_DYE) {
                    if (target != null) {
                        TrollUtils.gravity(p, target);
                    } else {
                        return;
                    }


                } else if (e.getCurrentItem().getType() == Material.GUNPOWDER) {
                    if (target != null) {
                        TrollUtils.creeperSound(p, target);
                    } else {
                        return;
                    }


                } else if (e.getCurrentItem().getType() == Material.COBWEB) {
                    if (target != null) {
                        TrollUtils.cobweb(p, target);
                    } else {
                        return;
                    }


                } else if (e.getCurrentItem().getType() == Material.ENDER_EYE) {
                    if (target != null) {
                        TrollUtils.timeWarp(p, target);
                    } else {
                        return;
                    }


                } else if (e.getCurrentItem().getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                    if (target != null) {
                        TrollUtils.explosiveApple(p, target);
                    } else {
                        return;
                    }


                } else if (e.getCurrentItem().getType() == Material.FIRE_CHARGE) {
                    if (target != null) {
                        TrollUtils.meteor(p, target);
                    } else {
                        return;
                    }


                } else if (e.getCurrentItem().getType() == Material.POTATO) {
                    if (target != null) {
                        TrollUtils.patata(p, target);
                    } else {
                        return;
                    }


                } else if (e.getCurrentItem().getType() == Material.WITHER_SKELETON_SPAWN_EGG) {
                    GuiUtils.openPickMobGui(p, target);

                } else if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.PAPER) {
                    if (target != null) {
                        TrollUtils.fakeMessage(p, target);
                    } else {
                        return;
                    }

                } else if (e.getCurrentItem().getType() == Material.RED_DYE) {
                    if (target != null) {
                        TrollUtils.oneHeart(p, target);
                    } else {
                        return;
                    }

                } else if (e.getCurrentItem().getType() == Material.SAND) {
                    if (target != null) {
                        Location location = target.getLocation();
                        TrollUtils.pepe(p, target, location);
                    } else {
                        return;
                    }

                } else if (e.getCurrentItem().getType() == Material.BEDROCK) {
                    if (target != null) {
                        TrollUtils.cancelBreakBLocks(p, target);
                    } else {
                        return;
                    }

                } else if (e.getCurrentItem().getType() == Material.IRON_BARS) {
                    if (target != null) {
                        TrollUtils.cage(p, target);
                    } else {
                        return;
                    }

                } else if (e.getCurrentItem().getType() == lavaSwapMaterial) {
                    if (target != null) {
                        TrollUtils.swapLavaWater(p, target);
                        GuiUtils.openTrollGui(p, target);
                    } else {
                        return;
                    }

                } else if (e.getCurrentItem().getType() == Material.BAKED_POTATO) {
                    if (target != null) {
                        TrollUtils.hotPotato(p, target);
                    } else {
                        return;
                    }

                } else if (e.getCurrentItem().getType() == reachMaterial) {
                    if (target != null) {
                        TrollUtils.reach(p, target);
                        GuiUtils.openTrollGui(p, target);
                    } else {
                        return;
                    }

                } else if (e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                    if (target != null) {
                        String textureValue = Base64.getEncoder().encodeToString("ewogICJ0aW1lc3RhbXAiIDogMTU5NjQwNjUyNDgyNiwKICAicHJvZmlsZUlkIiA6ICJmMjc0YzRkNjI1MDQ0ZTQxOGVmYmYwNmM3NWIyMDIxMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJIeXBpZ3NlbCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81NDRjNTk0ZWI2MTBkMDAzNjQxODQ0OTBkZTVkNjg4N2Q0ZmVmZTc2ODAwYzEyNjA3MGIwN2EyMTBmNDEyNTY2IgogICAgfQogIH0KfQ==".getBytes());
                        String textureSignature = "LK5HbVn5lMXgEFxhvVN60g3Smpa6tC60ji+66mOaY/T3/WPPB/0fPE8Ea0m3OVKBd8U8/pnfmqaNGgfXw3EpJXX0nmQkoA0GroQ/1l3fzB+OMuEjahtaiEw0hwjq/GWN4fXog9QZeb79bzKPLIxz6bN3M+DJVxien86ZAJd8fr6+BqJCy01WAXElbCQrBA7oLxjhMIhhoevlB7NmQ/ikadYXQ4eqtpLtY/En7nr0LMPdEzz/ixxD76mGN0KJORY7ab5+nRkABNhDLf31uOZ/woE0mXkeIq5jJ8HoAAG1gEbzXoSHNRfreUfgY5fYsWGXBbD4vd6oZHAHES9MZlocnMtcaWEBpero2sHMI3LiaXRyvFBz3/QoMBsXA4CcvEEhNKHW8RJTKKi2E7KKMENhOUF7D9e6piHWJQKCNgahVIbbVMzf/dlWCrGOAbIlCqvV7ZoeoMyBheCGDlM5THl5HUMMfu+T8JhSFCxNsgzFG8hLnwQW8iusD49MuQtLTFnjfOK2LxJ25agawXaRnPwonCasjtZfHcGU9C6u4edRHDTEhrPHAZKkvMy1GeagpPd9NFGIL4Q2ypO/OIMSGKaBTqitrTPbhPXe80As/GYekXC4tUkufnh/QqbhSFZFskXQDYlRLGZujMFC58LDkwoqbrqhvgtJyY5Z9aEjJ0plZRs=";
                        ItemStack skull = getAlienHead(textureValue, textureSignature);
                        if (e.getCurrentItem().equals(skull)) {
                            TrollUtils.ufo(p, target);
                        }
                    } else {
                        return;
                    }
                } else if (e.getCurrentItem().getType() == Material.ENDER_PEARL) {
                    if (target != null) {
                        TrollUtils.teleport(p, target);
                    } else {
                        return;
                    }

                }

            }
            e.setCancelled(true);
        } else if (e.getView().getTitle().equalsIgnoreCase(ChatColor.BLUE + "" + ChatColor.BOLD + "Pick Mob")) {
            Location location = target.getLocation().add(0, 1, 4);
            if (e.getCurrentItem().getType() == Material.ZOMBIE_SPAWN_EGG) {
                TrollUtils.zombie(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.SKELETON_SPAWN_EGG) {
                TrollUtils.skeleton(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.SPIDER_SPAWN_EGG) {
                TrollUtils.spider(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.CHICKEN_SPAWN_EGG) {
                TrollUtils.chickenJokey(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.RAVAGER_SPAWN_EGG) {
                TrollUtils.ravager(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.ENDERMAN_SPAWN_EGG) {
                TrollUtils.enderman(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.WARDEN_SPAWN_EGG) {
                TrollUtils.warden(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.GHAST_SPAWN_EGG) {
                TrollUtils.ironGolem(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.CREEPER_SPAWN_EGG) {
                String displayName = e.getCurrentItem().getItemMeta().getDisplayName();
                if (displayName.equalsIgnoreCase(ChatColor.BLUE + "Charged " + ChatColor.GREEN + "Creeper")) {
                    TrollUtils.chargedCreeper(p, target, location);
                } else if (displayName.equalsIgnoreCase(ChatColor.DARK_GREEN + "Spawn Creeper")) {
                    TrollUtils.creeper(p, target, location);
                }
            } else if (e.getCurrentItem().getType() == Material.POLAR_BEAR_SPAWN_EGG) {
                TrollUtils.snowMan(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.BLAZE_SPAWN_EGG) {
                TrollUtils.blaze(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.AXOLOTL_SPAWN_EGG) {
                TrollUtils.axolotl(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.ALLAY_SPAWN_EGG) {
                TrollUtils.allay(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.SNIFFER_SPAWN_EGG) {
                TrollUtils.sniffer(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.CAMEL_SPAWN_EGG) {
                TrollUtils.camel(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.PANDA_SPAWN_EGG) {
                TrollUtils.allay(p, target, location);
            } else if (e.getCurrentItem().getType() == Material.BARRIER) {
                GuiUtils.openTrollGui(p, target);
            }

            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onSnowballHIt(ProjectileHitEvent e) {
        Projectile projectile = e.getEntity();

        if (projectile.getShooter() instanceof Snowman && projectile.getType() == EntityType.SNOWBALL) {
            Snowman snowman = (Snowman) projectile.getShooter();
            PersistentDataContainer snowManData = snowman.getPersistentDataContainer();
            if (e.getHitEntity() instanceof Player) {
                if (snowManData.has(new NamespacedKey(plugin, "isFromTroll"), PersistentDataType.INTEGER)) {
                    Player p = (Player) e.getHitEntity();
                    if (!(p.getGameMode() == GameMode.CREATIVE)) {
                        p.setVelocity(snowman.getLocation().getDirection().multiply(0.5));
                        p.damage(0.1);
                    }
                }
            }
        }
    }

}




















