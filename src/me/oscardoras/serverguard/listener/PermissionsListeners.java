package me.oscardoras.serverguard.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public final class PermissionsListeners implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.block.BlockBreakEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.break")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.block.BlockDamageEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.damage")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.block.BlockPlaceEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.blockplace")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.block.SignChangeEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.signchange")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerArmorStandManipulateEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.armorstandmanipulate")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerBedEnterEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.bedenter")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerDropItemEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.dropitem")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerEditBookEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.editbook")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerFishEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.fish")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerInteractAtEntityEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.interactentity")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerInteractEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.interact")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerInteractEntityEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.interactentity")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerItemConsumeEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.itemconsume")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerItemHeldEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.itemheld")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerMoveEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.move")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.entity.EntityPickupItemEvent e) {
		if (!e.getEntity().hasPermission("serverguard.action.pickupitem")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerPortalEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.portal")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerShearEntityEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.shearentity")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerToggleFlightEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.toggleflight")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerToggleSneakEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.togglesneak")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.PlayerToggleSprintEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.togglesprint")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.inventory.CraftItemEvent e) {
		if (!(e.getWhoClicked()).hasPermission("serverguard.action.craft")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.player.AsyncPlayerChatEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.chat")) e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on(org.bukkit.event.inventory.InventoryOpenEvent e) {
		if (!e.getPlayer().hasPermission("serverguard.action.openinventory")) e.setCancelled(true);
	}
	
}