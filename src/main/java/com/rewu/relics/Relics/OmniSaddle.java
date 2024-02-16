package com.rewu.relics.Relics;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.rewu.relics.Main;
import com.rewu.relics.RelicUtils;
import com.rewu.relics.Abstract.Relic;
import com.rewu.relics.Enums.RelicRarity;
import com.rewu.relics.Enums.RelicType;
import com.rewu.relics.Interfaces.EntityInteractingRelic;

public class OmniSaddle extends Relic implements EntityInteractingRelic {
    
    @Override
    public RelicType GetType() {
        return RelicType.OMNI_SADDLE;
    }

    @Override
    public String GetName() {
        return "Omni Saddle";
    }

    @Override
    public String GetDesc() {
        return "§7If thy walks, thy can be mounted.";   
    }

    @Override
    public RelicRarity GetRarity() {
        return RelicRarity.FABLED;
    }

    @Override
    public ItemStack GetItem() {
        return RelicUtils.GenerateRelicItemBase(this, Material.SADDLE, 1);
    }

    public void InteractWithEntity(PlayerInteractEntityEvent event) {
    
        Player player = event.getPlayer(); // Getting relic's owner

        Entity entity = event.getRightClicked(); // Getting interacted entity

        entity.addPassenger(player); // Adding player as passenger of the entity 
    
        event.setCancelled(true); // Cancelling event 
    }
}
