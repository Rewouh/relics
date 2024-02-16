package com.rewu.relics.Relics;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.rewu.relics.ContainersAPI;
import com.rewu.relics.RelicUtils;
import com.rewu.relics.Abstract.Relic;
import com.rewu.relics.Enums.RelicRarity;
import com.rewu.relics.Enums.RelicType;
import com.rewu.relics.Interfaces.DamageableRelic;
import com.rewu.relics.Interfaces.DamagingRelic;
import com.rewu.relics.Interfaces.SelectableRelic;

public class CravenEdge extends Relic implements DamagingRelic, SelectableRelic, DamageableRelic {
    
    private String[] attackedPhrases = new String[] {
        "§4§lBloodbath §fincoming..."
    };

    private String[] killedPhrases = new String[] {
        "YEEEEEEEEES, I §4§l WANT MORE §f!",
        "§4§lBLOOOD§f, §4§lBLOOOOD§f, §4§lBLOOOOOOOOD§f!"
    };

    private String[] remindPhrases = new String[] {
        "§4§lFeed me§f, or I'll feed on §4§lYOU§f! ",
        "§4§lHunger§f...",
        "I §4§lthirsts§f...",
        "Find me §4§lBLOOOOOD§f!"
    };

    @Override
    public RelicType GetType() {
        return RelicType.CRAVEN_EDGE;
    }

    @Override
    public String GetName() {
        return "Craven Edge";
    }

    @Override
    public String GetDesc() {
        return "§cCurse of Thirst";   
    }

    @Override
    public RelicRarity GetRarity() {
        return RelicRarity.ANCIENT;
    }

    @Override
    public ItemStack GetItem() {
        
        ItemStack item = RelicUtils.GenerateRelicItemBase(this, Material.DIAMOND_SWORD, 1);

        ItemMeta meta = item.getItemMeta();

        ContainersAPI.Store(meta, PersistentDataType.INTEGER, "Kills", 0);

        item.setItemMeta(meta);

        return item;
    }

    @Override
    public void Damage(EntityDamageByEntityEvent event, ItemStack item, boolean killed) {

        Player player = (Player) event.getDamager();

        ItemMeta meta = item.getItemMeta(); // Grabbing item meta

        // Grabbing Kills from the sword data container
        int killsNumber = (int) ContainersAPI.Get(meta, PersistentDataType.INTEGER, "Kills");

        // Calculating damage that will be added
        double addedDamage = Math.min(killsNumber * 0.01, 15);

        // Setting new damage
        event.setDamage(event.getDamage() + addedDamage);

        if (!(event.getEntity() instanceof LivingEntity damaged)) // If target not living skip
            return;

        Random rand = new Random(); // Init random

        if (!killed) { // If target doesn't die from this hit

            // If it doesn't, rolling for attack-that-didnt-kill phrase
            if (rand.nextInt(0, 10) == 5)
                player.sendMessage(attackedPhrases[rand.nextInt(0, attackedPhrases.length)]);

        } else {

            // If it does, storing new values for Kills & Last Kill
            ContainersAPI.Store(meta, PersistentDataType.INTEGER, "Kills", killsNumber + 1);
    
            item.setItemMeta(meta); // Setting meta

            // And rolling for attack-that-did-kill phrase
            if (rand.nextInt(0, 10) == 5)
                player.sendMessage(killedPhrases[rand.nextInt(0, killedPhrases.length)]);
        }
    }

    @Override
    public void Selected(PlayerItemHeldEvent event) {
        
        Player player = (Player) event.getPlayer();

        ItemMeta meta = player.getInventory().getItem(event.getNewSlot()).getItemMeta();
        
        // Retrieving number of kills 
        int killsNumber = (int) ContainersAPI.Get(meta, PersistentDataType.INTEGER, "Kills"); 

        Random rand = new Random(); // Init random

        // Rolling, if player has killed more than 1000 entities it will always happen
        if (killsNumber < 1000 && rand.nextInt(0, 1000 - killsNumber) != 1) 
            return;

        double damage = (double) killsNumber / 100;

        player.sendMessage(remindPhrases[rand.nextInt(0, remindPhrases.length)]);
        player.damage(damage);
    }

    @Override
    public void Damaged(PlayerItemDamageEvent event) {
        event.setCancelled(true);        
    }
}
