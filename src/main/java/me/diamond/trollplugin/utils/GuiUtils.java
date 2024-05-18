package me.diamond.trollplugin.utils;

import me.diamond.trollplugin.TrollPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;


import java.util.ArrayList;
import java.util.Base64;

import static me.diamond.trollplugin.utils.ItemUtils.getAlienHead;

public class GuiUtils {

    private static TrollPlugin plugin;

    public GuiUtils(Plugin plugin) {
        this.plugin = (TrollPlugin) plugin;
    }

    private static Material lavaSwapMaterial;

    private static Material reachMaterial;

    static boolean isEconomyEnabled() {
        if (plugin.getConfig().getBoolean("Economy-Enabled") && plugin.isVaultInstalled()) {
            return true;
        } else {
            return false;
        }
    }


    public static void openTrollGui(Player p, Player target) {
        FileConfiguration config = plugin.getConfig();
        Inventory trollgui = Bukkit.createInventory(p, 45, ChatColor.GREEN + "" + ChatColor.BOLD + "Troll Gui");

        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullmetainfo = (SkullMeta) head.getItemMeta();
        skullmetainfo.setDisplayName(ChatColor.RED + target.getDisplayName());
        skullmetainfo.setOwnerProfile(target.getPlayerProfile());
        ArrayList<String> head_lore = new ArrayList<>();
        head_lore.add(ChatColor.GOLD + "Ping:" + ChatColor.GREEN + target.getPing());
        head_lore.add(ChatColor.GOLD + "Level:" + ChatColor.GREEN + target.getLevel());
        head_lore.add(ChatColor.GOLD + "Health:" + ChatColor.RED + target.getHealth());
        skullmetainfo.setLore(head_lore);
        head.setItemMeta(skullmetainfo);


        ItemStack launch = new ItemStack(Material.SLIME_BALL);
        ItemMeta launch_meta = launch.getItemMeta();
        launch_meta.setDisplayName(ChatColor.AQUA + "Launch");
        ArrayList<String> launch_lore = new ArrayList<>();
        launch_lore.add(ChatColor.GREEN + "Launches player into the air");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            launch_lore.add("--------------------");
            launch_lore.add(ChatColor.GREEN + "Cost: $" + config.getDouble("launch-cost"));
        }
        launch_meta.setLore(launch_lore);
        launch.setItemMeta(launch_meta);

