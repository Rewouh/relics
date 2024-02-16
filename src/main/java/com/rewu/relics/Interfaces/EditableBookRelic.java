package com.rewu.relics.Interfaces;

import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemStack;

public interface EditableBookRelic {
    
    /* Specifies a relic that takes the shape of a book and does something when edited. */

    public void Edit(PlayerEditBookEvent event, ItemStack item);
}
