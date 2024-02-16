package com.rewu.relics.Interfaces;

import org.bukkit.event.player.PlayerInteractEntityEvent;

public interface EntityInteractingRelic {
    
    /* Specifies a relic that does something when interacting with entities. */

    public void InteractWithEntity(PlayerInteractEntityEvent event);
}
