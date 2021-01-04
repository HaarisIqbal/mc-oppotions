package com.haarisiqbal.oppotions.listeners;

import java.util.ArrayList;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.entity.Item;

@SuppressWarnings("unused")
public class Listeners implements Listener {
  
  // Lightning Potion
  private ItemStack lightningPotion = new ItemStack(Material.SPLASH_POTION);
  private final String lName = "Lighting in a Bottle";
  private final String lLore = "Throw this for a bad time...";
  
  // TNT Potion
  private ItemStack tntPotion = new ItemStack(Material.SPLASH_POTION);
  private final String tName = "Holy Explosive Bottle";
  private final String tLore = "A present from the heavens above";
  
  // Wither TNT Potion
  private ItemStack witherTNTPotion = new ItemStack(Material.SPLASH_POTION);
  private final String wName = "UnGodly Obsidian Buster Bottle";
  private final String wLore = "burn this in lava or die.";
  
  // configure custom lightning potion drop.
  public void lightningPotionCon() {
    PotionMeta meta = (PotionMeta) lightningPotion.getItemMeta();
    ArrayList<String> lore = new ArrayList<String>();
    
    meta.setDisplayName(lName);
    lore.add(lLore);
    meta.setLore(lore);
    meta.setColor(Color.WHITE);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    
    lightningPotion.setItemMeta(meta);
  }
  
  public void tntPotionCon() {
    PotionMeta meta = (PotionMeta) tntPotion.getItemMeta();
    ArrayList<String> lore = new ArrayList<String>();
    
    meta.setDisplayName(tName);
    lore.add(tLore);
    meta.setLore(lore);
    meta.setColor(Color.GREEN);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    
    tntPotion.setItemMeta(meta);
  }
  
  public void witherTNTPotionCon() {
    PotionMeta meta = (PotionMeta) witherTNTPotion.getItemMeta();
    ArrayList<String> lore = new ArrayList<String>();
    
    meta.setDisplayName(wName);
    lore.add(wLore);
    meta.setLore(lore);
    meta.setColor(Color.BLACK);
    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    
    witherTNTPotion.setItemMeta(meta);
  }
  
  @EventHandler
  public void deathDrop(EntityDeathEvent deathEvent) {
    LivingEntity e = deathEvent.getEntity();
    
    switch (e.getType()) {
      case CREEPER:
        deathEvent.getDrops().clear(); // stop the entity default drop
        tntPotionCon();
        for ( int i = 0; i < 2; i++) {e.getWorld().dropItem(e.getLocation(), tntPotion); }
        break;
        
      case ZOMBIE:
        deathEvent.getDrops().clear(); // stop the entity default drop
        lightningPotionCon();
        for ( int i = 0; i < 2; i++) {e.getWorld().dropItem(e.getLocation(), lightningPotion); }
        break;
      
      //case COW: // testing
      case WITHER:
        deathEvent.getDrops().clear(); // stop the entity default drop
        witherTNTPotionCon();
        e.getWorld().dropItem(e.getLocation(), witherTNTPotion);
        break;
        
      default:
        break;
    }
  }
  
  @EventHandler
  public void potionThrow(ProjectileHitEvent proHit) {
    
    // Check 1
    if (proHit.getEntityType() != EntityType.SPLASH_POTION) { return; }
    
    // Check 2
    ThrownPotion t = (ThrownPotion) proHit.getEntity(); // casting to Projectile interface for new methods.
    if (t.getItem().getItemMeta().getLore() == null) { return; } // checking if special potion (bug fix)
    
    String loreCase = t.getItem().getItemMeta().getLore().get(0);
    
    switch(loreCase) {
      // lightning bottle
      case lLore: 
        proHit.getEntity().getWorld().createExplosion(proHit.getEntity().getLocation(), 2f);
        for (int i = 0; i < 10; i++) {
          proHit.getEntity().getWorld().strikeLightning(proHit.getEntity().getLocation()); // strike lightning.
        }
        break;
      
      // TNT Bottle
      case tLore:
        for (int i = 0; i < 10; i++) {
          proHit.getEntity().getWorld().spawn(proHit.getEntity().getLocation().add(0, 0, 0), TNTPrimed.class);
          proHit.getEntity().setTicksLived(20);
        }
        break;
      
      // Wither TNT Bottle
      case wLore:
        for (int i = 0; i < 562; i++) {
          proHit.getEntity().getWorld().spawn(proHit.getEntity().getLocation().add(0, 0, 0), TNTPrimed.class);
        }
        break;
        
      default:
        break;
    }
    
    // testing cast.
    //proHit.getEntity().getWorld().dropItem(proHit.getEntity().getLocation(), t.getItem());
  }
  
  /*
  @EventHandler
  public void arrHit(ProjectileHitEvent hitEv, EntityShootBowEvent shootEv) {
    
    Material m = Material.TNT;
    BlockData b = m.createBlockData();
    
    if (hitEv.getEntityType() == EntityType.ARROW) {
      Location l = hitEv.getEntity().getLocation(); // position in world
      hitEv.getEntity().getWorld().spawnFallingBlock(l, b); // 
    }
    
  }
  */
  
}
