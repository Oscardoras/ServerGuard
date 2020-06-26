package me.oscardoras.serverguard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import me.oscardoras.serverguard.targets.Area;
import me.oscardoras.serverguard.targets.Group;

public final class Permissions {
	private Permissions() {}
	
	
	private static final Configuration config = ServerGuardPlugin.plugin.getConfig();
	
	public static List<String> get(Server server) {
		List<String> permissions = new ArrayList<String>();
		if (config.contains("server.permissions")) {
			for (String permission : config.getStringList("server.permissions"))
				permissions.add(permission);
		}
		return permissions;
	}
	
	public static List<String> get(World world) {
		List<String> permissions = new ArrayList<String>();
		if (config.contains("world." + world.getUID().toString() + ".permissions")) {
			for (String permission : config.getStringList("world." + world.getUID().toString() + ".permissions"))
				permissions.add(permission);
		}
		return permissions;
	}

	public static List<String> get(Area area) {
		List<String> permissions = new ArrayList<String>();
		if (config.contains("area." + area.getName() + ".permissions")) {
			for (String permission : config.getStringList("area." + area.getName() + ".permissions"))
				permissions.add(permission);
		}
		return permissions;
	}
	
	public static List<String> get(Group group) {
		List<String> permissions = new ArrayList<String>();
		if (config.contains("group." + group.getName() + ".permissions")) {
			for (String permission : config.getStringList("group." + group.getName() + ".permissions"))
				permissions.add(permission);
		}
		return permissions;
	}
	
	public static List<String> get(OfflinePlayer offlinePlayer) {
		List<String> permissions = new ArrayList<String>();
		if (config.contains("player." + offlinePlayer.getUniqueId().toString() + ".permissions")) {
			for (String permission : config.getStringList("player." + offlinePlayer.getUniqueId().toString() + ".permissions"))
				permissions.add(permission);
		}
		return permissions;
	}
	
	
	public static void add(Server server, String permission) {
		List<String> list = config.contains("server.permissions") ? config.getStringList("server.permissions") : new ArrayList<String>();
		if (!list.contains(permission)) {
			list.add(permission);
			config.set("server.permissions", list);
			ServerGuardPlugin.plugin.saveConfig();
			for (Player player : Bukkit.getOnlinePlayers()) ServerGuardPlugin.plugin.reloadPermissions(player, player.getLocation());
		}
	}
	
	public static void add(World world, String permission) {
		List<String> list = config.contains("world." + world.getUID().toString() + ".permissions") ? config.getStringList("world." + world.getUID().toString() + ".permissions") : new ArrayList<String>();
		if (!list.contains(permission)) {
			list.add(permission);
			config.set("world." + world.getUID().toString() + ".permissions", list);
			ServerGuardPlugin.plugin.saveConfig();
			for (Player player : world.getPlayers()) ServerGuardPlugin.plugin.reloadPermissions(player, player.getLocation());
		}
	}

	public static void add(Area area, String permission) {
		List<String> list = config.contains("area." + area.getName() + ".permissions") ? config.getStringList("area." + area.getName() + ".permissions") : new ArrayList<String>();
		if (!list.contains(permission)) {
			list.add(permission);
			config.set("area." + area.getName() + ".permissions", list);
			ServerGuardPlugin.plugin.saveConfig();
			for (Player player : area.getPlayers()) ServerGuardPlugin.plugin.reloadPermissions(player, player.getLocation());
		}
	}
	
	public static void add(Group group, String permission) {
		List<String> list = config.contains("group." + group.getName() + ".permissions") ? config.getStringList("group." + group.getName() + ".permissions") : new ArrayList<String>();
		if (!list.contains(permission)) {
			list.add(permission);
			config.set("group." + group.getName() + ".permissions", list);
			ServerGuardPlugin.plugin.saveConfig();
			for (OfflinePlayer offlinePlayer : group.getPlayers()) if (offlinePlayer.isOnline()) ServerGuardPlugin.plugin.reloadPermissions(offlinePlayer.getPlayer(), offlinePlayer.getPlayer().getLocation());
		}
	}
	
	public static void add(OfflinePlayer offlinePlayer, String permission) {
		List<String> list = config.contains("player." + offlinePlayer.getUniqueId().toString() + ".permissions") ? config.getStringList("player." + offlinePlayer.getUniqueId().toString() + ".permissions") : new ArrayList<String>();
		if (!list.contains(permission)) {
			list.add(permission);
			config.set("player." + offlinePlayer.getUniqueId().toString() + ".permissions", list);
			ServerGuardPlugin.plugin.saveConfig();
			if (offlinePlayer.isOnline()) ServerGuardPlugin.plugin.reloadPermissions(offlinePlayer.getPlayer(), offlinePlayer.getPlayer().getLocation());
		}
	}
	
	
	public static void remove(Server server, String permission) {
		List<String> list = config.contains("server.permissions") ? config.getStringList("server.permissions") : new ArrayList<String>();
		if (list.contains(permission)) {
			list.remove(permission);
			config.set("server.permissions", list);
			ServerGuardPlugin.plugin.saveConfig();
			for (Player player : Bukkit.getOnlinePlayers()) ServerGuardPlugin.plugin.reloadPermissions(player, player.getLocation());
		}
	}
	
	public static void remove(World world, String permission) {
		List<String> list = config.contains("world." + world.getUID().toString() + ".permissions") ? config.getStringList("world." + world.getUID().toString() + ".permissions") : new ArrayList<String>();
		if (list.contains(permission)) {
			list.remove(permission);
			config.set("world." + world.getUID().toString() + ".permissions", list);
			ServerGuardPlugin.plugin.saveConfig();
			for (Player player : world.getPlayers()) ServerGuardPlugin.plugin.reloadPermissions(player, player.getLocation());
		}
	}

	public static void remove(Area area, String permission) {
		List<String> list = config.contains("area." + area.getName() + ".permissions") ? config.getStringList("area." + area.getName() + ".permissions") : new ArrayList<String>();
		if (list.contains(permission)) {
			list.remove(permission);
			config.set("area." + area.getName() + ".permissions", list);
			ServerGuardPlugin.plugin.saveConfig();
			for (Player player : area.getPlayers()) ServerGuardPlugin.plugin.reloadPermissions(player, player.getLocation());
		}
	}
	
	public static void remove(Group group, String permission) {
		List<String> list = config.contains("group." + group.getName() + ".permissions") ? config.getStringList("group." + group.getName() + ".permissions") : new ArrayList<String>();
		if (list.contains(permission)) {
			list.remove(permission);
			config.set("group." + group.getName() + ".permissions", list);
			ServerGuardPlugin.plugin.saveConfig();
			for (OfflinePlayer offlinePlayer : group.getPlayers()) if (offlinePlayer.isOnline()) ServerGuardPlugin.plugin.reloadPermissions(offlinePlayer.getPlayer(), offlinePlayer.getPlayer().getLocation());
		}
	}
	
	public static void remove(OfflinePlayer offlinePlayer, String permission) {
		List<String> list = config.contains("player." + offlinePlayer.getUniqueId().toString() + ".permissions") ? config.getStringList("player." + offlinePlayer.getUniqueId().toString() + ".permissions") : new ArrayList<String>();
		if (list.contains(permission)) {
			list.remove(permission);
			config.set("player." + offlinePlayer.getUniqueId().toString() + ".permissions", list);
			ServerGuardPlugin.plugin.saveConfig();
			if (offlinePlayer.isOnline()) ServerGuardPlugin.plugin.reloadPermissions(offlinePlayer.getPlayer(), offlinePlayer.getPlayer().getLocation());
		}
	}
	
}
