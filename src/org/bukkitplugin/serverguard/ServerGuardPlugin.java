package org.bukkitplugin.serverguard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginManager;
import org.bukkitplugin.serverguard.ProtectionRule.ProtectionRuleNotDefinedException;
import org.bukkitplugin.serverguard.commands.AreaCommand;
import org.bukkitplugin.serverguard.commands.GroupCommand;
import org.bukkitplugin.serverguard.commands.PermissionCommand;
import org.bukkitplugin.serverguard.commands.ProtectionRuleCommand;
import org.bukkitplugin.serverguard.listener.PermissionsListeners;
import org.bukkitplugin.serverguard.listener.ProtectionRuleListeners;
import org.bukkitplugin.serverguard.targets.Area;
import org.bukkitplugin.serverguard.targets.Group;
import org.bukkitplugin.serverguard.targets.PermissiblePlayer;
import org.bukkitutils.BukkitPlugin;
import org.bukkitutils.PlayerReloader;
import org.bukkitutils.PlayerReloader.Type;

public final class ServerGuardPlugin extends BukkitPlugin {
	
	public static ServerGuardPlugin plugin;
	
	public ServerGuardPlugin() {
		plugin = this;
	}
	
	
	protected final Map<Player, PermissionAttachment> attachments = new HashMap<Player, PermissionAttachment>();
	protected final Map<Player, List<Area>> areas = new HashMap<Player, List<Area>>();
	
	@Override
	public void onLoad() {
		AreaCommand.list();
		AreaCommand.create();
		AreaCommand.delete();
		AreaCommand.locations();
		AreaCommand.displayName();
		GroupCommand.list();
		GroupCommand.create();
		GroupCommand.delete();
		GroupCommand.join();
		GroupCommand.leave();
		GroupCommand.displayName();
		PermissionCommand.server();
		PermissionCommand.world();
		PermissionCommand.area();
		PermissionCommand.group();
		PermissionCommand.player();
		ProtectionRuleCommand.server();
		ProtectionRuleCommand.world();
		ProtectionRuleCommand.area();
	}
	
	@Override
	public void onEnable() {
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new PermissionsListeners(), this);
		pluginManager.registerEvents(new ProtectionRuleListeners(), this);
		
		PlayerReloader.register(this, (player, location, type) -> {
			if (type != Type.QUIT) {
				if (type == Type.JOIN) reloadDisplayName(player);
				reloadArea(player, location);
				reloadPermissions(player, location);
			} else {
				attachments.remove(player);
				areas.remove(player);
			}
		}, 100L);
	}
	
	
	public boolean getProtectionRuleValue(ProtectionRule rule, Location location) {
		for (Area area : Area.getLocationAreas(location)) {
		    try {
		    	return ProtectionRule.get(area, rule);
		    } catch (ProtectionRuleNotDefinedException ex) {}
		}
		try {
			return ProtectionRule.get(location.getWorld(), rule);
		} catch (ProtectionRuleNotDefinedException ex1) {
			try {
				return ProtectionRule.get(Bukkit.getServer(), rule);
			} catch (ProtectionRuleNotDefinedException ex2) {
				return true;
			}
		}
	}
	
	public void reloadArea(Player player, Location location) {
		List<Area> areas1 = areas.containsKey(player) ? areas.get(player) : new ArrayList<Area>();
		List<Area> areas2 = Area.getLocationAreas(player.getLocation());
		for (Area area : areas1) if (!areas2.contains(area)) {
			String name = area.getDisplayName();
			if (name != null) player.sendTitle("", "~" + name + "~", 1, 60, 10);
		}
		for (Area area : areas2) if (!areas1.contains(area)) {
			String name = area.getDisplayName();
			if (name != null) player.sendTitle("", name, 1, 60, 10);
		}
		areas.put(player, areas2);
	}
	
	public void reloadDisplayName(Player player) {
		if (player.getCustomName() == null) player.setCustomName(player.getName());
		String name = ChatColor.RESET + "";
		for (Group group : new PermissiblePlayer(player).getGroups()) {
			String displayName = group.getDisplayName();
			if (displayName != null) name += "[" + displayName +  ChatColor.RESET + "] ";
		}
		name += player.getCustomName();
		player.setDisplayName(name);
		player.setPlayerListName(name);
	}
	
	public void reloadPermissions(Player player, Location location) {
		if (!attachments.containsKey(player)) attachments.put(player, player.addAttachment(this));
		PermissionAttachment attachment = attachments.get(player);
		
		Map<String, Boolean> oldPermissions = attachment.getPermissions();
		
		Map<String, Boolean> newPermissions = new HashMap<String, Boolean>();
		if (!player.isOp()) {
			addPermissions(newPermissions, Permissions.get(Bukkit.getServer()));
			addPermissions(newPermissions, Permissions.get(location.getWorld()));
			for (Area area : areas.containsKey(player) ? areas.get(player) : new ArrayList<Area>())  addPermissions(newPermissions, Permissions.get(area));
			for (Group group : new PermissiblePlayer(player).getGroups()) addPermissions(newPermissions, Permissions.get(group));
			addPermissions(newPermissions, Permissions.get(player));
		} else {
			List<String> permissions = new ArrayList<String>();
			for (Permission permission : Bukkit.getPluginManager().getPermissions()) permissions.add(permission.getName());
			addPermissions(newPermissions, permissions);
		}
		
		
		boolean changed = false;
		for (String oldPermission : oldPermissions.keySet()) if (!newPermissions.containsKey(oldPermission)) {
			attachment.unsetPermission(oldPermission);
			changed = true;
		}
		for (String newPermission : newPermissions.keySet()) if (!oldPermissions.containsKey(newPermission) || oldPermissions.get(newPermission) != newPermissions.get(newPermission)) {
			attachment.setPermission(newPermission, newPermissions.get(newPermission));
			changed = true;
		}
		
		if (changed) {
			player.recalculatePermissions();
			player.updateCommands();
		}
	}
	
	protected void addPermissions(Map<String, Boolean> newPermissions, Collection<String> permissionsToAdd) {
		for (String permission : permissionsToAdd) {
			if (permission.startsWith("-")) newPermissions.put(permission.substring(1), false);
			else newPermissions.put(permission, true);
		}
	}
	
}