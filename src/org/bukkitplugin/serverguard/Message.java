package org.bukkitplugin.serverguard;

import org.bukkitutils.io.TranslatableMessage;

public class Message extends TranslatableMessage {
	
	public Message(String path, String... args) {
		super(ServerGuardPlugin.plugin, path, args);
	}
	
}