        Material lightningMaterial;
        if (ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.17")) {
            lightningMaterial = Material.LIGHTNING_ROD;
        } else {
            lightningMaterial = Material.SPECTRAL_ARROW;
        }
        ItemStack lightning = new ItemStack(lightningMaterial);
        ItemMeta lightning_meta = lightning.getItemMeta();
        lightning_meta.setDisplayName(ChatColor.YELLOW + "Lightning");
        ArrayList<String> lightning_lore = new ArrayList<>();
        lightning_lore.add(ChatColor.GREEN + "Strike player with lightning");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            lightning_lore.add("--------------------");
            lightning_lore.add(ChatColor.GREEN + "Cost: $" + config.getDouble("lightning-cost"));
        }
        lightning_meta.setLore(lightning_lore);
        lightning.setItemMeta(lightning_meta);


        ItemStack wifilag = new ItemStack(Material.BARRIER);
        ItemMeta wifilag_meta = wifilag.getItemMeta();
        wifilag_meta.setDisplayName(ChatColor.DARK_RED + "No wifi");
        ArrayList<String> wifilag_lore = new ArrayList<>();
        wifilag_lore.add(ChatColor.RED + "Fake Kicks player for having no wifi");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            wifilag_lore.add(ChatColor.DARK_RED + "--------------------");
            wifilag_lore.add(ChatColor.RED + "Cost: $" + config.getDouble("no-Internet-cost"));
        }
        wifilag_meta.setLore(wifilag_lore);
        wifilag.setItemMeta(wifilag_meta);

        ItemStack fire = new ItemStack(Material.FLINT_AND_STEEL);
        ItemMeta fire_meta = fire.getItemMeta();
        fire_meta.setDisplayName(ChatColor.GOLD + "Fire");
        ArrayList<String> fire_lore = new ArrayList<>();
        fire_lore.add(ChatColor.YELLOW + "Set The player on fire");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            fire_lore.add(ChatColor.GOLD + "--------------------");
            fire_lore.add(ChatColor.YELLOW + "Cost: $" + config.getDouble("set-on-fire-cost"));
        }
        fire_meta.setLore(fire_lore);
        fire.setItemMeta(fire_meta);

        ItemStack nausea = new ItemStack(Material.GREEN_CANDLE);
        ItemMeta nausea_meta = nausea.getItemMeta();
        nausea_meta.setDisplayName(ChatColor.DARK_GREEN + "Nausea");
        ArrayList<String> nausea_lore = new ArrayList<>();
        nausea_lore.add(ChatColor.GREEN + "Gives the player the nausea effect for 1 minute");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            nausea_lore.add("--------------------");
            nausea_lore.add(ChatColor.GREEN + "Cost: $" + config.getDouble("nausea-cost"));
        }
        nausea_meta.setLore(nausea_lore);
        nausea.setItemMeta(nausea_meta);

        ItemStack slow = new ItemStack(Material.TURTLE_SPAWN_EGG);
        ItemMeta slow_meta = slow.getItemMeta();
        slow_meta.setDisplayName(ChatColor.BLACK + "Slow");
        ArrayList<String> slow_lore = new ArrayList<>();
        slow_lore.add(ChatColor.DARK_GRAY + "Makes the player 2x SLower " + ChatColor.UNDERLINE + "" + ChatColor.RED + "With no potion effects");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            slow_lore.add(ChatColor.BLACK + "--------------------");
            slow_lore.add(ChatColor.DARK_GRAY + "Cost: $" + config.getDouble("slow-cost"));
        }
        slow_meta.setLore(slow_lore);
        slow.setItemMeta(slow_meta);

        ItemStack slowreset = new ItemStack(Material.GRAY_DYE);
        ItemMeta slowreset_meta = slowreset.getItemMeta();
        slowreset_meta.setDisplayName("Reset Slow");
        ArrayList<String> slowreset_lore = new ArrayList<>();
        slowreset_lore.add("Makes The player the default speed");
        slowreset_meta.setLore(slowreset_lore);
        slowreset.setItemMeta(slowreset_meta);


        ItemStack warden = new ItemStack(Material.WARDEN_SPAWN_EGG);
        ItemMeta warden_meta = warden.getItemMeta();
        warden_meta.setDisplayName(ChatColor.BLACK + "Warden");
        ArrayList<String> warden_lore = new ArrayList<>();
        warden_lore.add(ChatColor.DARK_GRAY + "Plays the warden sound to the player");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            warden_lore.add(ChatColor.BLACK + "--------------------");
            warden_lore.add(ChatColor.DARK_GRAY + "Cost: $" + config.getDouble("fake-sound-cost"));
        }
        warden_meta.setLore(warden_lore);
        warden.setItemMeta(warden_meta);

        ItemStack ban = new ItemStack(Material.ANVIL);
        ItemMeta ban_meta = ban.getItemMeta();
        ban_meta.setDisplayName(ChatColor.DARK_RED + "Fake Ban");
        ArrayList<String> ban_lore = new ArrayList<>();
        ban_lore.add(ChatColor.DARK_GRAY + "Kicks the player imitating a ban");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ban_lore.add(ChatColor.DARK_RED + "--------------------");
            ban_lore.add(ChatColor.DARK_GRAY + "Cost: $" + config.getDouble("fake-ban-cost"));
        }
        ban_meta.setLore(ban_lore);
        ban.setItemMeta(ban_meta);

        ItemStack gravity = new ItemStack(Material.FEATHER);
        ItemMeta gravity_meta = gravity.getItemMeta();
        gravity_meta.setDisplayName(ChatColor.DARK_BLUE + "Disable Gravity");
        ArrayList<String> gravity_lore = new ArrayList<>();
        gravity_lore.add(ChatColor.BLUE + "Disables The Gravity For The player");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            gravity_lore.add(ChatColor.DARK_BLUE + "--------------------");
            gravity_lore.add(ChatColor.BLUE + "Cost: $" + config.getDouble("no-gravity-cost"));
        }
        gravity_meta.setLore(gravity_lore);
        gravity.setItemMeta(gravity_meta);

        ItemStack egravity = new ItemStack(Material.GREEN_DYE);
        ItemMeta egravity_meta = egravity.getItemMeta();
        egravity_meta.setDisplayName(ChatColor.DARK_BLUE + "Enable Gravity");
        ArrayList<String> egravity_lore = new ArrayList<>();
        egravity_lore.add(ChatColor.BLUE + "Enables The Gravity For The player");
        egravity_meta.setLore(egravity_lore);
        egravity.setItemMeta(egravity_meta);

        ItemStack creperexplossion = new ItemStack(Material.GUNPOWDER);
        ItemMeta creperexplossion_meta = creperexplossion.getItemMeta();
        creperexplossion_meta.setDisplayName(ChatColor.GREEN + "Creeper Sound");
        ArrayList<String> creperexplossion_lore = new ArrayList<>();
        creperexplossion_lore.add(ChatColor.DARK_GREEN + "Plays the creeper explosion sound to the player, scaring them");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            creperexplossion_lore.add("--------------------");
            creperexplossion_lore.add(ChatColor.DARK_GREEN + "Cost: $" + config.getDouble("fake-sound-cost"));
        }
        creperexplossion_meta.setLore(creperexplossion_lore);
        creperexplossion.setItemMeta(creperexplossion_meta);

        ItemStack cobweb = new ItemStack(Material.COBWEB);
        ItemMeta cobweb_meta = cobweb.getItemMeta();
        cobweb_meta.setDisplayName(ChatColor.GRAY + "Cobweb Player");
        ArrayList<String> cobweb_lore = new ArrayList<>();
        cobweb_lore.add(ChatColor.WHITE + "Surrounds player with cobwebs ");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            cobweb_lore.add(ChatColor.GRAY + "--------------------");
            cobweb_lore.add(ChatColor.WHITE + "Cost: $" + config.getDouble("cobweb-cost"));
        }
        cobweb_meta.setLore(cobweb_lore);
        cobweb.setItemMeta(cobweb_meta);

        ItemStack timewarp = new ItemStack(Material.ENDER_EYE);
        ItemMeta timewarp_meta = timewarp.getItemMeta();
        timewarp_meta.setDisplayName(ChatColor.DARK_AQUA + "Time Warp");
        ArrayList<String> timewarp_lore = new ArrayList<>();
        timewarp_lore.add(ChatColor.DARK_GREEN + "Teleports player " + config.getInt("time-warp-time") + "seconds back");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            timewarp_lore.add(ChatColor.DARK_AQUA + "--------------------");
            timewarp_lore.add(ChatColor.DARK_GREEN + "Cost: $" + config.getDouble("time-warp-cost"));
        }
        timewarp_meta.setLore(timewarp_lore);
        timewarp.setItemMeta(timewarp_meta);

        ItemStack explosiveapple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        ItemMeta explosiveapple_meta = explosiveapple.getItemMeta();
        explosiveapple_meta.setDisplayName(ChatColor.YELLOW + "Explosive Apple");
        ArrayList<String> explosiveapple_lore = new ArrayList<>();
        explosiveapple_lore.add(ChatColor.LIGHT_PURPLE + "Gives the player a enchanted golden apple,");
        explosiveapple_lore.add(ChatColor.LIGHT_PURPLE + "but when they eat it they explode");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            explosiveapple_lore.add(ChatColor.YELLOW + "--------------------");
            explosiveapple_lore.add(ChatColor.LIGHT_PURPLE + "Cost: $" + config.getDouble("explosive-apple-cost"));
        }
        explosiveapple_meta.setLore(explosiveapple_lore);
        explosiveapple.setItemMeta(explosiveapple_meta);

        ItemStack fireball = new ItemStack(Material.FIRE_CHARGE);
        ItemMeta fireball_meta = fireball.getItemMeta();
        fireball_meta.setDisplayName(ChatColor.GOLD + "Meteor");
        ArrayList<String> fireball_lore = new ArrayList<>();
        fireball_lore.add(ChatColor.DARK_GRAY + "Summons a meteor above the player");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            fireball_lore.add(ChatColor.GOLD + "--------------------");
            fireball_lore.add(ChatColor.DARK_GRAY + "Cost: $" + config.getDouble("meteor-cost"));
        }
        fireball_meta.setLore(fireball_lore);
        fireball.setItemMeta(fireball_meta);

        ItemStack potato = new ItemStack(Material.POTATO);
        ItemMeta potato_meta = potato.getItemMeta();
        potato_meta.setDisplayName(ChatColor.YELLOW + "Patata");
        ArrayList<String> potato_lore = new ArrayList<>();
        potato_lore.add(ChatColor.DARK_AQUA + "Replaces Every Item in their inventory with an Potato, ");
        potato_lore.add(ChatColor.DARK_AQUA + "and undoes after " + config.getInt("patata-time") + "s");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            potato_lore.add(ChatColor.YELLOW + "--------------------");
            potato_lore.add(ChatColor.DARK_AQUA + "Cost: $" + config.getDouble("patata-cost"));
        }
        potato_meta.setLore(potato_lore);
        potato.setItemMeta(potato_meta);

        ItemStack mob = new ItemStack(Material.WITHER_SKELETON_SPAWN_EGG);
        ItemMeta mob_meta = mob.getItemMeta();
        mob_meta.setDisplayName(ChatColor.DARK_GRAY + "Spawn Mobs");
        ArrayList<String> mob_lore = new ArrayList<>();
        mob_lore.add(ChatColor.GRAY + "Spawn Any mob of yor choice");
        mob_meta.setLore(mob_lore);
        mob.setItemMeta(mob_meta);

        ItemStack msg = new ItemStack(Material.PAPER);
        ItemMeta msg_meta = msg.getItemMeta();
        msg_meta.setDisplayName(ChatColor.YELLOW + "Fake Message");
        ArrayList<String> msg_lore = new ArrayList<>();
        msg_lore.add(ChatColor.WHITE + "Send a random fake message from the ");
        msg_lore.add(ChatColor.WHITE + "trolled player, confusing them");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            msg_lore.add(ChatColor.YELLOW + "--------------------");
            msg_lore.add(ChatColor.WHITE + "Cost: $" + config.getDouble("fake-messages-cost"));
        }
        msg_meta.setLore(msg_lore);
        msg.setItemMeta(msg_meta);

        ItemStack heart = new ItemStack(Material.RED_DYE);
        ItemMeta heart_meta = heart.getItemMeta();
        heart_meta.setDisplayName(ChatColor.DARK_RED + "1 Heart");
        ArrayList<String> heart_lore = new ArrayList<>();
        heart_lore.add(ChatColor.RED + "Sets the player's max health to 2 for 1m");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            heart_lore.add(ChatColor.DARK_RED + "--------------------");
            head_lore.add(ChatColor.RED + "Cost: $" + config.getDouble("one-heart-cost"));
        }
        heart_meta.setLore(heart_lore);
        heart.setItemMeta(heart_meta);

        ItemStack pepe = new ItemStack(Material.SAND);
        ItemMeta pepe_meta = pepe.getItemMeta();
        pepe_meta.setDisplayName(ChatColor.YELLOW + "PP");
        ArrayList<String> pepe_lore = new ArrayList<>();
        pepe_lore.add(ChatColor.GOLD + "PP falls from the sky");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            pepe_lore.add(ChatColor.YELLOW + "--------------------");
            pepe_lore.add(ChatColor.GOLD + "Cost: $" + config.getDouble("pp-cost"));
        }
        pepe_meta.setLore(pepe_lore);
        pepe.setItemMeta(pepe_meta);

        ItemStack bedrock = new ItemStack(Material.BEDROCK);
        ItemMeta bedrock_meta = bedrock.getItemMeta();
        bedrock_meta.setDisplayName(ChatColor.RED + "Cancel Block break");
        ArrayList<String> bedrock_lore = new ArrayList<>();
        bedrock_lore.add(ChatColor.DARK_RED + "Cancel Block breaks for player (30s)");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            bedrock_lore.add(ChatColor.RED + "--------------------");
            bedrock_lore.add(ChatColor.DARK_RED + "Cost: $" + config.getDouble("pp-cost"));
        }
        bedrock_meta.setLore(bedrock_lore);
        bedrock.setItemMeta(bedrock_meta);

        ItemStack cage = new ItemStack(Material.IRON_BARS);
        ItemMeta cage_meta = cage.getItemMeta();
        cage_meta.setDisplayName(ChatColor.GRAY + "Cage");
        ArrayList<String> cage_lore = new ArrayList<>();
        cage_lore.add(ChatColor.DARK_GRAY + "Player Will get trapped in an unbreakable cage");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            cage_lore.add(ChatColor.GRAY + "--------------------");
            cage_lore.add(ChatColor.DARK_GRAY + "Cost: $" + config.getDouble("cage-cost"));
        }
        cage_meta.setLore(cage_lore);
        cage.setItemMeta(cage_meta);


        if (!TrollPlugin.getInstance().lavaSwapped.contains(target)) {
            lavaSwapMaterial = Material.LAVA_BUCKET;
        } else {
            lavaSwapMaterial = Material.WATER_BUCKET;
        }

        ItemStack swapLava = new ItemStack(lavaSwapMaterial);
        ItemMeta swapLava_meta = swapLava.getItemMeta();
        if (!plugin.lavaSwapped.contains(target)) {
            swapLava_meta.setDisplayName(ChatColor.BLUE + "Swap Water with Lava");
        } else {
            swapLava_meta.setDisplayName(ChatColor.GOLD + "Unswap Lava with Water");
        }
        ArrayList<String> swapLava_lore = new ArrayList<>();
        swapLava_lore.add(ChatColor.YELLOW + "Swap lava and water, only for targeted player");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            swapLava_lore.add(ChatColor.GRAY + "--------------------");
            swapLava_lore.add(ChatColor.BLUE + "Cost: $" + config.getDouble("swapLavaWater-cost"));
        }
        swapLava_meta.setLore(swapLava_lore);
        swapLava.setItemMeta(swapLava_meta);


        if (!plugin.loweredReach.containsKey(target)){
            reachMaterial = Material.GREEN_CONCRETE;
        } else {
            if (plugin.loweredReach.get(target) == 2){
                reachMaterial = Material.YELLOW_CONCRETE;
            } else if (plugin.loweredReach.get(target) == 1){
                reachMaterial = Material.RED_CONCRETE;
            }
        }
        ItemStack reach = new ItemStack(reachMaterial);
        ItemMeta reach_meta = reach.getItemMeta();
        if (!plugin.loweredReach.containsKey(target)){
            reach_meta.setDisplayName(ChatColor.GREEN + "Lower Reach, Current: 3");
        } else {
            if (plugin.loweredReach.get(target) == 2){
                reach_meta.setDisplayName(ChatColor.YELLOW + "Lower Reach, Current: 2");
            } else if (plugin.loweredReach.get(target) == 1){
                reach_meta.setDisplayName(ChatColor.RED + "Lower Reach, Current: 1");
            }
        }
        ArrayList<String> reach_lore = new ArrayList<>();
        reach_lore.add("Lower Reach of the Player");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            reach_lore.add(ChatColor.RED + "--------------------");
            reach_lore.add(ChatColor.GREEN + "Cost: $" + config.getDouble("cage-cost"));
        }
        reach_meta.setLore(reach_lore);
        reach.setItemMeta(reach_meta);

        ItemStack hotpotato = new ItemStack(Material.BAKED_POTATO);
        ItemMeta hotpotato_meta = hotpotato.getItemMeta();
        hotpotato_meta.setDisplayName(ChatColor.RED + "HOT POTATO");
        ArrayList<String> hotpotato_lore = new ArrayList<>();
        hotpotato_lore.add(ChatColor.DARK_AQUA + "The HOT POTATO will burn them unless");
        hotpotato_lore.add(ChatColor.DARK_AQUA + "they pass it to someone else");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            hotpotato_lore.add(ChatColor.RED + "--------------------");
            hotpotato_lore.add(ChatColor.DARK_AQUA + "Cost: $" + config.getDouble("hotpotato-cost"));
        }
        hotpotato_meta.setLore(hotpotato_lore);
        hotpotato.setItemMeta(hotpotato_meta);


        ItemStack teleport = new ItemStack(Material.ENDER_PEARL);
        ItemMeta teleport_meta = teleport.getItemMeta();
        teleport_meta.setDisplayName(ChatColor.DARK_AQUA + "Teleport");
        teleport_meta.addEnchant(Enchantment.LUCK, 1, false);
        teleport_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ArrayList<String> teleport_lore = new ArrayList<>();
        teleport_lore.add(ChatColor.DARK_GREEN + "Teleport To player");
        teleport_meta.setLore(teleport_lore);
        teleport.setItemMeta(teleport_meta);

        ItemStack back = new ItemStack(Material.BARRIER, 1);
        ItemMeta back_meta = back.getItemMeta();
        back_meta.setDisplayName(ChatColor.RED + "Close");
        back.setItemMeta(back_meta);



        trollgui.setItem(4, head);
        trollgui.setItem(44, back);

        for (int i = 0; i < 9; i++) {
            if (trollgui.getItem(i) == null) {
                ItemStack fillerglass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta fillerglass_meta = fillerglass.getItemMeta();
                fillerglass_meta.setDisplayName(" ");
                fillerglass.setItemMeta(fillerglass_meta);
                trollgui.setItem(i, fillerglass);
            }
        }
        if (p.hasPermission("diatroll.troll.launch")) {
            trollgui.addItem(launch);
        }
        if (p.hasPermission("diatroll.troll.lightning")) {
            trollgui.addItem(lightning);
        }
        if (p.hasPermission("diatroll.troll.nointernet")) {
            trollgui.addItem(wifilag);
        }
        if (p.hasPermission("diatroll.troll.fire")) {
            trollgui.addItem(fire);
        }
        if (p.hasPermission("diatroll.troll.nausea")) {
            trollgui.addItem(nausea);
        }
        if (p.hasPermission("diatroll.troll.slow")) {
            trollgui.addItem(slow);
            trollgui.addItem(slowreset);
        }
        if (ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.19")) {
            if (p.hasPermission("diatroll.troll.wardensound")) {
                trollgui.addItem(warden);
            }
        }
        if (p.hasPermission("diatroll.troll.fakeban")) {
            trollgui.addItem(ban);
        }
        if (p.hasPermission("diatroll.troll.togglegravity")) {
            trollgui.addItem(gravity);
            trollgui.addItem(egravity);
        }
        if (p.hasPermission("diatroll.troll.creepersound")) {
            trollgui.addItem(creperexplossion);
        }
        if (p.hasPermission("diatroll.troll.cobweb")) {
            trollgui.addItem(cobweb);
        }
        if (p.hasPermission("diatroll.troll.timewarp")) {
            trollgui.addItem(timewarp);
        }
        if (p.hasPermission("diatroll.troll.explosiveapple")) {
            trollgui.addItem(explosiveapple);
        }
        if (p.hasPermission("diatroll.troll.meteor")) {
            trollgui.addItem(fireball);
        }
        if (p.hasPermission("diatroll.troll.patata")) {
            trollgui.addItem(potato);
        }
        if (p.hasPermission("diatroll.troll.spawnmobs")) {
            trollgui.addItem(mob);
        }
        if (p.hasPermission("diatroll.troll.fakemessage")) {
            trollgui.addItem(msg);
        }
        if (p.hasPermission("diatroll.troll.oneheart")) {
            trollgui.addItem(heart);
        }
