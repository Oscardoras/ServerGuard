package org.bukkitplugin.serverguard.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkitplugin.serverguard.Message;
import org.bukkitplugin.serverguard.targets.Area;
import org.bukkitutils.command.v1_15_V1.CustomArgument;

public class AreaArgument extends CustomArgument<Area> {

	public AreaArgument() {
		withSuggestionsProvider((cmd) -> {
			List<String> list = new ArrayList<String>();
			for (Area area : Area.getAreas()) list.add(area.getName());
			return list;
		});
	}

	@Override
	public Area parse(String arg, SuggestedCommand cmd) throws Exception {
		Area area = Area.getArea(arg);
		if (area == null) throw getCustomException(new Message("area.does_not_exist").getMessage(cmd.getLanguage(), arg));
		else return area;
	}
}