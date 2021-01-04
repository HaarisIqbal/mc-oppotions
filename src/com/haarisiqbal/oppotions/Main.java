package com.haarisiqbal.oppotions;

import org.bukkit.plugin.java.JavaPlugin;
import com.haarisiqbal.oppotions.listeners.Listeners;

public class Main extends JavaPlugin {

  private Main instance;
  
  public Main getInstance() {
    return instance;
  }
  
  @Override
  public void onEnable() {
    instance = this;
    getServer().getPluginManager().registerEvents(new Listeners(), this);
  }
  
  @Override
  public void onDisable() {
    instance = null;
  }
  
}
