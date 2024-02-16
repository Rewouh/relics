package com.rewu.relics;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class ContainersAPI {

    public static NamespacedKey getNamespacedKey(String key) {
        return new NamespacedKey(Main.getInstance(), key);
    }

    /* ------- GLOBAL ------- */

    public static boolean Has(PersistentDataHolder holder, PersistentDataType type, String key) {
        return holder.getPersistentDataContainer().has(getNamespacedKey(key), type);
    }

    public static Object Get(PersistentDataHolder holder, PersistentDataType type, String key) {
        return holder.getPersistentDataContainer().get(getNamespacedKey(key), type);
    }

    public static void Store(PersistentDataHolder holder, PersistentDataType type, String key, Object value) {
        holder.getPersistentDataContainer().set(getNamespacedKey(key), type, value);
    }

    public static void Store(PersistentDataHolder holder, PersistentDataType[] types, String[] keys, Object[] values) {
        PersistentDataContainer dataContainer = holder.getPersistentDataContainer();

        for (int i=0; i<keys.length; i++)
            dataContainer.set(getNamespacedKey(keys[i]), types[i], values[i]);
    }
}
