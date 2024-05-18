package me.diamond.trollplugin.commands;

import me.diamond.trollplugin.TrollPlugin;
import me.diamond.trollplugin.utils.GuiUtils;
import me.diamond.trollplugin.utils.ServerVersionUtils;
import me.diamond.trollplugin.troll_utils.TrollUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrollCommand implements CommandExecutor {

    private final TrollPlugin plugin;

    public TrollCommand(TrollPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (p.hasPermission("diatroll.troll") || p.hasPermission("diatroll.admin")) {
                if (args.length == 0) {
                    p.sendMessage(ChatColor.RED + "You need to provide a player name");
                    p.sendMessage(ChatColor.RED + "How to use this command: /troll <player>");
                } else {
                    String playerName = args[0];
                    Player target = Bukkit.getPlayerExact(playerName);
                    if (target == null) {
                        p.sendMessage(ChatColor.RED + "This Player is not online. Please Provide A valid player.");
                    } else {
                        if (args.length == 1) {
                            GuiUtils.openTrollGui(p, target);
                        } else if (args.length == 2) {
                            if (args[1].equalsIgnoreCase("launch")) {
                                if (p.hasPermission("diatroll.troll.launch")) {
                                    TrollUtils.launch(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("lightning")) {
                                if (p.hasPermission("diatroll.troll.lightning")) {
                                    TrollUtils.lightning(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("nowifi")) {
                                if (p.hasPermission("diatroll.troll.nointernet")) {
                                    TrollUtils.noInternet(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("fire")) {
                                if (p.hasPermission("diatroll.troll.fire")) {
                                    TrollUtils.fire(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("nausea")) {
                                if (p.hasPermission("diatroll.troll.nausea")) {
                                    TrollUtils.nausea(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("slow")) {
                                if (p.hasPermission("diatroll.troll.slow")) {
                                    TrollUtils.slow(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("slowreset")) {
                                if (p.hasPermission("diatroll.troll.slow")) {
                                    TrollUtils.resetslow(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("warden")) {
                                if (p.hasPermission("diatroll.troll.wardensound")) {
                                    TrollUtils.wardenSound(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("fakeban")) {
                                if (p.hasPermission("diatroll.troll.fakeban")) {
                                    TrollUtils.fakeBan(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("nogravity")) {
                                if (p.hasPermission("diatroll.troll.togglegravity")) {
                                    TrollUtils.noGravity(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("gravity")) {
                                if (p.hasPermission("diatroll.troll.togglegravity")) {
                                    TrollUtils.gravity(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("creeper")) {
                                if (p.hasPermission("diatroll.troll.creepersound")) {
                                    TrollUtils.creeperSound(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("cobweb")) {
                                if (p.hasPermission("diatroll.troll.cobweb")) {
                                    TrollUtils.cobweb(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("timewarp")) {
                                if (p.hasPermission("diatroll.troll.timewarp")) {
                                    TrollUtils.timeWarp(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("explosiveapple")) {
                                if (p.hasPermission("diatroll.troll.explosiveapple")) {
                                    TrollUtils.explosiveApple(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("meteor")) {
                                if (p.hasPermission("diatroll.troll.meteor")) {
                                    TrollUtils.meteor(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("patata")) {
                                if (p.hasPermission("diatroll.troll.patata")) {
                                    TrollUtils.patata(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("fakemessage")) {
                                if (p.hasPermission("diatroll.troll.fakemessage")) {
                                    TrollUtils.fakeMessage(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("oneheart")) {
                                if (p.hasPermission("diatroll.troll.oneheart")) {
                                    TrollUtils.oneHeart(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("cancelbreakblocks")) {
                                if (p.hasPermission("diatroll.troll.cancelbreakblocks")) {
                                    TrollUtils.cancelBreakBLocks(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("cage")) {
                                if (p.hasPermission("diatroll.troll.cage")) {
                                    TrollUtils.cage(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("swaplavawater")) {
                                if (p.hasPermission("diatroll.troll.swaplavawater")) {
                                    TrollUtils.swapLavaWater(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("lowerreach")) {
                                if (p.hasPermission("diatroll.troll.lowerreach")) {
                                    int reach;
                                    if (plugin.loweredReach.get(target) != null){
                                        reach = plugin.loweredReach.get(target);
                                    } else {
                                        reach = 3;
                                    }
                                    TrollUtils.reach(p, target);
                                    p.sendMessage(ChatColor.RED + "Current reach: " + reach);
                                }
                            } else if (args[1].equalsIgnoreCase("hotpotato")) {
                                if (p.hasPermission("diatroll.troll.hotpotato")) {
                                    TrollUtils.hotPotato(p, target);
                                }
                            } else if (args[1].equalsIgnoreCase("UFO")) {
                                if (p.hasPermission("diatroll.troll.ufo")) {
//                                    TrollUtils.ufo(p, target);
                                }
                            }
                        } else if (args.length == 3) {
                            Location location = target.getLocation().add(0, 1, 4);
                            if (args[1].equalsIgnoreCase("spawn")) {
                                if (args[2].equalsIgnoreCase("zombie")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.zombie")) {
                                        TrollUtils.zombie(p, target, location);
                                    }
                                } else if (args[2].equalsIgnoreCase("skeleton")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.skeleton")) {
                                        TrollUtils.skeleton(p, target, location);
                                    }
                                } else if (args[2].equalsIgnoreCase("spider")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.spider")) {
                                        TrollUtils.spider(p, target, location);
                                    }
                                } else if (args[2].equalsIgnoreCase("chickenjokey")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.chickenjokey")) {
                                        TrollUtils.chickenJokey(p, target, location);
                                    }
                                } else if (args[2].equalsIgnoreCase("ravager")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.ravager")) {
                                        TrollUtils.ravager(p, target, location);
                                    }
                                } else if (args[2].equalsIgnoreCase("enderman")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.enderman")) {
                                        TrollUtils.enderman(p, target, location);
                                    }
                                } else if (args[2].equalsIgnoreCase("warden")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.warden")) {
                                        if (ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.19")) {
                                            TrollUtils.warden(p, target, location);
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Sorry, This is 1.19+");
                                        }
                                    }
                                } else if (args[2].equalsIgnoreCase("irongolem")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.irongolem")) {
                                        TrollUtils.ironGolem(p, target, location);
                                    }
                                } else if (args[2].equalsIgnoreCase("creeper")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.creeper")) {
                                        TrollUtils.creeper(p, target, location);
                                    }
                                } else if (args[2].equalsIgnoreCase("chargedcreeper")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.chargedcreeper")) {
                                        TrollUtils.chargedCreeper(p, target, location);
                                    }
                                } else if (args[2].equalsIgnoreCase("snowman")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.snowman")) {
                                        TrollUtils.snowMan(p, target, location);
                                    }
                                } else if (args[2].equalsIgnoreCase("blaze")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.blaze")) {
                                        TrollUtils.blaze(p, target, location);
                                    }
                                } else if (args[2].equalsIgnoreCase("axolotl")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.axolotl")) {
                                        if (ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.17")) {
                                            TrollUtils.axolotl(p, target, location);
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Sorry, This is 1.17+");
                                        }
                                    }
                                } else if (args[2].equalsIgnoreCase("sniffer")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.sniffer")) {
                                        if (!ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.20.1")) {
                                            TrollUtils.sniffer(p, target, location);
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Sorry, This is 1.20.1+");
                                        }
                                    }
                                } else if (args[2].equalsIgnoreCase("allay")) {
                                    if (p.hasPermission("diatroll.troll.spawnmobs.allay")) {
                                        if (ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.19")) {
                                            TrollUtils.allay(p, target, location);
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Sorry, This is 1.19+");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                p.sendMessage(ChatColor.RED + "You do not have permission to run this command");
            }

        } else {
            sender.sendMessage(ChatColor.RED + "Only players can access this command.");
        }
        return true;
    }
}