//        if (p.hasPermission("diatroll.troll.pp")) {
//            trollgui.addItem(pepe);
//        }
        if (p.hasPermission("diatroll.troll.cancelblockbreak")) {
            trollgui.addItem(bedrock);
        }
        if (p.hasPermission("diatroll.troll.cage")) {
            trollgui.addItem(cage);
        }
        if (p.hasPermission("diatroll.troll.swaplavawater")) {
            trollgui.addItem(swapLava);
        }
        if (p.hasPermission("diatroll.teleport")) {
            trollgui.setItem(43, teleport);
        }
        if (p.hasPermission("diatroll.troll.lowerreach")) {
            trollgui.addItem(reach);
        }
        if (p.hasPermission("diatroll.troll.hotpotato")) {
            trollgui.addItem(hotpotato);
        }
//        if (p.hasPermission("diatroll.troll.ufo")) {
//            String textureValue = Base64.getEncoder().encodeToString("ewogICJ0aW1lc3RhbXAiIDogMTU5NjQwNjUyNDgyNiwKICAicHJvZmlsZUlkIiA6ICJmMjc0YzRkNjI1MDQ0ZTQxOGVmYmYwNmM3NWIyMDIxMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJIeXBpZ3NlbCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81NDRjNTk0ZWI2MTBkMDAzNjQxODQ0OTBkZTVkNjg4N2Q0ZmVmZTc2ODAwYzEyNjA3MGIwN2EyMTBmNDEyNTY2IgogICAgfQogIH0KfQ==".getBytes());
//            String textureSignature = "LK5HbVn5lMXgEFxhvVN60g3Smpa6tC60ji+66mOaY/T3/WPPB/0fPE8Ea0m3OVKBd8U8/pnfmqaNGgfXw3EpJXX0nmQkoA0GroQ/1l3fzB+OMuEjahtaiEw0hwjq/GWN4fXog9QZeb79bzKPLIxz6bN3M+DJVxien86ZAJd8fr6+BqJCy01WAXElbCQrBA7oLxjhMIhhoevlB7NmQ/ikadYXQ4eqtpLtY/En7nr0LMPdEzz/ixxD76mGN0KJORY7ab5+nRkABNhDLf31uOZ/woE0mXkeIq5jJ8HoAAG1gEbzXoSHNRfreUfgY5fYsWGXBbD4vd6oZHAHES9MZlocnMtcaWEBpero2sHMI3LiaXRyvFBz3/QoMBsXA4CcvEEhNKHW8RJTKKi2E7KKMENhOUF7D9e6piHWJQKCNgahVIbbVMzf/dlWCrGOAbIlCqvV7ZoeoMyBheCGDlM5THl5HUMMfu+T8JhSFCxNsgzFG8hLnwQW8iusD49MuQtLTFnjfOK2LxJ25agawXaRnPwonCasjtZfHcGU9C6u4edRHDTEhrPHAZKkvMy1GeagpPd9NFGIL4Q2ypO/OIMSGKaBTqitrTPbhPXe80As/GYekXC4tUkufnh/QqbhSFZFskXQDYlRLGZujMFC58LDkwoqbrqhvgtJyY5Z9aEjJ0plZRs=";
//            ItemStack skull = getAlienHead(textureValue, textureSignature);
//            trollgui.addItem(skull);
//        }


        for (int i = 9; i < 45; i++) {
            if (trollgui.getItem(i) == null) {
                ItemStack fillerglass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta fillerglass_meta = fillerglass.getItemMeta();
                fillerglass_meta.setDisplayName(" ");
                fillerglass.setItemMeta(fillerglass_meta);
                trollgui.setItem(i, fillerglass);
            }
        }
        p.openInventory(trollgui);
    }


    public static void openPickMobGui(Player p, Player target) {

        FileConfiguration config = plugin.getConfig();
        Inventory pickMobGui = Bukkit.createInventory(p, 54, ChatColor.BLUE + "" + ChatColor.BOLD + "Pick Mob");

        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta head_meta = (SkullMeta) head.getItemMeta();
        head_meta.setDisplayName(ChatColor.RED + target.getDisplayName());
        head_meta.setOwnerProfile(target.getPlayerProfile());
        ArrayList<String> head_lore = new ArrayList<>();
        head_lore.add(ChatColor.GOLD + "Ping:" + ChatColor.GREEN + target.getPing());
        head_lore.add(ChatColor.GOLD + "Level:" + ChatColor.GREEN + target.getLevel());
        head_lore.add(ChatColor.GOLD + "Health:" + ChatColor.RED + target.getHealth());
        head_meta.setLore(head_lore);
        head.setItemMeta(head_meta);

        ItemStack creeper = new ItemStack(Material.CREEPER_SPAWN_EGG, 1);
        ItemMeta creeper_meta = creeper.getItemMeta();
        creeper_meta.setDisplayName(ChatColor.DARK_GREEN + "Spawn Creeper");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + "--------------------");
            lore.add(ChatColor.DARK_GREEN + "Cost: $" + config.getDouble("Mob-options.Creeper.cost"));
            creeper_meta.setLore(lore);

        }
        creeper.setItemMeta(creeper_meta);

        ItemStack zombie = new ItemStack(Material.ZOMBIE_SPAWN_EGG, 1);
        ItemMeta zombie_meta = zombie.getItemMeta();
        zombie_meta.setDisplayName(ChatColor.DARK_AQUA + "Spawn Zombie");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.AQUA + "--------------------");
            lore.add(ChatColor.DARK_AQUA + "Cost: $" + config.getDouble("Mob-options.Zombie.cost"));
            zombie_meta.setLore(lore);

        }
        zombie.setItemMeta(zombie_meta);


        ItemStack skeleton = new ItemStack(Material.SKELETON_SPAWN_EGG, 1);
        ItemMeta skeleton_meta = skeleton.getItemMeta();
        skeleton_meta.setDisplayName(ChatColor.GRAY + "Spawn Skeleton");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_GRAY + "--------------------");
            lore.add(ChatColor.GRAY + "Cost: $" + config.getDouble("Mob-options.Skeleton.cost"));
            skeleton_meta.setLore(lore);

        }
        skeleton.setItemMeta(skeleton_meta);

        ItemStack spider = new ItemStack(Material.SPIDER_SPAWN_EGG, 1);
        ItemMeta spider_meta = spider.getItemMeta();
        spider_meta.setDisplayName(ChatColor.DARK_RED + "Spawn Spider");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.BLACK + "--------------------");
            lore.add(ChatColor.DARK_RED + "Cost: $" + config.getDouble("Mob-options.Spider.cost"));
            spider_meta.setLore(lore);

        }
        spider.setItemMeta(spider_meta);

        ItemStack chickenjokey = new ItemStack(Material.CHICKEN_SPAWN_EGG, 1);
        ItemMeta chickenjokey_meta = chickenjokey.getItemMeta();
        chickenjokey_meta.setDisplayName(ChatColor.RED + "Spawn " + ChatColor.WHITE + "Chicken Jokey");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "--------------------");
            lore.add(ChatColor.RED + "Cost: $" + config.getDouble("Mob-options.Chicken-Jokey.cost"));
            chickenjokey_meta.setLore(lore);

        }
        chickenjokey.setItemMeta(chickenjokey_meta);

        ItemStack ravager = new ItemStack(Material.RAVAGER_SPAWN_EGG, 1);
        ItemMeta ravager_meta = ravager.getItemMeta();
        ravager_meta.setDisplayName(ChatColor.DARK_GRAY + "Spawn Ravager");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "--------------------");
            lore.add(ChatColor.DARK_GRAY + "Cost: $" + config.getDouble("Mob-options.Ravager.cost"));
            ravager_meta.setLore(lore);

        }
        ravager.setItemMeta(ravager_meta);

        ItemStack enderman = new ItemStack(Material.ENDERMAN_SPAWN_EGG, 1);
        ItemMeta enderman_meta = enderman.getItemMeta();
        enderman_meta.setDisplayName(ChatColor.DARK_GRAY + "Spawn Enderman");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "--------------------");
            lore.add(ChatColor.DARK_GRAY + "Cost: $" + config.getDouble("Mob-options.Enderman.cost"));
            enderman_meta.setLore(lore);

        }
        enderman.setItemMeta(enderman_meta);

        ItemStack warden = new ItemStack(Material.WARDEN_SPAWN_EGG, 1);
        ItemMeta warden_meta = warden.getItemMeta();
        warden_meta.setDisplayName(ChatColor.DARK_GRAY + "Spawn " + ChatColor.DARK_AQUA + "Warden");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_AQUA + "--------------------");
            lore.add(ChatColor.DARK_GRAY + "Cost: $" + config.getDouble("Mob-options.Warden.csot"));
            warden_meta.setLore(lore);

        }
        warden.setItemMeta(warden_meta);

        ItemStack irongolem = new ItemStack(Material.GHAST_SPAWN_EGG, 1);
        ItemMeta irongolem_meta = irongolem.getItemMeta();
        irongolem_meta.setDisplayName(ChatColor.RED + "Spawn Angry " + ChatColor.GRAY + "Iron Golem");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "--------------------");
            lore.add(ChatColor.RED + "Cost: $" + config.getDouble("Mob-options.Iron-Golem.cost"));
            irongolem_meta.setLore(lore);

        }
        irongolem.setItemMeta(irongolem_meta);

        ItemStack charged_creeper = new ItemStack(Material.CREEPER_SPAWN_EGG, 1);
        ItemMeta charged_creeper_meta = charged_creeper.getItemMeta();
        charged_creeper_meta.setDisplayName(ChatColor.BLUE + "Charged " + ChatColor.GREEN + "Creeper");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + "--------------------");
            lore.add(ChatColor.BLUE + "Cost: $" + config.getDouble("Mob-options.Charged-Creeper.cost"));
            charged_creeper_meta.setLore(lore);

        }
        charged_creeper.setItemMeta(charged_creeper_meta);

        ItemStack snowman = new ItemStack(Material.POLAR_BEAR_SPAWN_EGG, 1);
        ItemMeta snowman_meta = snowman.getItemMeta();
        snowman_meta.setDisplayName(ChatColor.GRAY + "Spawn Snow Golem");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_GRAY + "--------------------");
            lore.add(ChatColor.GRAY + "Cost: $" + config.getDouble("Mob-options.Snowman.cost"));
            snowman_meta.setLore(lore);

        }
        snowman.setItemMeta(snowman_meta);

        ItemStack blaze = new ItemStack(Material.BLAZE_SPAWN_EGG, 1);
        ItemMeta blaze_meta = blaze.getItemMeta();
        blaze_meta.setDisplayName(ChatColor.YELLOW + "Spawn" + ChatColor.GOLD + " Blaze");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GOLD + "--------------------");
            lore.add(ChatColor.YELLOW + "Cost: $" + config.getDouble("Mob-options.Blaze.cost"));
            blaze_meta.setLore(lore);

        }
        blaze.setItemMeta(blaze_meta);

        ItemStack axolotl = new ItemStack(Material.AXOLOTL_SPAWN_EGG, 1);
        ItemMeta axolotl_meta = axolotl.getItemMeta();
        axolotl_meta.setDisplayName(ChatColor.WHITE + "Spawn" + ChatColor.LIGHT_PURPLE + " Axolotl");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add("--------------------");
            lore.add(ChatColor.WHITE + "Cost: $" + config.getDouble("Mob-options.Axolotl.cost"));
            axolotl_meta.setLore(lore);

        }
        axolotl.setItemMeta(axolotl_meta);

        ItemStack allay = new ItemStack(Material.ALLAY_SPAWN_EGG, 1);
        ItemMeta allay_meta = allay.getItemMeta();
        allay_meta.setDisplayName(ChatColor.AQUA + "Spawn Allay");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add("--------------------");
            lore.add(ChatColor.AQUA + "Cost: $" + config.getDouble("Mob-options.Allay.cost"));
            allay_meta.setLore(lore);
        }
        allay.setItemMeta(allay_meta);

        ItemStack sniffer = new ItemStack(Material.SNIFFER_SPAWN_EGG, 1);
        ItemMeta sniffer_meta = sniffer.getItemMeta();
        sniffer_meta.setDisplayName(ChatColor.DARK_RED + "Spawn Sniffer");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_GREEN + "--------------------");
            lore.add(ChatColor.DARK_RED + "Cost: $" + config.getDouble("Mob-options.Sniffer.cost"));
            sniffer_meta.setLore(lore);
        }
        sniffer.setItemMeta(sniffer_meta);

        ItemStack camel = new ItemStack(Material.CAMEL_SPAWN_EGG, 1);
        ItemMeta camel_meta = camel.getItemMeta();
        camel_meta.setDisplayName(ChatColor.YELLOW + "Spawn Camel");
        if (isEconomyEnabled() && !p.hasPermission("diatroll.admin")) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GOLD + "--------------------");
            lore.add(ChatColor.YELLOW + "Cost: $" + config.getDouble("Mob-options.Camel.cost"));
            camel_meta.setLore(lore);
        }
        camel.setItemMeta(camel_meta);


        ItemStack back = new ItemStack(Material.BARRIER, 1);
        ItemMeta back_meta = back.getItemMeta();
        back_meta.setDisplayName(ChatColor.RED + "Return");
        back.setItemMeta(back_meta);

        for (int i = 0; i < 9; i++) {
            if (pickMobGui.getItem(i) == null) {
                ItemStack fillerglass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta fillerglass_meta = fillerglass.getItemMeta();
                fillerglass_meta.setDisplayName(" ");
                fillerglass.setItemMeta(fillerglass_meta);
                pickMobGui.setItem(i, fillerglass);
            }
        }

        pickMobGui.setItem(4, head);
        if (p.hasPermission("diatroll.troll.spawnmobs.creeper")) {
            pickMobGui.addItem(creeper);
        }
        if (p.hasPermission("diatroll.troll.spawnmobs.zombie")) {
            pickMobGui.addItem(zombie);
        }
        if (p.hasPermission("diatroll.troll.spawnmobs.skeleton")) {
            pickMobGui.addItem(skeleton);
        }
        if (p.hasPermission("diatroll.troll.spawnmobs.spider")) {
            pickMobGui.addItem(spider);
        }
        if (p.hasPermission("diatroll.troll.spawnmobs.chickenjokey")) {
            pickMobGui.addItem(chickenjokey);
        }
        if (p.hasPermission("diatroll.troll.spawnmobs.ravager")) {
            pickMobGui.addItem(ravager);
        }
        if (p.hasPermission("diatroll.troll.spawnmobs.enderman")) {
            pickMobGui.addItem(enderman);
        }
        if (ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.19")) {
            if (p.hasPermission("diatroll.troll.spawnmobs.warden")) {
                pickMobGui.addItem(warden);
            }
        }
        if (p.hasPermission("diatroll.troll.spawnmobs.irongolem")) {
            pickMobGui.addItem(irongolem);
        }
        if (p.hasPermission("diatroll.troll.spawnmobs.chargedcreeper")) {
            pickMobGui.addItem(charged_creeper);
        }
        if (p.hasPermission("diatroll.troll.spawnmobs.snowman")) {
            pickMobGui.addItem(snowman);
        }
        if (p.hasPermission("diatroll.troll.spawnmobs.blaze")) {
            pickMobGui.addItem(blaze);
        }
        if (p.hasPermission("diatroll.troll.spawnmobs.axolotl")) {
            pickMobGui.addItem(axolotl);
        }
        if (ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.19")) {
            if (p.hasPermission("diatroll.troll.spawnmobs.allay")) {
                pickMobGui.addItem(allay);
            }
        }
        if (!ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.20.1")) {
            if (p.hasPermission("diatroll.troll.spawnmobs.sniffer")) {
                pickMobGui.addItem(sniffer);
            }
        }
        if (!ServerVersionUtils.isVersionGreaterThanOrEqualTo("1.20.1")) {
            if (p.hasPermission("diatroll.troll.spawnmobs.camel")) {
                pickMobGui.addItem(camel);
            }
        }

        pickMobGui.setItem(53, back);


        for (int i = 0; i < 8; i++) {
            if (pickMobGui.getItem(i) == null) {
                ItemStack fillerglass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta fillerglass_meta = fillerglass.getItemMeta();
                fillerglass_meta.setDisplayName(" ");
                fillerglass.setItemMeta(fillerglass_meta);
                pickMobGui.setItem(i, fillerglass);
            }
        }
        p.openInventory(pickMobGui);

    }
    public static Inventory openCustomTrollsGui(Player p, Player target) {

        FileConfiguration config = plugin.getConfig();
        Inventory customTrollsGui = Bukkit.createInventory(p, 54, ChatColor.BLUE + "" + ChatColor.BOLD + "Troll Gui");



        for (int i = 0; i < 8; i++) {
            if (customTrollsGui.getItem(i) == null) {
                ItemStack fillerglass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta fillerglass_meta = fillerglass.getItemMeta();
                fillerglass_meta.setDisplayName(" ");
                fillerglass.setItemMeta(fillerglass_meta);
                customTrollsGui.setItem(i, fillerglass);
            }
        }

        return customTrollsGui;
    }


}

