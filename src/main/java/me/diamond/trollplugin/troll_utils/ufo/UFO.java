package me.diamond.trollplugin.troll_utils.ufo;

import net.minecraft.server.level.ServerPlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class UFO {


    public Player getP() {
        return p;
    }

    public void setP(Player p) {
        this.p = p;
    }

    private Player p;

    public ServerPlayer getNpc() {
        return npc;
    }

    public void setNpc(ServerPlayer npc) {
        this.npc = npc;
    }

    private ServerPlayer npc;
    private ArrayList<Block> blocks;
    public ArrayList<Block> getBlocks() {
        return blocks;
    }
    public ArrayList<Block> getLevers() {
        return levers;
    }

    public void setLevers(ArrayList<Block> levers) {
        this.levers = levers;
    }

    public ArrayList<Block> getGlass_Blocks() {
        return glass_Blocks;
    }

    public void setGlass_Blocks(ArrayList<Block> glass_Blocks) {
        this.glass_Blocks = glass_Blocks;
    }

    public Block getTrapdoor() {
        return trapdoor;
    }

    public void setTrapdoor(Block trapdoor) {
        this.trapdoor = trapdoor;
    }

    public Block getComparator() {
        return comparator;
    }

    public void setComparator(Block comparator) {
        this.comparator = comparator;
    }

    public ArrayList<Block> getSmooth_Stone_Blocks() {
        return smooth_Stone_Blocks;
    }

    public void setSmooth_Stone_Blocks(ArrayList<Block> smooth_Stone_Blocks) {
        this.smooth_Stone_Blocks = smooth_Stone_Blocks;
    }

    private ArrayList<Block> levers;
    private ArrayList<Block> glass_Blocks;
    private Block trapdoor;
    private Block comparator;
    private ArrayList<Block> smooth_Stone_Blocks;

    public UFO(Player p, ArrayList<Block> levers, ArrayList<Block> glassBlocks, Block trapdoor, Block comparator, ArrayList<Block> smoothStoneBlocks) {
        this.p = p;
        this.levers = levers;
        this.glass_Blocks = glassBlocks;
        this.trapdoor = trapdoor;
        this.comparator = comparator;
        this.smooth_Stone_Blocks = smoothStoneBlocks;
        blocks.addAll(levers);
        blocks.addAll(glassBlocks);
        blocks.addAll(smoothStoneBlocks);

    }
}
