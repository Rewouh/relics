package com.rewu.relics.Relics;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rewu.relics.Main;
import com.rewu.relics.RelicUtils;
import com.rewu.relics.Utils;
import com.rewu.relics.Abstract.Relic;
import com.rewu.relics.Enums.RelicRarity;
import com.rewu.relics.Enums.RelicType;
import com.rewu.relics.Interfaces.DamagingRelic;
import com.rewu.relics.Interfaces.ProjectingRelic;

public class Fenthras extends Relic implements ProjectingRelic, DamagingRelic {

    private PotionEffect[] effectsOnHit = new PotionEffect[] {new PotionEffect(PotionEffectType.SLOW, 5 * 20, 1), new PotionEffect(PotionEffectType.POISON, 5 * 20, 1)}; 

    @Override
    public RelicType GetType() {
        return RelicType.FENTHRAS;
    }

    @Override
    public String GetName() {
        return "Fenthras";
    }

    @Override
    public String GetDesc() {
        return "§7From death comes life.";   
    }

    @Override
    public RelicRarity GetRarity() {
        return RelicRarity.ANCIENT;
    }

    @Override
    public ItemStack GetItem() {
        return RelicUtils.GenerateRelicItemBase(this, Material.BOW, 2);
    }

    public void Launch(ProjectileLaunchEvent event) { }

    public void Hit(ProjectileHitEvent event) { }

    public void Damage(EntityDamageByEntityEvent event, ItemStack item, boolean killed) {

        if (!(event.getEntity() instanceof LivingEntity damaged))
            return;

        for (PotionEffect effect : effectsOnHit)
            damaged.addPotionEffect(effect);

        if (!killed)
            return;

        Location loc = damaged.getLocation();
        Block b = loc.getWorld().getBlockAt(loc);

        if (b.getType() == Material.AIR)
            b.setType(Utils.GetRandomSapling());
    }
}
