package com.rewu.relics.Relics;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.rewu.relics.Main;
import com.rewu.relics.RelicUtils;
import com.rewu.relics.Abstract.Relic;
import com.rewu.relics.Enums.RelicRarity;
import com.rewu.relics.Enums.RelicType;
import com.rewu.relics.Interfaces.EditableBookRelic;


public class DeathBook extends Relic implements EditableBookRelic {

    private PotionEffect darknessEffect = new PotionEffect(PotionEffectType.DARKNESS, 40 * 20, 0);
    
    private HashMap<Player, BukkitTask> condemnedPlayers = new HashMap<Player, BukkitTask>();

    @Override
    public RelicType GetType() {
        return RelicType.DEATH_BOOK;
    }

    @Override
    public String GetName() {
        return "Death Book";
    }

    @Override
    public String GetDesc() {
        return "§7The human whose name is written §7in this book shall die.";   
    }

    @Override
    public RelicRarity GetRarity() {
        return RelicRarity.PRIMORDIAL;
    }

    @Override
    public ItemStack GetItem() {
        return RelicUtils.GenerateRelicItemBase(this, Material.WRITABLE_BOOK, 1);
    }

    @Override
    public void Edit(PlayerEditBookEvent event, ItemStack item) {
        
        BookMeta meta = event.getNewBookMeta();

        Player target = Bukkit.getPlayer(meta.getPage(1));

        if (target == null) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();

        /* Removing book from player's inventory */
        player.getInventory().remove(item);

        player.sendMessage("§0May it's fate be sealed.");

        target.addPotionEffect(darknessEffect);

        /* Playing heartbeat sound to target for 40 seconds then killing it. */
        int[] seconds = new int[] {0};
        BukkitTask task = new BukkitRunnable()
        {
            public void run()
            {
                seconds[0] += 1;
        
                if (!target.isOnline())
                    return;

                Location loc = target.getLocation();

                loc.getWorld().playSound(loc, Sound.ENTITY_WARDEN_NEARBY_CLOSE, SoundCategory.HOSTILE, 100 * ((float) seconds[0] / 40f), 0);

                if (seconds[0] == 40) {
                    target.setHealth(0);

                    condemnedPlayers.remove(target);

                    cancel();
                }
            }

        }.runTaskTimer(Main.getInstance(), 0, 20);

        condemnedPlayers.put(target, task);
    }
    
}
