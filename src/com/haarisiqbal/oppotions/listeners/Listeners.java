package com.haarisiqbal.oppotions.listeners;

import java.util.ArrayList;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class Listeners implements Listener {
  
  // OP Potion (default overpowered potion)
  private ItemStack opPotion = new ItemStack(Material.SPLASH_POTION);
  
  // Lightning Potion Info
  private final String lightningPotionName = "Lighting in a Bottle";
  private final String lightningPotionLore = "Throw this for a bad time...";
  private final Color lightningPotionColour = Color.WHITE;
  
  // TNT Potion Info
  private final String tntPotionName = "Holy Explosive Bottle";
  private final String tntPotionLore = "A present from the heavens above";
  private final Color tntPotionColour = Color.GREEN;
  
  // UnGodly-562 TNT Potion Info
  private final String obsidianBusterPotionName = "UnGodly-562 Obsidian Buster Bottle";
  private final String obsidianBusterPotionLore = "Burn this in lava or you will break the game.";
  private final Color obsidianBusterPotionColour = Color.BLACK;
  
  // Configure Lightning Potion.
  public void lightningPotionCon() {
    PotionMeta meta = (PotionMeta) opPotion.getItemMeta();
    ArrayList<String> lore = new ArrayList<String>();
    
    meta.setDisplayName(lightningPotionName);
    lore.add(lightningPotionLore);
    meta.setLore(lore);
    meta.setColor(lightningPotionColour);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    
    opPotion.setItemMeta(meta);
  }
  
  // Configure TNT Potion.
  public void tntPotionCon() {
    PotionMeta meta = (PotionMeta) opPotion.getItemMeta();
    ArrayList<String> lore = new ArrayList<String>();
    
    meta.setDisplayName(tntPotionName);
    lore.add(tntPotionLore);
    meta.setLore(lore);
    meta.setColor(tntPotionColour);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    
    opPotion.setItemMeta(meta);
  }
  
  // Configure Obsidian Buster Potion.
  public void witherTNTPotionCon() {
    PotionMeta meta = (PotionMeta) opPotion.getItemMeta();
    ArrayList<String> lore = new ArrayList<String>();
    
    meta.setDisplayName(obsidianBusterPotionName);
    lore.add(obsidianBusterPotionLore);
    meta.setLore(lore);
    meta.setColor(obsidianBusterPotionColour);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    
    opPotion.setItemMeta(meta);
  }
  
  // Making Potion obtainable via mob drop.
  @EventHandler
  public void deathDrop(EntityDeathEvent deathEvent) {
    LivingEntity e = deathEvent.getEntity();
    
    // What (configuration) to drop, depending on type of entity
    switch (e.getType()) {
      case CREEPER:
        deathEvent.getDrops().clear(); // stop the entity default drop
        tntPotionCon();
        for ( int i = 0; i < 2; i++) {e.getWorld().dropItem(e.getLocation(), opPotion); }
        break;
        
      case ZOMBIE:
        deathEvent.getDrops().clear(); 
        lightningPotionCon();
        for ( int i = 0; i < 2; i++) {e.getWorld().dropItem(e.getLocation(), opPotion); }
        break;
      
      case WITHER:
        deathEvent.getDrops().clear(); 
        witherTNTPotionCon();
        e.getWorld().dropItem(e.getLocation(), opPotion);
        break;
        
      default:
        break;
    }
  }
  
  // Potion functionality triggered for every splash potion thrown.
  @EventHandler
  public void potionThrow(PotionSplashEvent proHit) {
    
    // Checking if splash potion has lore (normal potion will return null).
    ThrownPotion t = proHit.getPotion();
    if (t.getItem().getItemMeta().getLore() == null) { return; }
    
    String loreCase = t.getItem().getItemMeta().getLore().get(0);
    
    // Effects, based on type (lore) of potion.
    switch(loreCase) {
      case lightningPotionLore: 
        proHit.getEntity().getWorld().createExplosion(proHit.getEntity().getLocation(), 2f);
        for (int i = 0; i < 10; i++) {
          proHit.getEntity().getWorld().strikeLightning(proHit.getEntity().getLocation()); // strike lightning.
        }
        break;
      
      case tntPotionLore:
        for (int i = 0; i < 10; i++) {
          Entity tnt = proHit.getEntity().getWorld().spawn(proHit.getEntity().getLocation().add(0, 0, 0), TNTPrimed.class); // spawn TNT in world + assign variable.
          ((TNTPrimed) tnt).setFuseTicks(40); // faster timer (2 seconds, half the time of normal TNT).
        }
        break;
      
      case obsidianBusterPotionLore:
        for (int i = 0; i < 562; i++) {
          Entity tnt = proHit.getEntity().getWorld().spawn(proHit.getEntity().getLocation(), TNTPrimed.class); // spawn far more TNT
          ((TNTPrimed) tnt).setFuseTicks(200); // 10 second timer.
        }
        break;
        
      default:
        break;
    }
    
    // testing cast.
    //proHit.getEntity().getWorld().dropItem(proHit.getEntity().getLocation(), t.getItem());
  }
  
}

