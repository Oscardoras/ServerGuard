package org.bukkitplugin.serverguard.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkitplugin.serverguard.Message;
import org.bukkitplugin.serverguard.targets.Area;
import org.bukkitutils.command.v1_15_V1.Argument;
import org.bukkitutils.command.v1_15_V1.CommandRegister;
import org.bukkitutils.command.v1_15_V1.LiteralArgument;
import org.bukkitutils.command.v1_15_V1.CommandRegister.CommandExecutorType;
import org.bukkitutils.command.v1_15_V1.arguments.GreedyStringArgument;
import org.bukkitutils.command.v1_15_V1.arguments.LocationArgument;
import org.bukkitutils.command.v1_15_V1.arguments.StringArgument;
import org.bukkitutils.command.v1_15_V1.arguments.LocationArgument.LocationType;

public final class AreaCommand {
	private AreaCommand() {}
	
	
	public static void list() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("list", new LiteralArgument("list"));
		CommandRegister.register("area", arguments, new Permission("serverguard.command.area"), CommandExecutorType.ALL, (cmd) -> {
			List<String> list = new ArrayList<String>();
			for (Area area : Area.getAreas()) list.add(area.getName());
			cmd.sendListMessage(list, new Object[] {new Message("command.area.list.list")}, new Object[] {new Message("command.area.list.empty")});
			return list.size();
		});
		
		arguments.put("area", new AreaArgument());
		CommandRegister.register("area", arguments, new Permission("serverguard.command.area"), CommandExecutorType.ALL, (cmd) -> {
			Area area = (Area) cmd.getArg(0);
			List<String> list = new ArrayList<String>();
			for (Player player : area.getPlayers()) list.add(player.getName());
			cmd.sendListMessage(list, new Object[] {new Message("command.area.players.list", area.getName())}, new Object[] {new Message("command.area.players.empty", area.getName())});
			return list.size();
		});
	}
	
	public static void create() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("create", new LiteralArgument("create"));
		arguments.put("area", new StringArgument());
		arguments.put("positionNegative", new LocationArgument(LocationType.BLOCK_LOCATION));
		arguments.put("positionPositive", new LocationArgument(LocationType.BLOCK_LOCATION));
		CommandRegister.register("area", arguments, new Permission("serverguard.command.area"), CommandExecutorType.ALL, (cmd) -> {
			Area area = Area.addArea((String) cmd.getArg(0), (Location) cmd.getArg(1), (Location) cmd.getArg(2));
			if (area != null) {
				cmd.broadcastMessage(new Message("command.area.create", area.getName()));
				return 1;
			} else {
				cmd.sendMessage(new Message("area.already_exists", (String) cmd.getArg(0)));
				return 0;
			}
		});
	}
	
	public static void delete() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("delete", new LiteralArgument("delete"));
		arguments.put("area", new AreaArgument());
		CommandRegister.register("area", arguments, new Permission("serverguard.command.area"), CommandExecutorType.ALL, (cmd) -> {
			Area area = (Area) cmd.getArg(0);
			area.delete();
			cmd.broadcastMessage(new Message("command.area.delete", area.getName()));
			return 1;
		});
	}
	
	public static void locations() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("locations", new LiteralArgument("locations"));
		arguments.put("area", new AreaArgument());
		arguments.put("positionNegative", new LocationArgument(LocationType.BLOCK_LOCATION));
		arguments.put("positionPositive", new LocationArgument(LocationType.BLOCK_LOCATION));
		CommandRegister.register("area", arguments, new Permission("serverguard.command.area"), CommandExecutorType.ALL, (cmd) -> {
			Area area = (Area) cmd.getArg(0);
			area.setLocations((Location) cmd.getArg(1), (Location) cmd.getArg(2));
			cmd.broadcastMessage(new Message("command.area.locations", area.getName()));
			return 1;
		});
	}
	
	public static void displayName() {
		LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
		arguments.put("displayName_literal", new LiteralArgument("displayName"));
		arguments.put("area", new AreaArgument());
		CommandRegister.register("area", arguments, new Permission("serverguard.command.area"), CommandExecutorType.ALL, (cmd) -> {
			Area area = (Area) cmd.getArg(0);
			String name = area.getDisplayName();
			cmd.sendMessage(new Message("command.area.name.get", area.getName(), name != null ? name : area.getName()));
			return 1;
		});
		
		arguments.put("displayName", new GreedyStringArgument());
		CommandRegister.register("area", arguments, new Permission("serverguard.command.area"), CommandExecutorType.ALL, (cmd) -> {
			Area area = (Area) cmd.getArg(0);
			String name = (String) cmd.getArg(1);
			area.setDisplayName(name.equals("-") ? null : name);
			cmd.broadcastMessage(new Message("command.area.name.set", area.getName(), name));
			return 1;
		});
	}
	
}