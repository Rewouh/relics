package com.rewu.relics.Interfaces;

import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public interface ProjectingRelic {
    
    /* Specifies a relic that does something when launching projectiles or when those hit something. */

    public void Launch(ProjectileLaunchEvent event);

    public void Hit(ProjectileHitEvent event);
}
