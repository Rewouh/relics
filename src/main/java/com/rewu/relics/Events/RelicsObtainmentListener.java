package com.rewu.relics.Events;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.world.LootGenerateEvent;

import com.rewu.relics.Main;
import com.rewu.relics.RelicUtils;
import com.rewu.relics.RelicsObtainment;
import com.rewu.relics.Enums.RelicType;

public class RelicsObtainmentListener implements Listener {
    
    @EventHandler
    public void onEntityDropItem(EntityDropItemEvent event){

        EntityType entityType = event.getEntityType(); // Grabbing entity type

        RelicType rolledRelicType = RelicsObtainment.RollDrop(entityType); // Rolling possible relic type for this entity 

        Main.getInstance().getLogger().info("Rolled for drop from " + entityType.toString() + " and got " + rolledRelicType.toString());

        if (rolledRelicType == RelicType.NONE) // Type not found or rolled nothing 
            return;

        // Relic rolled, replacing event item by the relic 
        event.getItemDrop().setItemStack(RelicUtils.GetItemFromRelicType(rolledRelicType)); 
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        EntityType entityType = event.getEntityType(); // Grabbing entity type

        RelicType rolledRelicType = RelicsObtainment.RollLoot(entityType); // Rolling possible relic type for this entity 

        Main.getInstance().getLogger().info("Rolled for loot from " + entityType.toString() + " and got " + rolledRelicType.toString());

        if (rolledRelicType == RelicType.NONE) // Type not found or rolled nothing 
            return;

        // Relic rolled, adding it to the drops
        event.getDrops().add(RelicUtils.GetItemFromRelicType(rolledRelicType));
    }

    @EventHandler
    public void onLootGenerate(LootGenerateEvent event) {

        NamespacedKey structureKey = event.getLootTable().getKey(); // Grabbing structure key (identifier)

        RelicType rolledRelicType = RelicsObtainment.RollStructure(structureKey); // Rolling possible relic type for this key

        Main.getInstance().getLogger().info("Rolled for structure from " + structureKey + " and got " + rolledRelicType.toString());

        if (rolledRelicType == RelicType.NONE) // Key not found or rolled nothing 
            return;

        // Relic rolled, adding it to the chest loot
        event.getLoot().add(RelicUtils.GetItemFromRelicType(rolledRelicType)); 
    }

}
