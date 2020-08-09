package Cradle.Teleport.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Cradle.Cradle;

public class CommandSpawn implements CommandExecutor {

	private Cradle main = null;
	
	public CommandSpawn(Cradle _main) {
		main = _main;
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		
		
		Location spawn = main.globalSpawn;
		
		if(sender instanceof Player) {
			if (spawn == null) {
				/*String result = ChatFormatter.format("&cNo spawn is available", false, false, "", "");
				sender.sendMessage(result);*/
			}
			else {
				((Player) sender).teleport(spawn);
			}
		}
		else {
			sender.sendMessage("This is a player-only command.");
		}
		return true;
	}

}
