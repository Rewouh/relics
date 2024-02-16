package com.rewu.relics.Events;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;

import com.rewu.relics.ContainersAPI;
import com.rewu.relics.RelicUtils;
import com.rewu.relics.Utils;
import com.rewu.relics.Abstract.Relic;
import com.rewu.relics.Interfaces.DamageableRelic;
import com.rewu.relics.Interfaces.DamagingRelic;
import com.rewu.relics.Interfaces.EditableBookRelic;
import com.rewu.relics.Interfaces.EggRelic;
import com.rewu.relics.Interfaces.EntityInteractingRelic;
import com.rewu.relics.Interfaces.ProjectingRelic;
import com.rewu.relics.Interfaces.SelectableRelic;

public class RelicsEffectsListener implements Listener {
    
    @EventHandler
    public void onBookEdited(PlayerEditBookEvent event) {

        Player player = event.getPlayer();

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() != Material.WRITABLE_BOOK)
            item = player.getInventory().getItemInOffHand();

        BookMeta meta = event.getNewBookMeta();

        if (!RelicUtils.IsRelic(meta))
            return;

        Relic relic = RelicUtils.GetRelic(meta);

        if (relic instanceof EditableBookRelic bookRelic)
            bookRelic.Edit(event, item);
    }

    @EventHandler
    public void onItemDamaged(PlayerItemDamageEvent event){

        ItemStack item = event.getItem(); // Getting item

        if (!RelicUtils.IsRelic(item)) // If it's not a relic skip
            return;

        Relic relic = RelicUtils.GetRelic(item); // Getting relic 

        if (!(relic instanceof DamageableRelic)) // Checking if it can be damaged
            return;

        ((DamageableRelic) relic).Damaged(event); // Throwing related method
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {

        ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot()); // Retrieving selected item

        if (item == null || !RelicUtils.IsRelic(item)) // If it's empty or not a relic skip 
            return;

        Relic relic = RelicUtils.GetRelic(item); // Getting relic 

        if (!(relic instanceof SelectableRelic)) // Checking if it can damage entities
            return;

        ((SelectableRelic) relic).Selected(event); // Throwing related method
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        ItemStack item = null;
        Relic relic = null;

        if (event.getDamager() instanceof Player player)  {
            item = player.getInventory().getItemInMainHand(); // Grabbing item in main hand

            if (item.getType().isAir() || !RelicUtils.IsRelic(item)) // If it's empty or not a relic skip 
                return;

            relic = RelicUtils.GetRelic(item);
        } else if (event.getDamager() instanceof Projectile projectile) {
            if (!RelicUtils.IsRelic(projectile))
                return;

            relic = RelicUtils.GetRelic(projectile);
        } else 
            return;

        // If this relic does something when damaging entities, calling related method
        if (relic instanceof DamagingRelic damagingRelic) {
            boolean killed = (event.getEntity() instanceof LivingEntity victim ? victim.getHealth() <= event.getFinalDamage() : false);

            damagingRelic.Damage(event, item, killed);
        }
    }

    @EventHandler
    public void onPlayerInteractWithEntity(PlayerInteractEntityEvent event) {

        Player player = event.getPlayer(); // Getting player

        /* Grabbing item in main hand */
        ItemStack item = player.getInventory().getItem(event.getHand()); 

        if (item.getType().isAir() || !RelicUtils.IsRelic(item)) // If it's empty or not a relic skip
            return;

        Relic relic = RelicUtils.GetRelic(item); // Getting relic 

        if (!(relic instanceof EntityInteractingRelic)) // Checking if it can interact with entities
            return;

        // If it can, calling related method
        ((EntityInteractingRelic) relic).InteractWithEntity(event);
    }

    @EventHandler
    public void onEggThrow(PlayerEggThrowEvent event) {

        Player player = event.getPlayer(); // Getting player

        /* Grabs the thrown egg's itemstack */
        ItemStack item = player.getInventory().getItemInMainHand(); 

        if (item.getType() != Material.EGG)
            item = player.getInventory().getItemInOffHand();

        if (!RelicUtils.IsRelic(item)) // If it's not a relic skip
            return;

        EggRelic eggRelic = (EggRelic) RelicUtils.GetRelic(item); // Grabbing relic 

        eggRelic.Throw(event); // Calling egg throw relic event 
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event){
        

        Projectile projectile = event.getEntity(); // Grabbing projectile

        if (!(projectile.getShooter() instanceof Player)) // If not launched by a player skip
            return;

        Player player = (Player) projectile.getShooter(); // Gets the player 

        /* Grabs the item responsible for the projectile  */
        ItemStack item = player.getInventory().getItemInMainHand(); 

        if (!Utils.IsThrowable(item))
            item = player.getInventory().getItemInOffHand();

        if (!RelicUtils.IsRelic(item)) // If it's not a relic skip
            return;

        Relic relic = RelicUtils.GetRelic(item); // Grabbing relic 
        
        // Storing relic type in the projectile
        ContainersAPI.Store(projectile, PersistentDataType.STRING, "relic_type", relic.GetType().toString());

        ((ProjectingRelic) relic).Launch(event); // Calling projectable relic event 
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event){
        
        Projectile projectile = event.getEntity();

        if (!(projectile.getShooter() instanceof Player))
            return;

        if (!RelicUtils.IsRelic(projectile))
            return;

        ProjectingRelic relic = (ProjectingRelic) RelicUtils.GetRelic(projectile);

        relic.Hit(event);
    }
    
}
