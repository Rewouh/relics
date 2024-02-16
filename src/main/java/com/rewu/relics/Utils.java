package com.rewu.relics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class Utils {
    
    private static ArrayList<Material> throwables = new ArrayList<>(Arrays.asList(Material.EGG, Material.BOW, Material.ENDER_PEARL, Material.CROSSBOW, Material.FISHING_ROD, Material.FIREWORK_ROCKET, Material.SNOWBALL, Material.SPLASH_POTION, Material.LINGERING_POTION, Material.ENDER_EYE, Material.EXPERIENCE_BOTTLE));

    private static Material[] saplings = new Material[] { Material.OAK_SAPLING, Material.BIRCH_SAPLING, Material.SPRUCE_SAPLING, Material.ACACIA_SAPLING, Material.BAMBOO_SAPLING, Material.JUNGLE_SAPLING };

    public static boolean IsThrowable(Material material) { return throwables.contains(material); }

    public static boolean IsThrowable(ItemStack item){ return throwables.contains(item.getType()); }

    public static Material GetRandomSapling() { return saplings[new Random().nextInt(0, saplings.length)]; }

    public static ArrayList<Block> GetBlocksInSphereRadius(Block origin, int radius) {

        World world = origin.getWorld();

        ArrayList<Block> blocks = new ArrayList<Block>();

        for (int Y = -radius; Y < radius; Y++)
        for (int X = -radius; X < radius; X++)
        for (int Z = -radius; Z < radius; Z++)
            if (Math.sqrt((X * X) + (Y * Y) + (Z * Z)) <= radius) 
                blocks.add(world.getBlockAt(X + origin.getX(), Y + origin.getY(), Z + origin.getZ()));

        return blocks;
    }

}


