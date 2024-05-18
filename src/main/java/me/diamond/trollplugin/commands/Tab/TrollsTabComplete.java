package me.diamond.trollplugin.commands.Tab;

import me.diamond.trollplugin.utils.ServerVersionUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TrollsTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {

        Player p = (Player) sender;

        if (args.length == 2) {
            List<String> availableArgs = new ArrayList<>();
            if (p.hasPermission("diatroll.troll.launch")) {

            }
            if (p.hasPermission("diatroll.troll.lightning")) {
                availableArgs.add("lightning");
            }
            if (p.hasPermission("diatroll.troll.nointernet")) {
                availableArgs.add("nointernet");
            }
            if (p.hasPermission("diatroll.troll.fire")) {
                availableArgs.add("fire");
            }
            if (p.hasPermission("diatroll.troll.nausea")) {
                availableArgs.add("nausea");
            }
            if (p.hasPermission("diatroll.troll.slow")) {
                availableArgs.add("slowreset");
                availableArgs.add("slow");
            }
            if (p.hasPermission("diatroll.troll.wardensound")) {
                availableArgs.add("wardensound");
            }
            if (p.hasPermission("diatroll.troll.fakeban")) {
                availableArgs.add("fakeban");

            }
            if (p.hasPermission("diatroll.troll.togglegravity")) {
                availableArgs.add("gravity");
                availableArgs.add("nogravity");
            }
            if (p.hasPermission("diatroll.troll.creepersound")) {
                availableArgs.add("creeper");
            }
            if (p.hasPermission("diatroll.troll.cobweb")) {
                availableArgs.add("cobweb");
            }
            if (p.hasPermission("diatroll.troll.timewarp")) {
                availableArgs.add("timewarp");
            }
            if (p.hasPermission("diatroll.troll.explosiveapple")) {
                availableArgs.add("explosiveapple");
            }
            if (p.hasPermission("diatroll.troll.meteor")) {
                availableArgs.add("meteor");
            }
            if (p.hasPermission("diatroll.troll.patata")) {
                availableArgs.add("patata");
            }
            if (p.hasPermission("diatroll.troll.spawnmobs")) {
                availableArgs.add("spawn");
            }
            if (p.hasPermission("diatroll.troll.fakemessage")) {
                availableArgs.add("fakemessage");
            }
            if (p.hasPermission("diatroll.troll.oneheart")) {
                availableArgs.add("oneheart");
            }
            if (p.hasPermission("diatroll.troll.pp")) {
                availableArgs.add("pp");
            } if (p.hasPermission("diatroll.troll.cancelbreakblocks")) {
                availableArgs.add("cancelbreakblocks");
            } if (p.hasPermission("diatroll.troll.cage")) {
                availableArgs.add("cage");
            } if (p.hasPermission("diatroll.troll.swaplavawater")) {
                availableArgs.add("swaplavawater");
            } if (p.hasPermission("diatroll.troll.lowerreach")) {
                availableArgs.add("lowerreach");
            } if (p.hasPermission("diatroll.troll.hotpotato")) {
                availableArgs.add("hotpotato");
            } if (p.hasPermission("diatroll.troll.ufo")) {
                availableArgs.add("UFO");
            }
            return availableArgs;

        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("spawn")) {
                List<String> availableArgs = new ArrayList<>();
                if (p.hasPermission("diatroll.troll.spawnmobs.zombie")) {
                    availableArgs.add("zombie");
                }
                if (p.hasPermission("diatroll.troll.spawnmobs.skeleton")) {
                    availableArgs.add("skeleton");
                }
                if (p.hasPermission("diatroll.troll.spawnmobs.spider")) {
                    availableArgs.add("spider");
                }
                if (p.hasPermission("diatroll.troll.spawnmobs.chickenjokey")) {
                    availableArgs.add("chickenjokey");
                }
                if (p.hasPermission("diatroll.troll.spawnmobs.ravager")) {
                    availableArgs.add("ravager");
                }
                if (p.hasPermission("diatroll.troll.spawnmobs.enderman")) {
                    availableArgs.add("enderman");
                }
                if (p.hasPermission("diatroll.troll.spawnmobs.warden")) {
                    availableArgs.add("warden");
                }
                if (p.hasPermission("diatroll.troll.spawnmobs.irongolem")) {
                    availableArgs.add("irongolem");
                }
                if (p.hasPermission("diatroll.troll.spawnmobs.creeper")) {
                    availableArgs.add("creeper");
                }
                if (p.hasPermission("diatroll.troll.spawnmobs.chargedcreeper")) {
                    availableArgs.add("chargedcreeper");
                }
                if (p.hasPermission("diatroll.troll.spawnmobs.snowman")) {
                    availableArgs.add("snowman");
                }
                if (p.hasPermission("diatroll.troll.spawnmobs.blaze")) {
                    availableArgs.add("blaze");
                }
                if (p.hasPermission("diatroll.troll.spawnmobs.axolotl")) {
                    if (ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.17")) {
                        availableArgs.add("axolotl");
                    }
                }
                if (p.hasPermission("diatroll.troll.spawnmobs.allay")) {
                    if (ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.19")) {
                        availableArgs.add("allay");
                    }
                } if (p.hasPermission("diatroll.troll.spawnmobs.sniffer")) {
                    if (!ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.20.1")) {
                        availableArgs.add("sniffer");
                    }
                }
                return availableArgs;
            }
        }


        return null;
    }
}
