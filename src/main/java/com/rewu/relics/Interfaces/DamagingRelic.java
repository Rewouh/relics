package com.rewu.relics.Interfaces;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public interface DamagingRelic {

    /* Specifies a relic that does something when inflicting damages. */

    public void Damage(EntityDamageByEntityEvent event, ItemStack item, boolean killed);
}
