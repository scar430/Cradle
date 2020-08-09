package Cradle.Chat.Events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import Cradle.Chat.Misc.ChatFormatter;

public class SendOnlineStatusChange implements Listener {

	String joinMsg = "";
	String quitMsg = "";
	
	public SendOnlineStatusChange(FileConfiguration config) {
		joinMsg = (String) config.get("chat-join-msg");
		quitMsg = (String) config.get("chat-quit-msg");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		// Message to send.
		String message = joinMsg;
		
		// Implement variables.
		message = message.replace("%PREFIX", "");
		message = message.replace("%USERNAME", e.getPlayer().getName());
		message = message.replace("%DISPLAYNAME", e.getPlayer().getDisplayName());
		message = message.replace("%SUFFIX", "");
		
		String result = ChatFormatter.format(message, false, false, "", "");
		
		e.setJoinMessage(result);
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		
		// Message to send.
		String message = quitMsg;
		
		// Implement variables.
		message = message.replace("%PREFIX", "");
		message = message.replace("%USERNAME", e.getPlayer().getName());
		message = message.replace("%DISPLAYNAME", e.getPlayer().getDisplayName());
		message = message.replace("%SUFFIX", "");
		
		String result = ChatFormatter.format(message, false, false, "", "");
		
		e.setQuitMessage(result);
	}
}
