package com.rewu.relics.Abstract;

import org.bukkit.inventory.ItemStack;

import com.rewu.relics.Enums.RelicRarity;
import com.rewu.relics.Enums.RelicType;

public abstract class Relic {
    
    public abstract RelicType GetType();

    public abstract String GetName();

    public abstract String GetDesc();

    public abstract RelicRarity GetRarity();

    public abstract ItemStack GetItem();
}