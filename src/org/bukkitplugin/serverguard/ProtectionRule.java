package org.bukkitplugin.serverguard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkitplugin.serverguard.targets.Area;

public enum ProtectionRule {
    
    doAnimalSpawning,
    doBlockBurning,
    doExplosion,
    doEntityDamage,
    doFireIgniting,
    doFireSpreading,
    doMonsterSpawning,
    doPlayerBuilding,
    doTntExplosion;
	
	private static final Configuration config = ServerGuardPlugin.plugin.getConfig();
	
	
	private String getPath() {
		return name().toLowerCase();
	}
	
	
	public static boolean get(Server server, ProtectionRule rule) throws ProtectionRuleNotDefinedException {
		if (config.contains("server.protection_rules." + rule.getPath())) return config.getBoolean("server.protection_rules." + rule.getPath());
		else throw new ProtectionRuleNotDefinedException();
	}
	
	public static boolean get(World world, ProtectionRule rule) throws ProtectionRuleNotDefinedException {
		if (config.contains("world." + world.getUID().toString() + ".protection_rules." + rule.getPath())) return config.getBoolean("world." + world.getUID().toString() + ".protection_rules." + rule.getPath());
		else throw new ProtectionRuleNotDefinedException();
	}
	
	public static boolean get(Area area, ProtectionRule rule) throws ProtectionRuleNotDefinedException {
		if (config.contains("area." + area.getName() + ".protection_rules." + rule.getPath())) return config.getBoolean("area." + area.getName() + ".protection_rules." + rule.getPath());
		else throw new ProtectionRuleNotDefinedException();
	}
	
	public static List<ProtectionRule> list(Server server) {
		List<ProtectionRule> list = new ArrayList<ProtectionRule>();
		for (ProtectionRule rule : ProtectionRule.values()) {
			if (config.contains("server.protection_rules." + rule.getPath())) list.add(rule);
		}
		return list;
	}
	
	public static List<ProtectionRule> list(World world) {
		List<ProtectionRule> list = new ArrayList<ProtectionRule>();
		for (ProtectionRule rule : ProtectionRule.values()) {
			if (config.contains("world." + world.getUID().toString() + ".protection_rules." + rule.getPath())) list.add(rule);
		}
		return list;
	}
	
	public static List<ProtectionRule> list(Area area) {
		List<ProtectionRule> list = new ArrayList<ProtectionRule>();
		for (ProtectionRule rule : ProtectionRule.values()) {
			if (config.contains("area." + area.getName() + ".protection_rules." + rule.getPath())) list.add(rule);
		}
		return list;
	}
	
	public static void set(Server server, ProtectionRule rule, boolean value) {
		config.set("server.protection_rules." + rule.getPath(), value);
		ServerGuardPlugin.plugin.saveConfig();
	}
	
	public static void set(World world, ProtectionRule rule, boolean value) {
		config.set("world." + world.getUID().toString() + ".protection_rules." + rule.getPath(), value);
		ServerGuardPlugin.plugin.saveConfig();
	}
	
	public static void set(Area area, ProtectionRule rule, boolean value) {
		config.set("area." + area.getName() + ".protection_rules." + rule.getPath(), value);
		ServerGuardPlugin.plugin.saveConfig();
	}
	
	public static void remove(Server server, ProtectionRule rule) {
		config.set("server.protection_rules." + rule.getPath(), null);
		ServerGuardPlugin.plugin.saveConfig();
	}
	
	public static void remove(World world, ProtectionRule rule) {
		config.set("world." + world.getUID().toString() + ".protection_rules." + rule.getPath(), null);
		ServerGuardPlugin.plugin.saveConfig();
	}
	
	public static void remove(Area area, ProtectionRule rule) {
		config.set("area." + area.getName() + ".protection_rules." + rule.getPath(), null);
		ServerGuardPlugin.plugin.saveConfig();
	}
	
	public static class ProtectionRuleNotDefinedException extends Exception {
	    
		private static final long serialVersionUID = -4403712021976219641L;
		
	}
	
}