package com.rewu.relics.Interfaces;

import org.bukkit.event.player.PlayerEggThrowEvent;

public interface EggRelic {
    
    /* Specifies a relic that takes the shape of an Egg and does something when thrown. */

    public void Throw(PlayerEggThrowEvent event);
}
