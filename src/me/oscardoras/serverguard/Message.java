package me.oscardoras.serverguard;

import me.oscardoras.spigotutils.io.TranslatableMessage;

public class Message extends TranslatableMessage {
	
	public Message(String path, String... args) {
		super(ServerGuardPlugin.plugin, path, args);
	}
	
}