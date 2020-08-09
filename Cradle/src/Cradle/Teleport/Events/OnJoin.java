package Cradle.Teleport.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import Cradle.Cradle;

public class OnJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.getPlayer().teleport(Cradle.globalSpawn);
	}
}
