package Cradle.Chat.Events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;

public class SendMOTD implements Listener {
	
	String motd = "";
	
	public SendMOTD(FileConfiguration config) {
		String temp = (String) config.get("motd");
		motd = ChatColor.translateAlternateColorCodes('&', temp);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.getPlayer().sendMessage(motd);
	}
}
