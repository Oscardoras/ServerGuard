package org.bukkitplugin.serverguard.targets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkitplugin.serverguard.CalculateArea;
import org.bukkitplugin.serverguard.ServerGuardPlugin;

public class Area {
	
	protected final static Configuration config = ServerGuardPlugin.plugin.getConfig();
	
	
	protected final String name;
	
	private Area(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDisplayName() {
		if (config.contains("area." + name)) {
			if (config.contains("area." + name + ".display_name")) return config.getString("area." + name + ".display_name");
			else return null;
		} else throw new IllegalStateException("Unregistered area");
	}
	
	public void setDisplayName(String displayName) {
		if (config.contains("area." + name)) {
			config.set("area." + name + ".display_name", displayName);
			ServerGuardPlugin.plugin.saveConfig();
			for (Player player : getPlayers()) ServerGuardPlugin.plugin.reloadArea(player, player.getLocation());
		} else throw new IllegalStateException("Unregistered area");
	}
	
	public World getWorld() {
		if (config.contains("area." + name)) {
			try {
				return Bukkit.getWorld(UUID.fromString(config.getString("area." + name + ".world")));
			} catch (Exception e) {
				return null;
			}
		} else throw new IllegalStateException("Unregistered area");
	}
	
	public Location getPositive() {
		if (config.contains("area." + name)) {
			ConfigurationSection section = config.getConfigurationSection("area." + name + ".positive");
			try {
				return new Location(getWorld(), section.getDouble("x"), section.getDouble("y"), section.getDouble("z"));
			} catch (Exception e) {
				return null;
			}
		} else throw new IllegalStateException("Unregistered area");
	}
	
	public Location getNegative() {
		if (config.contains("area." + name)) {
			ConfigurationSection section = config.getConfigurationSection("area." + name + ".negative");
			try {
				return new Location(getWorld(), section.getDouble("x"), section.getDouble("y"), section.getDouble("z"));
			} catch (Exception e) {
				return null;
			}
		} else throw new IllegalStateException("Unregistered area");
	}
	
	public void setLocations(Location positive, Location negative) {
		if (config.contains("area." + name)) {
			World oldWorld = getWorld();
			ConfigurationSection section = config.getConfigurationSection("area." + name);
			CalculateArea locations = new CalculateArea(positive, negative);
			Location p = locations.getPositive();
			Location n = locations.getNegative();
			World world = p.getWorld();
			section.set("world", world.getUID().toString());
			section.set("positive.x", p.getX());
			section.set("positive.y", p.getY());
			section.set("positive.z", p.getZ());
			section.set("negative.x", n.getX());
			section.set("negative.y", n.getY());
			section.set("negative.z", n.getZ());
			ServerGuardPlugin.plugin.saveConfig();
			
			for (Player player : oldWorld.getPlayers()) ServerGuardPlugin.plugin.reloadPermissions(player, player.getLocation());
			for (Player player : getPlayers()) {
				ServerGuardPlugin.plugin.reloadArea(player, player.getLocation());
				ServerGuardPlugin.plugin.reloadPermissions(player, player.getLocation());
			}
		} else throw new IllegalStateException("Unregistered area");
	}
	
	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<Player>();
		for (Player player : getWorld().getPlayers()) if (Area.getLocationAreas(player.getLocation()).contains(this)) players.add(player);
		return players;
	}
	
	public void delete() {
		if (config.contains("area." + name)) {
			config.set("area." + name, null);
			ServerGuardPlugin.plugin.saveConfig();
			
			for (Player player : getPlayers()) ServerGuardPlugin.plugin.reloadPermissions(player, player.getLocation());
		} else throw new IllegalStateException("Unregistered area");
	}
	
	@Override
	public boolean equals(Object object) {
		return object != null && object instanceof Area && name.equals(((Area) object).name);
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash *= 1 + name.hashCode();
		return hash;
	}
	
	public static Area getArea(String name) {
		if (config.contains("area." + name)) {
			Area area = new Area(name);
			if (area.getWorld() != null) return area;
		}
		return null;
	}
	
	public static List<Area> getAreas() {
		List<Area> areas = new ArrayList<Area>();
		if (config.contains("area")) {
			for (String name : config.getConfigurationSection("area").getKeys(false)) {
				Area area = new Area(name);
				if (area.getWorld() != null) areas.add(area);
			}
		}
		return areas;
	}
	
	public static Area addArea(String name, Location positive, Location negative) {
		if (getArea(name) == null) {
			config.createSection("area." + name);
			ConfigurationSection section = config.getConfigurationSection("area." + name);
			CalculateArea locations = new CalculateArea(positive, negative);
			Location p = locations.getPositive();
			Location n = locations.getNegative();
			World world = p.getWorld();
			section.set("world", world.getUID().toString());
			section.set("positive.x", p.getX());
			section.set("positive.y", p.getY());
			section.set("positive.z", p.getZ());
			section.set("negative.x", n.getX());
			section.set("negative.y", n.getY());
			section.set("negative.z", n.getZ());
			ServerGuardPlugin.plugin.saveConfig();
			
			Area area = new Area(name);
			for (Player player : area.getPlayers()) {
				ServerGuardPlugin.plugin.reloadArea(player, player.getLocation());
				ServerGuardPlugin.plugin.reloadPermissions(player, player.getLocation());
			}
			return area;
		}
		return null;
	}
	
	public static List<Area> getLocationAreas(Location location) {
		List<Area> areas = new ArrayList<Area>();
		World world = location.getWorld();
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		for (Area area : getAreas()) {
			Location positive = area.getPositive();
			Location negative = area.getNegative();
			if (world.equals(positive.getWorld()))
				if (x <= positive.getBlockX() && x >= negative.getBlockX())
					if (y <= positive.getBlockY() && y >= negative.getBlockY())
						if (z <= positive.getBlockZ() && z >= negative.getBlockZ())
							areas.add(area);
		}
		return areas;
	}
}