package com.rewu.relics;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import com.rewu.relics.Abstract.Relic;
import com.rewu.relics.Enums.RelicType;

public class RelicUtils {
    
    // Given a PersistentDataHolder, returns a boolean indicating wether it's a relic or not
    public static boolean IsRelic(PersistentDataHolder holder){ return ContainersAPI.Has(holder, PersistentDataType.STRING, "relic_type"); }

    // Given a PersistentDataHolder assumed to be a relic, returns the RelicType associated to this relic
    public static RelicType GetRelicType(PersistentDataHolder holder) { return RelicType.valueOf((String) ContainersAPI.Get(holder, PersistentDataType.STRING, "relic_type")); }

    // Given a PersistentDataHolder assumed to be a relic, returns the Relic associated to this relic
    public static Relic GetRelic(PersistentDataHolder holder) { return RelicsLibrary.GetRelicFromType(GetRelicType(holder)); }

    // Given an ItemStack, returns a boolean indicating wether it's a relic or not
    public static boolean IsRelic(ItemStack item) { return IsRelic(item.getItemMeta()); }

    // Given an ItemStack assumed to be a relic, returns the RelicType associated to this relic
    public static RelicType GetRelicType(ItemStack item) { return GetRelicType(item.getItemMeta()); }
    
    // Given an ItemStack assumed to be a relic, returns the Relic associated to this relic
    public static Relic GetRelic(ItemStack item) { return GetRelic(item.getItemMeta()); }

    // Given a RelicType, returns the ItemStack shaping this relic
    public static ItemStack GetItemFromRelicType(RelicType relicType) { return RelicsLibrary.GetRelicFromType(relicType).GetItem(); }

    /* Generates a partially configured ItemStack for the item shaping a relic  */
    public static ItemStack GenerateRelicItemBase(Relic relic, Material material, int modelId) {
        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(RelicUtils.GetColoredName(relic));
        meta.setCustomModelData(modelId);
        meta.setLore(new ArrayList<String>(Arrays.asList(relic.GetDesc().split("(?=§7)"))));
    
        ContainersAPI.Store(meta, PersistentDataType.STRING, "relic_type", relic.GetType().toString());

        item.setItemMeta(meta);

        return item;
    }

    /* Given a relic, returns its name properly colored (according to its rarity) */
    public static String GetColoredName(Relic relic){
        
        String color;

        switch (relic.GetRarity()) {
            case ANCIENT:
                color = "§8";
                break;
            case FABLED:
                color = "§6";
                break;
            case PRIMORDIAL:
                color = "§0";
                break;
            default:
                color = "§f";
                break;
        }

        return color + relic.GetName();
    }

}
