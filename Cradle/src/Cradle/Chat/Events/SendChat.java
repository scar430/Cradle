package Cradle.Chat.Events;

import org.bukkit.Bukkit; 
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import Cradle.Cradle;
import Cradle.Chat.Misc.ChatFormatter;

public class SendChat implements Listener {

	// This is what will prefix a message (i.e. the message senders name.)
	private String chatPrefix = "";
	
	private Boolean tagEnabled = true; 
	// String to replace the mentions indicator.
	// This is set in the config, do not edit this.
	private String tagcc = "";
	
	private Boolean greenTextEnabled =true;
	// String to replace the greentext indicator.
	// This is set in the config do not edit this.
	private String greenTextcc = "";
	
	// Main instance
	private Cradle main = null;
	
	public SendChat (Cradle _main) {
		
		main = _main;
		
		chatPrefix = (String) main.config.get("chat-player-format");
		
		tagEnabled = (Boolean) main.config.get("chat-tag-enable");
		tagcc = (String) main.config.get("chat-tag");
		
		greenTextEnabled = (Boolean) main.config.get("chat-green-text-enable");
		greenTextcc = (String) main.config.get("chat-green-text");
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		// Cancel the message so the default formatted message isn't sent.
		e.setCancelled(true);
		
		// Message to send.
		String message = chatPrefix;
		
		
		
		// Parse variables.
		// TO DO : Prefix and Suffix only support external plugins as Cradle Groups is not finished yet.
		
		// Check for prefix from Vault
		if (main.getChat().getPlayerPrefix(e.getPlayer()) != null) {
			message = message.replace("%PREFIX", main.getChat().getPlayerPrefix(e.getPlayer()));
		}
		else {
			message = message.replace("%PREFIX", "");
		}
		
		// Check for username and displayname
		message = message.replace("%USERNAME", e.getPlayer().getName());
		message = message.replace("%DISPLAYNAME", e.getPlayer().getDisplayName());
		
		// Check for suffix from Vault
		if (main.getChat().getPlayerSuffix(e.getPlayer()) != null) {
			message = message.replace("%SUFFIX", main.getChat().getPlayerSuffix(e.getPlayer()));
		}
		else {
			message = message.replace("%SUFFIX", "");
		}
		
		// Format message
		String fMessage = ChatFormatter.format(message + e.getMessage(), greenTextEnabled, tagEnabled, tagcc, greenTextcc);
		
		// Broadcast this message to all players.
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			player.sendMessage(fMessage);
		}
	}
}
