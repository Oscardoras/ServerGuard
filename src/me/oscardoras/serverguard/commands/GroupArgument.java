package me.oscardoras.serverguard.commands;

import java.util.ArrayList;
import java.util.List;

import me.oscardoras.serverguard.Message;
import me.oscardoras.serverguard.targets.Group;
import me.oscardoras.spigotutils.command.v1_16_1_V1.CustomArgument;

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
		if (group == null) throw getCustomException(new Message("group.does_not_exist").getMessage(cmd.getLanguage(), arg));
		else return group;
	}
}