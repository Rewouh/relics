package com.rewu.relics.Interfaces;

import org.bukkit.event.player.PlayerItemDamageEvent;

public interface DamageableRelic {
    
    /* Specifies a relic that does something when losing durability. */

    public void Damaged(PlayerItemDamageEvent event);
}
