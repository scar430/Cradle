package Cradle.Groups.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import Cradle.Cradle;

public class AssignDefault implements Listener {

	Cradle main;
	
	public AssignDefault(Cradle _main) {
		main = _main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (main.groupUsers.get("default").contains(e.getPlayer().getUniqueId().toString())) {
			
		}
		else {
			
		}
	}
}
