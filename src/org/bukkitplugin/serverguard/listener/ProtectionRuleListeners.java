package org.bukkitplugin.serverguard.listener;

import org.bukkit.Location;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkitplugin.serverguard.ProtectionRule;
import org.bukkitplugin.serverguard.ServerGuardPlugin;

public final class ProtectionRuleListeners implements Listener {
    
    @EventHandler(priority = EventPriority.LOWEST)
	public void on(CreatureSpawnEvent e) {
	    LivingEntity entity = e.getEntity();
	    Location location = e.getLocation();
		if (entity instanceof Animals) {
		    if (!ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doAnimalSpawning, location)) e.setCancelled(true);
		} else if (entity instanceof Monster) {
		    if (!ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doMonsterSpawning, location)) e.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(BlockBurnEvent e) {
		if (!ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doBlockBurning, e.getBlock().getLocation())) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(EntityExplodeEvent e) {
		Location location = e.getLocation();
		if (!ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doExplosion, location)) e.setCancelled(true);
		else if (e.getEntity() instanceof TNTPrimed && !ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doTntExplosion, location)) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(BlockExplodeEvent e) {
		if (!ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doExplosion, e.getBlock().getLocation())) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(EntityDamageEvent e) {
		Entity entity = e.getEntity();
	    if (entity instanceof Damageable && !ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doEntityDamage, entity.getLocation())) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(EntityDamageByBlockEvent e) {
		Entity entity = e.getEntity();
	    if (e.getEntity() instanceof Damageable && !ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doEntityDamage, entity.getLocation())) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(EntityDamageByEntityEvent e) {
		Entity entity = e.getEntity();
	    if (e.getEntity() instanceof Damageable && !ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doEntityDamage, entity.getLocation())) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void BlockIgnite(BlockIgniteEvent e) {
		Location location = e.getBlock().getLocation();
		if (!ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doFireIgniting, location)) e.setCancelled(true);
		else if (e.getCause() == BlockIgniteEvent.IgniteCause.SPREAD && !ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doFireSpreading, location)) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(BlockPlaceEvent e) {
	    if (!ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doPlayerBuilding, e.getBlock().getLocation())) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(BlockBreakEvent e) {
	    if (!ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doPlayerBuilding, e.getBlock().getLocation())) e.setCancelled(true);
	}
	
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(PlayerBucketFillEvent e) {
	    if (!ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doPlayerBuilding, e.getBlockClicked().getLocation())) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(PlayerBucketEmptyEvent e) {
	    if (!ServerGuardPlugin.plugin.getProtectionRuleValue(ProtectionRule.doPlayerBuilding, e.getBlockClicked().getLocation())) e.setCancelled(true);
	}
	
}