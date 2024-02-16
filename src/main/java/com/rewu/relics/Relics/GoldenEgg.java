package com.rewu.relics.Relics;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.rewu.relics.Main;
import com.rewu.relics.RelicUtils;
import com.rewu.relics.Utils;
import com.rewu.relics.Abstract.Relic;
import com.rewu.relics.Enums.RelicRarity;
import com.rewu.relics.Enums.RelicType;
import com.rewu.relics.Interfaces.EggRelic;
import com.rewu.relics.Interfaces.ProjectingRelic;


public class GoldenEgg extends Relic implements ProjectingRelic, EggRelic {

    @Override
    public RelicType GetType() {
        return RelicType.GOLDEN_EGG;
    }

    @Override
    public String GetName() {
        return "Golden Egg";
    }

    @Override
    public String GetDesc() {
        return "§7It is said to make its fortunate discoverer§7wealthy beyond his wildest dreams.";   
    }

    @Override
    public RelicRarity GetRarity() {
        return RelicRarity.FABLED;
    }

    @Override
    public ItemStack GetItem() {
        return RelicUtils.GenerateRelicItemBase(this, Material.EGG, 1);
    }

    public void Launch(ProjectileLaunchEvent event) {}

    public void Throw(PlayerEggThrowEvent event) {
        event.setHatching(false);
    }

    @Override
    public void Hit(ProjectileHitEvent event) {

        Location origin = event.getHitBlock() != null ? event.getHitBlock().getLocation() : event.getHitEntity().getLocation(); // Grabbing hit location

        World world = origin.getWorld(); // Getting world 

        ArrayList<Block> blocks = Utils.GetBlocksInSphereRadius(world.getBlockAt(origin), 3); // Getting all blocks in a 3x3 sphere radius

        blocks.removeIf(b -> b.isPassable()); // Removing all blocks that are passable (air, liquids...)

        Random rand = new Random(); // Init random

        /* Transforming blocks to gold (5 every 2 ticks) */
        new BukkitRunnable()
        {
            public void run()
            {
                int size = blocks.size(); // Remaining number of blocks

                if (size == 0) { // If no more blocks then stop
                    cancel();
                    return;
                }

                ArrayList<Block> curr = new ArrayList<Block>(); // Will contain the blocks that will be transformed this turn

                // Getting 5 random blocks
                for (int i = 0; i < Math.min(5, size); i++) 
                    curr.add(blocks.remove(rand.nextInt(0, blocks.size())));

                // Transforming them to gold
                for (Block b : curr)
                    b.setType(Material.GOLD_BLOCK);

                // Playing a egg laying sound 
                world.playSound(origin, Sound.ENTITY_CHICKEN_EGG, SoundCategory.AMBIENT, 100, 0);
            }

        }.runTaskTimer(Main.getInstance(), 0, 2);
    }
    
}
