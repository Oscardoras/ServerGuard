package org.bukkitplugin.serverguard.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.permissions.Permission;
import org.bukkitplugin.serverguard.Message;
import org.bukkitplugin.serverguard.targets.Group;
import org.bukkitplugin.serverguard.targets.PermissiblePlayer;
import org.bukkitutils.command.v1_15_V1.Argument;
import org.bukkitutils.command.v1_15_V1.CommandRegister;
import org.bukkitutils.command.v1_15_V1.LiteralArgument;
import org.bukkitutils.command.v1_15_V1.CommandRegister.CommandExecutorType;
import org.bukkitutils.command.v1_15_V1.arguments.GreedyStringArgument;
import org.bukkitutils.command.v1_15_V1.arguments.ScoreboardEntryArgument;
import org.bukkitutils.command.v1_15_V1.arguments.StringArgument;
import org.bukkitutils.command.v1_15_V1.arguments.ScoreboardEntryArgument.EntrySelector;

public final class GroupCommand {
	private GroupCommand() {}
	
	public static void list() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("list", new LiteralArgument("list"));
		CommandRegister.register("group", arguments, new Permission("serverguard.command.group"), CommandExecutorType.ALL, (cmd) -> {
			List<String> list = new ArrayList<String>();
			for (Group group : Group.getGroups()) list.add(group.getName());
			cmd.sendListMessage(list, new Object[] {new Message("command.group.list.list")}, new Object[] {new Message("command.group.list.empty")});
			return list.size();
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("list", new LiteralArgument("list"));
		arguments.put("group", new GroupArgument());
		CommandRegister.register("group", arguments, new Permission("serverguard.command.group"), CommandExecutorType.ALL, (cmd) -> {
			Group group = (Group) cmd.getArg(0);
			List<String> list = new ArrayList<String>();
			for (OfflinePlayer offlinePlayer : group.getPlayers()) list.add(offlinePlayer.getName());
			cmd.sendListMessage(list, new Object[] {new Message("command.group.players.list", group.getName())}, new Object[] {new Message("command.group.players.empty", group.getName())});
			return list.size();
		});
	}
	
	public static void create() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("create", new LiteralArgument("create"));
		arguments.put("group", new StringArgument());
		CommandRegister.register("group", arguments, new Permission("serverguard.command.group"), CommandExecutorType.ALL, (cmd) -> {
			Group group = Group.addGroup((String) cmd.getArg(0));
			if (group != null) {
				cmd.broadcastMessage(new Message("command.group.create", group.getName()));
				return 1;
			} else {
				cmd.sendFailureMessage(new Message("group.already_exists", (String) cmd.getArg(0)));
				return 0;
			}
		});
	}
	
	public static void delete() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("delete", new LiteralArgument("delete"));
		arguments.put("group", new GroupArgument());
		CommandRegister.register("group", arguments, new Permission("serverguard.command.group"), CommandExecutorType.ALL, (cmd) -> {
			Group group = (Group) cmd.getArg(0);
			group.delete();
			cmd.broadcastMessage(new Message("command.group.delete", group.getName()));
			return 1;
		});
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static void join() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("join", new LiteralArgument("join"));
		arguments.put("targets", new ScoreboardEntryArgument(EntrySelector.MANY_PLAYERS));
		arguments.put("group", new GroupArgument());
		CommandRegister.register("group", arguments, new Permission("serverguard.command.group"), CommandExecutorType.ALL, (cmd) -> {
			Group group = (Group) cmd.getArg(1);
			Collection<String> targets = (Collection<String>) cmd.getArg(0);
			for (String player : targets) {
				new PermissiblePlayer(Bukkit.getOfflinePlayer(player)).addGroup(group);
				cmd.broadcastMessage(new Message("command.group.join", player, group.getName()));
			}
			return targets.size();
		});
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static void leave() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("leave", new LiteralArgument("leave"));
		arguments.put("targets", new ScoreboardEntryArgument(EntrySelector.MANY_PLAYERS));
		arguments.put("group", new GroupArgument());
		CommandRegister.register("group", arguments, new Permission("serverguard.command.group"), CommandExecutorType.ALL, (cmd) -> {
			Group group = (Group) cmd.getArg(1);
			Collection<String> targets = (Collection<String>) cmd.getArg(0);
			for (String player : targets) {
				new PermissiblePlayer(Bukkit.getOfflinePlayer(player)).removeGroup(group);
				cmd.broadcastMessage(new Message("command.group.leave", player, group.getName()));
			}
			return targets.size();
		});
	}
	
	public static void displayName() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("displayName_literal", new LiteralArgument("displayName"));
		arguments.put("group", new GroupArgument());
		CommandRegister.register("group", arguments, new Permission("serverguard.command.group"), CommandExecutorType.ALL, (cmd) -> {
			Group group = (Group) cmd.getArg(0);
			String name = group.getDisplayName();
			cmd.sendMessage(new Message("command.group.name.get", group.getName(), name != null ? name : group.getName()));
			return 1;
		});
		
		arguments.put("displayName", new GreedyStringArgument());
		CommandRegister.register("group", arguments, new Permission("serverguard.command.group"), CommandExecutorType.ALL, (cmd) -> {
			Group group = (Group) cmd.getArg(0);
			String name = (String) cmd.getArg(1);
			group.setDisplayName(name.equals("-") ? null : name);
			cmd.broadcastMessage(new Message("command.group.name.set", group.getName(), name));
			return 1;
		});
	}
	
}