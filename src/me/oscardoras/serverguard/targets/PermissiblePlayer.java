package me.oscardoras.serverguard.targets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import me.oscardoras.serverguard.ServerGuardPlugin;

public class PermissiblePlayer {
	
	private static final Configuration config = ServerGuardPlugin.plugin.getConfig();
	
	
	protected final OfflinePlayer offlinePlayer;
	
	public PermissiblePlayer(OfflinePlayer offlinePlayer) {
		this.offlinePlayer = offlinePlayer;
	}
	
	public OfflinePlayer getPlayer() {
		return offlinePlayer;
	}
	
	public List<Group> getGroups() {
		List<Group> groups = new ArrayList<Group>();
		if (config.contains("player." + offlinePlayer.getUniqueId().toString() + ".groups")) {
			for (String name : config.getStringList("player." + offlinePlayer.getUniqueId().toString() + ".groups")) {
				Group group = Group.getGroup(name);
				if (group != null) groups.add(group);
			}
		}
		return groups;
	}
	
	public void addGroup(Group group) {
		List<Group> groups = getGroups();
		if (!groups.contains(group)) {
			groups.add(group);
			List<String> gs = new ArrayList<String>();
    		for (Group g : groups) gs.add(g.getName());
    		config.set("player." + offlinePlayer.getUniqueId().toString() + ".groups", gs);
    		ServerGuardPlugin.plugin.saveConfig();
    		
    		if (offlinePlayer.isOnline()) {
    			Player player = offlinePlayer.getPlayer();
    			ServerGuardPlugin.plugin.reloadPermissions(player, player.getLocation());
    			ServerGuardPlugin.plugin.reloadDisplayName(player);
    		}
		}
	}
	
	public void removeGroup(Group group) {
		List<Group> groups = getGroups();
		if (groups.contains(group)) {
			groups.remove(group);
			List<String> gs = new ArrayList<String>();
    		for (Group g : groups) gs.add(g.getName());
    		config.set("player." + offlinePlayer.getUniqueId().toString() + ".groups", gs);
    		ServerGuardPlugin.plugin.saveConfig();
    		
    		if (offlinePlayer.isOnline()) {
    			Player player = offlinePlayer.getPlayer();
    			ServerGuardPlugin.plugin.reloadPermissions(player, player.getLocation());
    			ServerGuardPlugin.plugin.reloadDisplayName(player);
    		}
		}
	}
	
	@Override
	public boolean equals(Object object) {
		return object != null && object instanceof PermissiblePlayer && offlinePlayer.equals(((PermissiblePlayer) object).offlinePlayer);
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash *= 7 + offlinePlayer.hashCode();
		return hash;
	}
	
}