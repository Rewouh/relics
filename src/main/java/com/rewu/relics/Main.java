package com.rewu.relics;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.rewu.relics.Commands.CmdRelicsGet;
import com.rewu.relics.Events.RelicsEffectsListener;
import com.rewu.relics.Events.RelicsObtainmentListener;

/*
 * enderboss java plugin
 */
public class Main extends JavaPlugin
{
  /* Private */

  private static Main instance;

  public static Main getInstance() {return instance;}

  public void onEnable()
  {
    instance = this; // Setting instance

    RelicsLibrary.registerRelics(); // Registering every relics 

    RelicsObtainment.registerMaps(); // Registering relics obtainment maps 

    registerCommands(); // Registering commands 

    // Registering event listeners
    PluginManager pluginManager = getServer().getPluginManager();
    pluginManager.registerEvents(new RelicsEffectsListener(), this);
    pluginManager.registerEvents(new RelicsObtainmentListener(), this);
  }

  private void registerCommands() {
      getCommand("rget").setExecutor(new CmdRelicsGet());
  }

  public void onDisable()
  {
    
  }
}
