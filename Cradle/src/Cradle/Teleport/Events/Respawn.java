package Cradle.Teleport.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import Cradle.Cradle;

public class Respawn implements Listener {

	private boolean doRespawn = false;
	
	public Respawn(boolean _doRespawn) {
		doRespawn = _doRespawn;
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		if (doRespawn == false) return;
		e.getPlayer().teleport(Cradle.globalSpawn);
	}
}