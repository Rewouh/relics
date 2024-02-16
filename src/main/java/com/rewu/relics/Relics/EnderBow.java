package com.rewu.relics.Relics;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import com.rewu.relics.RelicUtils;
import com.rewu.relics.Abstract.Relic;
import com.rewu.relics.Enums.RelicRarity;
import com.rewu.relics.Enums.RelicType;
import com.rewu.relics.Interfaces.ProjectingRelic;

public class EnderBow extends Relic implements ProjectingRelic {

    @Override
    public RelicType GetType() {
        return RelicType.ENDER_BOW;
    }

    @Override
    public String GetName() {
        return "Ender Bow";
    }

    @Override
    public String GetDesc() {
        return "§7It wields the power to travel to unknown lands.";   
    }

    @Override
    public RelicRarity GetRarity() {
        return RelicRarity.OLD;
    }

    @Override
    public ItemStack GetItem() {
        return RelicUtils.GenerateRelicItemBase(this, Material.BOW, 1);
    }

    public void Launch(ProjectileLaunchEvent event) {}

    public void Hit(ProjectileHitEvent event) {

        Projectile projectile = event.getEntity(); // Grabbing the arrow

        Player player = (Player) projectile.getShooter(); // Grabbing the shooter 

        Location loc = player.getLocation(); // Current player location
        Location dest = projectile.getLocation(); // Destination location (arrow hitted point)

        dest.setY((int) dest.getY()); // Flooring Y so the user doesn't fly when arriving

        /* Keeping player's original yaw & pitch to smooth the teleportation */
        dest.setYaw(loc.getYaw());
        dest.setPitch(loc.getPitch());

        projectile.remove(); // Destroying arrow

        player.teleport(dest); // Teleporting player
 
        // Playing teleportation sound 
        dest.getWorld().playSound(dest, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 100, 0);
    }
}
