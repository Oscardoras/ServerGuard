package org.bukkitplugin.serverguard.commands;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkitplugin.serverguard.Message;
import org.bukkitplugin.serverguard.Permissions;
import org.bukkitplugin.serverguard.targets.Area;
import org.bukkitplugin.serverguard.targets.Group;
import org.bukkitutils.command.v1_14_3_V1.Argument;
import org.bukkitutils.command.v1_14_3_V1.CommandRegister;
import org.bukkitutils.command.v1_14_3_V1.CommandRegister.CommandExecutorType;
import org.bukkitutils.command.v1_14_3_V1.LiteralArgument;
import org.bukkitutils.command.v1_14_3_V1.arguments.EntitySelectorArgument;
import org.bukkitutils.command.v1_14_3_V1.arguments.EntitySelectorArgument.EntitySelector;
import org.bukkitutils.command.v1_14_3_V1.arguments.OfflinePlayerArgument;
import org.bukkitutils.command.v1_14_3_V1.arguments.PermissionArgument;
import org.bukkitutils.command.v1_14_3_V1.arguments.WorldArgument;

public final class PermissionCommand {
	private PermissionCommand() {}
	
	
	public static void server() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("server", new LiteralArgument("server"));
		arguments.put("list", new LiteralArgument("list"));
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			List<String> permissions = Permissions.get(Bukkit.getServer());
			cmd.sendListMessage(permissions, new Object[] {new Message("command.permission.list.list")}, new Object[] {new Message("command.permission.list.empty")});
			return permissions.size();
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("server", new LiteralArgument("server"));
		arguments.put("add", new LiteralArgument("add"));
		arguments.put("permission", new PermissionArgument());
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			Permissions.add(Bukkit.getServer(), ((Permission) cmd.getArg(0)).getName());
			cmd.broadcastMessage(new Message("command.permission.add"));
			return 1;
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("server", new LiteralArgument("server"));
		arguments.put("remove", new LiteralArgument("remove"));
		arguments.put("permission", new PermissionArgument());
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			Permissions.remove(Bukkit.getServer(), ((Permission) cmd.getArg(0)).getName());
			cmd.broadcastMessage(new Message("command.permission.remove"));
			return 1;
		});
	}
	
	public static void world() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("world_literal", new LiteralArgument("world"));
		arguments.put("world", new WorldArgument());
		arguments.put("list", new LiteralArgument("list"));
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			World world = (World) cmd.getArg(0);
			List<String> permissions = Permissions.get(world);
			cmd.sendListMessage(permissions, new Object[] {new Message("command.permission.list.list")}, new Object[] {new Message("command.permission.list.empty")});
			return permissions.size();
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("world_literal", new LiteralArgument("world"));
		arguments.put("world", new WorldArgument());
		arguments.put("add", new LiteralArgument("add"));
		arguments.put("permission", new PermissionArgument());
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			World world = (World) cmd.getArg(0);
			Permissions.add(world, ((Permission) cmd.getArg(1)).getName());
			cmd.broadcastMessage(new Message("command.permission.add"));
			return 1;
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("world_literal", new LiteralArgument("world"));
		arguments.put("world", new WorldArgument());
		arguments.put("remove", new LiteralArgument("remove"));
		arguments.put("permission", new PermissionArgument());
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			World world = (World) cmd.getArg(0);
			Permissions.remove(world, ((Permission) cmd.getArg(1)).getName());
			cmd.broadcastMessage(new Message("command.permission.remove"));
			return 1;
		});
	}
	
	public static void area() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("area_literal", new LiteralArgument("area"));
		arguments.put("area", new AreaArgument());
		arguments.put("list", new LiteralArgument("list"));
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			Area area = (Area) cmd.getArg(0);
			List<String> permissions = Permissions.get(area);
			cmd.sendListMessage(permissions, new Object[] {new Message("command.permission.list.list")}, new Object[] {new Message("command.permission.list.empty")});
			return permissions.size();
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("area_literal", new LiteralArgument("area"));
		arguments.put("area", new AreaArgument());
		arguments.put("add", new LiteralArgument("add"));
		arguments.put("permission", new PermissionArgument());
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			Area area = (Area) cmd.getArg(0);
			Permissions.add(area, ((Permission) cmd.getArg(1)).getName());
			cmd.broadcastMessage(new Message("command.permission.add"));
			return 1;
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("area_literal", new LiteralArgument("area"));
		arguments.put("area", new AreaArgument());
		arguments.put("remove", new LiteralArgument("remove"));
		arguments.put("permission", new PermissionArgument());
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			Area area = (Area) cmd.getArg(0);
			Permissions.remove(area, ((Permission) cmd.getArg(1)).getName());
			cmd.broadcastMessage(new Message("command.permission.remove"));
			return 1;
		});
	}
	
	public static void group() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("group_literal", new LiteralArgument("group"));
		arguments.put("group", new GroupArgument());
		arguments.put("list", new LiteralArgument("list"));
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			Group group = (Group) cmd.getArg(0);
			List<String> permissions = Permissions.get(group);
			cmd.sendListMessage(permissions, new Object[] {new Message("command.permission.list.list")}, new Object[] {new Message("command.permission.list.empty")});
			return permissions.size();
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("group_literal", new LiteralArgument("group"));
		arguments.put("group", new GroupArgument());
		arguments.put("add", new LiteralArgument("add"));
		arguments.put("permission", new PermissionArgument());
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			Group group = (Group) cmd.getArg(0);
			Permissions.add(group, ((Permission) cmd.getArg(1)).getName());
			cmd.broadcastMessage(new Message("command.permission.add"));
			return 1;
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("group_literal", new LiteralArgument("group"));
		arguments.put("group", new GroupArgument());
		arguments.put("remove", new LiteralArgument("remove"));
		arguments.put("permission", new PermissionArgument());
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			Group group = (Group) cmd.getArg(0);
			Permissions.remove(group, ((Permission) cmd.getArg(1)).getName());
			cmd.broadcastMessage(new Message("command.permission.remove"));
			return 1;
		});
	}
	
	@SuppressWarnings("unchecked")
	public static void player() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("player_literal", new LiteralArgument("player"));
		arguments.put("targets", new EntitySelectorArgument(EntitySelector.MANY_PLAYERS));
		arguments.put("list", new LiteralArgument("list"));
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			Collection<Player> targets = (Collection<Player>) cmd.getArg(0);
			for (Player player : targets)
				cmd.sendListMessage(Permissions.get(player), new Object[] {new Message("command.permission.list.list")}, new Object[] {new Message("command.permission.list.empty")});
			return targets.size();
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("player_literal", new LiteralArgument("player"));
		arguments.put("targets", new EntitySelectorArgument(EntitySelector.MANY_PLAYERS));
		arguments.put("add", new LiteralArgument("add"));
		arguments.put("permission", new PermissionArgument());
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			Collection<Player> targets = (Collection<Player>) cmd.getArg(0);
			for (Player player : targets) {
				Permissions.add(player, ((Permission) cmd.getArg(1)).getName());
				cmd.broadcastMessage(new Message("command.permission.add"));
			}
			return targets.size();
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("player_literal", new LiteralArgument("player"));
		arguments.put("targets", new EntitySelectorArgument(EntitySelector.MANY_PLAYERS));
		arguments.put("remove", new LiteralArgument("remove"));
		arguments.put("permission", new PermissionArgument());
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			Collection<Player> targets = (Collection<Player>) cmd.getArg(0);
			for (Player player : targets) {
				Permissions.remove(player, ((Permission) cmd.getArg(1)).getName());
				cmd.broadcastMessage(new Message("command.permission.remove"));
			}
			return targets.size();
		});
	}
	
	public static void offlinePlayer() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("offlinePlayer_literal", new LiteralArgument("offlinePlayer"));
		arguments.put("targets", new OfflinePlayerArgument());
		arguments.put("list", new LiteralArgument("list"));
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			OfflinePlayer offlinePlayer = ((OfflinePlayer) cmd.getArg(0));
			List<String> permissions = Permissions.get(offlinePlayer);
			cmd.sendListMessage(permissions, new Object[] {new Message("command.permission.list.list")}, new Object[] {new Message("command.permission.list.empty")});
			return permissions.size();
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("offlinePlayer_literal", new LiteralArgument("offlinePlayer"));
		arguments.put("targets", new OfflinePlayerArgument());
		arguments.put("add", new LiteralArgument("add"));
		arguments.put("permission", new PermissionArgument());
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			OfflinePlayer offlinePlayer = ((OfflinePlayer) cmd.getArg(0));
			Permissions.add(offlinePlayer, ((Permission) cmd.getArg(1)).getName());
			cmd.broadcastMessage(new Message("command.permission.add"));
			return 1;
		});
		
		arguments = new LinkedHashMap<>();
		arguments.put("offlinePlayer_literal", new LiteralArgument("offlinePlayer"));
		arguments.put("targets", new OfflinePlayerArgument());
		arguments.put("remove", new LiteralArgument("remove"));
		arguments.put("permission", new PermissionArgument());
		CommandRegister.register("permission", arguments, new Permission("serverguard.command.permission"), CommandExecutorType.ALL, (cmd) -> {
			OfflinePlayer offlinePlayer = ((OfflinePlayer) cmd.getArg(0));
			Permissions.remove(offlinePlayer, ((Permission) cmd.getArg(1)).getName());
			cmd.broadcastMessage(new Message("command.permission.remove"));
			return 1;
		});
	}
	
}