package org.bukkitplugin.serverguard.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkitplugin.serverguard.Message;
import org.bukkitplugin.serverguard.targets.Group;
import org.bukkitutils.command.v1_14_3_V1.CustomArgument;

public class GroupArgument extends CustomArgument<Group> {

	public GroupArgument() {
		withSuggestionsProvider((cmd) -> {
			List<String> list = new ArrayList<String>();
			for (Group group : Group.getGroups()) list.add(group.getName());
			return list;
		});
	}

	@Override
	public Group parse(String arg, SuggestedCommand cmd) throws Exception {
		Group group = Group.getGroup(arg);
		if (group == null) throw new CustomArgumentException(new Message("group.does_not_exist").getMessage(cmd.getLanguage(), arg));
		else return group;
	}
}