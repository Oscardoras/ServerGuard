package org.bukkitplugin.serverguard.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.permissions.Permission;
import org.bukkitplugin.serverguard.Message;
import org.bukkitplugin.serverguard.ProtectionRule;
import org.bukkitplugin.serverguard.ProtectionRule.ProtectionRuleNotDefinedException;
import org.bukkitplugin.serverguard.targets.Area;
import org.bukkitutils.command.v1_15_V1.Argument;
import org.bukkitutils.command.v1_15_V1.CommandRegister;
import org.bukkitutils.command.v1_15_V1.LiteralArgument;
import org.bukkitutils.command.v1_15_V1.CommandRegister.CommandExecutorType;
import org.bukkitutils.command.v1_15_V1.arguments.BooleanArgument;
import org.bukkitutils.command.v1_15_V1.arguments.WorldArgument;

public final class ProtectionRuleCommand {
	private ProtectionRuleCommand() {}
	
	
	public static void server() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("server", new LiteralArgument("server"));
		CommandRegister.register("protectionrule", arguments, new Permission("serverguard.command.protectionrule"), CommandExecutorType.ALL, (cmd) -> {
			List<String> protectionRules = new ArrayList<String>();
			for (ProtectionRule protectionRule : ProtectionRule.list(Bukkit.getServer())) protectionRules.add(protectionRule.name());
			cmd.sendListMessage(protectionRules, new Object[] {new Message("command.protectionrule.list.list")}, new Object[] {new Message("command.protectionrule.list.empty")});
			return 1;
		});
		
