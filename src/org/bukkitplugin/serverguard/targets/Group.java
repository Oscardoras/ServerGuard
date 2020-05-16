package org.bukkitplugin.serverguard.targets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkitplugin.serverguard.ServerGuardPlugin;

public class Group {
	
	protected static final Configuration config = ServerGuardPlugin.plugin.getConfig();
	
	
	protected final String name;
	
	private Group(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDisplayName() {
		if (config.contains("group." + name)) {
			if (config.contains("group." + name + ".display_name")) return config.getString("group." + name + ".display_name");
			else return null;
		} else throw new IllegalStateException("Unregistered group");
	}
	
	public void setDisplayName(String displayName) {
		if (config.contains("group." + name)) {
			config.set("group." + name + ".display_name", displayName);
			ServerGuardPlugin.plugin.saveConfig();
			
			for (OfflinePlayer offlinePlayer : getPlayers()) if (offlinePlayer.isOnline()) ServerGuardPlugin.plugin.reloadDisplayName(offlinePlayer.getPlayer());
		} else throw new IllegalStateException("Unregistered group");
	}
	
	public List<OfflinePlayer> getPlayers() {
		List<OfflinePlayer> offlinePlayers = new ArrayList<OfflinePlayer>();
		for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
			if (new PermissiblePlayer(offlinePlayer).getGroups().contains(this)) offlinePlayers.add(offlinePlayer);
		}
		return offlinePlayers;
	}
	
	public void delete() {
		if (config.contains("group." + name)) {
			config.set("group." + name, null);
			ServerGuardPlugin.plugin.saveConfig();
			
			for (OfflinePlayer offlinePlayer : getPlayers()) if (offlinePlayer.isOnline()) {
				Player player = offlinePlayer.getPlayer();
				ServerGuardPlugin.plugin.reloadPermissions(player, player.getLocation());
				ServerGuardPlugin.plugin.reloadDisplayName(player);
			}
		} else throw new IllegalStateException("Unregistered group");
	}
	
	@Override
	public boolean equals(Object object) {
		return object != null && object instanceof Group && name.equals(((Group) object).name);
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash *= 12 + name.hashCode();
		return hash;
	}
	
	public static Group getGroup(String name) {
		if (config.contains("group." + name)) return new Group(name);
		return null;
	}
	
	public static List<Group> getGroups() {
		List<Group> groups = new ArrayList<Group>();
		if (config.contains("group")) {
			for (String name : config.getConfigurationSection("group").getKeys(false)) {
				try {
					groups.add(new Group(name));
				} catch (IllegalArgumentException ex) {}
			}
		}
		return groups;
	}
	
	public static Group addGroup(String name) {
		if (!config.contains("group." + name)) {
			config.createSection("group." + name);
			ServerGuardPlugin.plugin.saveConfig();
			
			Group group = new Group(name);
			return group;
		} else return null;
	}
	
}
