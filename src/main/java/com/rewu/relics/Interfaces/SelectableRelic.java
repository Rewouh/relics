package com.rewu.relics.Interfaces;

import org.bukkit.event.player.PlayerItemHeldEvent;

public interface SelectableRelic {

    /* Specifies a relic that does something when selected. */

    public void Selected(PlayerItemHeldEvent event);
}