		for (ProtectionRule rule : ProtectionRule.values()) {
			arguments = new LinkedHashMap<>();
			arguments.put("server", new LiteralArgument("server"));
			arguments.put("protectionrule", new LiteralArgument(rule.name()));
			CommandRegister.register("protectionrule", arguments, new Permission("serverguard.command.protectionrule"), CommandExecutorType.ALL, (cmd) -> {
				try {
					cmd.sendMessage(new Message("command.protectionrule.get", rule.name(), ""+ProtectionRule.get(Bukkit.getServer(), rule)));
					return 1;
				} catch (ProtectionRuleNotDefinedException ex) {
					cmd.sendFailureMessage(new Message("protectionrule.is_not_defined", rule.name()));
					return 0;
				}
			});
			
			arguments = new LinkedHashMap<>();
			arguments.put("server", new LiteralArgument("server"));
			arguments.put("protectionrule", new LiteralArgument(rule.name()));
			arguments.put("value", new BooleanArgument());
			CommandRegister.register("protectionrule", arguments, new Permission("serverguard.command.protectionrule"), CommandExecutorType.ALL, (cmd) -> {
				ProtectionRule.set(Bukkit.getServer(), rule, (boolean) cmd.getArg(0));
				cmd.broadcastMessage(new Message("command.protectionrule.set", rule.name(), ""+cmd.getArg(0)));
				return 1;
			});
			
			arguments = new LinkedHashMap<>();
			arguments.put("server", new LiteralArgument("server"));
			arguments.put("protectionrule", new LiteralArgument(rule.name()));
			arguments.put("remove", new LiteralArgument("remove"));
			CommandRegister.register("protectionrule", arguments, new Permission("serverguard.command.protectionrule"), CommandExecutorType.ALL, (cmd) -> {
				ProtectionRule.remove(Bukkit.getServer(), rule);
				cmd.broadcastMessage(new Message("command.protectionrule.remove", rule.name()));
				return 1;
			});
		}
	}
	
	public static void world() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("world_literal", new LiteralArgument("world"));
		arguments.put("world", new WorldArgument());
		CommandRegister.register("protectionrule", arguments, new Permission("serverguard.command.protectionrule"), CommandExecutorType.ALL, (cmd) -> {
			World world = (World) cmd.getArg(0);
			List<String> protectionRules = new ArrayList<String>();
			for (ProtectionRule protectionRule : ProtectionRule.list(world)) protectionRules.add(protectionRule.name());
			cmd.sendListMessage(protectionRules, new Object[] {new Message("command.protectionrule.list.list")}, new Object[] {new Message("command.protectionrule.list.empty")});
			return 1;
		});
		
		for (ProtectionRule rule : ProtectionRule.values()) {
			arguments = new LinkedHashMap<>();
			arguments.put("world_literal", new LiteralArgument("world"));
			arguments.put("world", new WorldArgument());
			arguments.put("protectionrule", new LiteralArgument(rule.name()));
			CommandRegister.register("protectionrule", arguments, new Permission("serverguard.command.protectionrule"), CommandExecutorType.ALL, (cmd) -> {
				World world = (World) cmd.getArg(0);
				try {
					cmd.sendMessage(new Message("command.protectionrule.get", rule.name(), ""+ProtectionRule.get(world, rule)));
					return 1;
				} catch (ProtectionRuleNotDefinedException ex) {
					cmd.sendFailureMessage(new Message("protectionrule.is_not_defined", rule.name()));
					return 0;
				}
			});
			
			arguments = new LinkedHashMap<>();
			arguments.put("world_literal", new LiteralArgument("world"));
			arguments.put("world", new WorldArgument());
			arguments.put("protectionrule", new LiteralArgument(rule.name()));
			arguments.put("value", new BooleanArgument());
			CommandRegister.register("protectionrule", arguments, new Permission("serverguard.command.protectionrule"), CommandExecutorType.ALL, (cmd) -> {
				World world = (World) cmd.getArg(0);
				ProtectionRule.set(world, rule, (boolean) cmd.getArg(1));
				cmd.broadcastMessage(new Message("command.protectionrule.set", rule.name(), ""+cmd.getArg(1)));
				return 1;
			});
			
			arguments = new LinkedHashMap<>();
			arguments.put("world_literal", new LiteralArgument("world"));
			arguments.put("world", new WorldArgument());
			arguments.put("protectionrule", new LiteralArgument(rule.name()));
			arguments.put("remove", new LiteralArgument("remove"));
			CommandRegister.register("protectionrule", arguments, new Permission("serverguard.command.protectionrule"), CommandExecutorType.ALL, (cmd) -> {
				World world = (World) cmd.getArg(0);
				ProtectionRule.remove(world, rule);
				cmd.broadcastMessage(new Message("command.protectionrule.remove", rule.name()));
				return 1;
			});
		}
	}
	
	public static void area() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("area_literal", new LiteralArgument("area"));
		arguments.put("area", new AreaArgument());
		CommandRegister.register("protectionrule", arguments, new Permission("serverguard.command.protectionrule"), CommandExecutorType.ALL, (cmd) -> {
			Area area = (Area) cmd.getArg(0);
			List<String> protectionRules = new ArrayList<String>();
			for (ProtectionRule protectionRule : ProtectionRule.list(area)) protectionRules.add(protectionRule.name());
			cmd.sendListMessage(protectionRules, new Object[] {new Message("command.protectionrule.list.list")}, new Object[] {new Message("command.protectionrule.list.empty")});
			return 1;
		});
		
		for (ProtectionRule rule : ProtectionRule.values()) {
			arguments = new LinkedHashMap<>();
			arguments.put("area_literal", new LiteralArgument("area"));
			arguments.put("area", new AreaArgument());
			arguments.put("protectionrule", new LiteralArgument(rule.name()));
			CommandRegister.register("protectionrule", arguments, new Permission("serverguard.command.protectionrule"), CommandExecutorType.ALL, (cmd) -> {
				Area area = (Area) cmd.getArg(0);
				try {
					cmd.sendMessage(new Message("command.protectionrule.get", rule.name(), ""+ProtectionRule.get(area, rule)));
					return 1;
				} catch (ProtectionRuleNotDefinedException ex) {
					cmd.sendFailureMessage(new Message("protectionrule.is_not_defined", rule.name()));
					return 0;
				}
			});
			
			arguments = new LinkedHashMap<>();
			arguments.put("area_literal", new LiteralArgument("area"));
			arguments.put("area", new AreaArgument());
			arguments.put("protectionrule", new LiteralArgument(rule.name()));
			arguments.put("value", new BooleanArgument());
			CommandRegister.register("protectionrule", arguments, new Permission("serverguard.command.protectionrule"), CommandExecutorType.ALL, (cmd) -> {
				Area area = (Area) cmd.getArg(0);
				ProtectionRule.set(area, rule, (boolean) cmd.getArg(1));
				cmd.broadcastMessage(new Message("command.protectionrule.set", rule.name(), ""+cmd.getArg(1)));
				return 1;
			});
			
			arguments = new LinkedHashMap<>();
			arguments.put("area_literal", new LiteralArgument("area"));
			arguments.put("area", new AreaArgument());
			arguments.put("protectionrule", new LiteralArgument(rule.name()));
			arguments.put("remove", new LiteralArgument("remove"));
			CommandRegister.register("protectionrule", arguments, new Permission("serverguard.command.protectionrule"), CommandExecutorType.ALL, (cmd) -> {
				Area area = (Area) cmd.getArg(0);
				ProtectionRule.remove(area, rule);
				cmd.broadcastMessage(new Message("command.protectionrule.remove", rule.name()));
				return 1;
			});
		}
	}
	
}