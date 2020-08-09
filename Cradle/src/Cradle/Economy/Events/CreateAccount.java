package Cradle.Economy.Events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import Cradle.Cradle;

public class CreateAccount implements Listener {

	Cradle main = null;
	FileConfiguration config = null;
	
	public CreateAccount(Cradle _main, FileConfiguration _config) {
		main = _main;
		config = _config;
	}
	
	@EventHandler
	public void createAccount(PlayerJoinEvent e) {
		// Check if player already has an account, if not then add one.
		if (!main.EcoBuffer.containsKey(e.getPlayer().getUniqueId().toString())) {
			// Add their account to the economy buffer.
			main.EcoBuffer.put(e.getPlayer().getUniqueId().toString(), Double.parseDouble((String) config.get("eco-initial-balance")));
		}
	}
}
