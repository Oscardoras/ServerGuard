package org.bukkitplugin.serverguard.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkitplugin.serverguard.Message;
import org.bukkitplugin.serverguard.targets.Group;
import org.bukkitplugin.serverguard.targets.PermissiblePlayer;
import org.bukkitutils.command.v1_14_3_V1.Argument;
import org.bukkitutils.command.v1_14_3_V1.CommandRegister;
import org.bukkitutils.command.v1_14_3_V1.CommandRegister.CommandExecutorType;
import org.bukkitutils.command.v1_14_3_V1.LiteralArgument;
import org.bukkitutils.command.v1_14_3_V1.arguments.EntitySelectorArgument;
import org.bukkitutils.command.v1_14_3_V1.arguments.EntitySelectorArgument.EntitySelector;
import org.bukkitutils.command.v1_14_3_V1.arguments.GreedyStringArgument;
import org.bukkitutils.command.v1_14_3_V1.arguments.OfflinePlayerArgument;
import org.bukkitutils.command.v1_14_3_V1.arguments.StringArgument;

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
	
	@SuppressWarnings("unchecked")
	public static void join() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("join", new LiteralArgument("join"));
		arguments.put("player", new LiteralArgument("player"));
		arguments.put("targets", new EntitySelectorArgument(EntitySelector.MANY_PLAYERS));
		arguments.put("group", new GroupArgument());
		CommandRegister.register("group", arguments, new Permission("serverguard.command.group"), CommandExecutorType.ALL, (cmd) -> {
			Group group = (Group) cmd.getArg(1);
			Collection<Player> targets = (Collection<Player>) cmd.getArg(0);
			for (Player player : targets) {
				new PermissiblePlayer(player).addGroup(group);
				cmd.broadcastMessage(new Message("command.group.join", player.getName(), group.getName()));
			}
			return targets.size();
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("join", new LiteralArgument("join"));
		arguments.put("offlinePlayer", new LiteralArgument("offlinePlayer"));
		arguments.put("targets", new OfflinePlayerArgument());
		arguments.put("group", new GroupArgument());
		CommandRegister.register("group", arguments, new Permission("serverguard.command.group"), CommandExecutorType.ALL, (cmd) -> {
			Group group = (Group) cmd.getArg(1);
			OfflinePlayer offlinePlayer = (OfflinePlayer) cmd.getArg(0);
			new PermissiblePlayer(offlinePlayer).addGroup(group);
			cmd.broadcastMessage(new Message("command.group.join", offlinePlayer.getName(), group.getName()));
			return 1;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static void leave() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("leave", new LiteralArgument("leave"));
		arguments.put("player", new LiteralArgument("player"));
		arguments.put("targets", new EntitySelectorArgument(EntitySelector.MANY_PLAYERS));
		arguments.put("group", new GroupArgument());
		CommandRegister.register("group", arguments, new Permission("serverguard.command.group"), CommandExecutorType.ALL, (cmd) -> {
			Group group = (Group) cmd.getArg(1);
			Collection<Player> targets = (Collection<Player>) cmd.getArg(0);
			for (Player player : targets) {
				new PermissiblePlayer(player).removeGroup(group);
				cmd.broadcastMessage(new Message("command.group.leave", player.getName(), group.getName()));
			}
			return targets.size();
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("leave", new LiteralArgument("leave"));
		arguments.put("offlinePlayer", new LiteralArgument("offlinePlayer"));
		arguments.put("targets", new OfflinePlayerArgument());
		arguments.put("group", new GroupArgument());
		CommandRegister.register("group", arguments, new Permission("serverguard.command.group"), CommandExecutorType.ALL, (cmd) -> {
			Group group = (Group) cmd.getArg(1);
			OfflinePlayer offlinePlayer = (OfflinePlayer) cmd.getArg(0);
			new PermissiblePlayer(offlinePlayer).removeGroup(group);
			cmd.broadcastMessage(new Message("command.group.leave", offlinePlayer.getName(), group.getName()));
			return 1;
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