package com.rewu.relics;

import java.util.HashMap;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.loot.LootTables;

import com.rewu.relics.Enums.RelicType;

public class RelicsObtainment {
    
    /* Rolled when generating the content of a chest inside a structure. */
    private static HashMap<NamespacedKey, RandomCollection<RelicType>> structuresMaps = new HashMap<NamespacedKey, RandomCollection<RelicType>>();

    /* Rolled when an entity is killed. */
    private static HashMap<EntityType, RandomCollection<RelicType>> lootsMaps = new HashMap<EntityType, RandomCollection<RelicType>>(); 

    /* Rolled when an entity creates an item. */
    private static HashMap<EntityType, RandomCollection<RelicType>> dropsMaps = new HashMap<EntityType, RandomCollection<RelicType>>(); 

    public static void registerMaps() {

        /* ----- Structures ----- */

        structuresMaps.put(LootTables.END_CITY_TREASURE.getKey(), new RandomCollection<RelicType>()
        .add(29, RelicType.NONE)
        .add(1, RelicType.ENDER_BOW));

        structuresMaps.put(LootTables.NETHER_BRIDGE.getKey(), new RandomCollection<RelicType>()
        .add(199, RelicType.NONE)
        .add(1, RelicType.CRAVEN_EDGE));

        structuresMaps.put(LootTables.JUNGLE_TEMPLE.getKey(), new RandomCollection<RelicType>()
        .add(29, RelicType.NONE)
        .add(1, RelicType.FENTHRAS));

        structuresMaps.put(LootTables.ANCIENT_CITY.getKey(), new RandomCollection<RelicType>()
        .add(999, RelicType.NONE)
        .add(1, RelicType.DEATH_BOOK));

        /* ----- Loots ----- */

        lootsMaps.put(EntityType.WANDERING_TRADER, new RandomCollection<RelicType>()
        .add(1, RelicType.NONE));

        /* ----- Drops ----- */

        dropsMaps.put(EntityType.CHICKEN, new RandomCollection<RelicType>()
        .add(999, RelicType.NONE)
        .add(1, RelicType.GOLDEN_EGG));

    }

    /* Roll for structure chest  */
    public static RelicType RollStructure(NamespacedKey structureKey) { 
        if (!structuresMaps.containsKey(structureKey))
            return RelicType.NONE;

        return structuresMaps.get(structureKey).next(); 
    }

    /* Roll for entity loot  */
    public static RelicType RollLoot(EntityType entityType) { 
        if (!lootsMaps.containsKey(entityType))
            return RelicType.NONE;

        return lootsMaps.get(entityType).next();
     }

     /* Roll for entity drop */
    public static RelicType RollDrop(EntityType entityType) { 
        if (!dropsMaps.containsKey(entityType))
            return RelicType.NONE;

        return dropsMaps.get(entityType).next(); 
    }

}
