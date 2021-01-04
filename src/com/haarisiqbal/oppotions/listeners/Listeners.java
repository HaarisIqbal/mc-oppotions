package com.haarisiqbal.oppotions.listeners;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.entity.Item;

@SuppressWarnings("unused")
public class Listeners implements Listener {
  
  private ItemStack lightningPotion = new ItemStack(Material.SPLASH_POTION);
  
  private String dName = "Lighting in a Bottle";
  private String dLore = "Throw this for a bad time...";
  
  // configure custom drop.
  public void customPotion() {
    ItemMeta im = lightningPotion.getItemMeta();
    ArrayList<String> lore = new ArrayList<String>();
    
    im.setDisplayName(dName);
    lore.add(dLore);
    im.setLore(lore);
    im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    
    lightningPotion.setItemMeta(im);
  }
  
  @EventHandler
  public void deathDrop(EntityDeathEvent deathEvent) {
    customPotion();
    
    LivingEntity e = deathEvent.getEntity();
    
    if (e.getType() == EntityType.CREEPER) {
      deathEvent.getDrops().clear(); // stop the entity default drop
      
      for (int i = 0; i < 3; i++) {
        e.getWorld().dropItem(e.getLocation(), lightningPotion);
      }
    }
  }
  
  /*
  public void prePotionThrow(ProjectileLaunchEvent proLaunch) {
    Player p = (Player) new Player.Spigot();
    
    if (proLaunch.getEntity().getShooter() == new ProjectileSource() {
      p = (Player) proLaunch.getEntity().getShooter();
    }
    proLaunch.getEntity().getShooter();
  }
  */
  
  @EventHandler
  public void potionThrow(ProjectileHitEvent proHit) {
    
    // Three checks
    if (proHit.getEntityType() != EntityType.SPLASH_POTION) {return;} // "GET OUT OF MY HOUSE".
    ThrownPotion t = (ThrownPotion) proHit.getEntity();
    if (t.getItem().getItemMeta().getLore() == null) {return;} // checking if special potion (bug fix)
    if (!t.getItem().getItemMeta().getLore().get(0).equals(dLore)) {return;} // checking if lightning potion
    
    proHit.getEntity().getWorld().strikeLightning(proHit.getEntity().getLocation()); // strike lightning.
    
    // used for testing.
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
