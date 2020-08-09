package Cradle.Teleport.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Cradle.Cradle;
import Cradle.Chat.Misc.ChatFormatter;
import Cradle.Teleport.Data.DataGlobalSpawn;
import Cradle.Teleport.Data.DataHandler;

public class CommandSetGlobalSpawn implements CommandExecutor {

	private Cradle main = null;
	private String filePath = "";
	
	public CommandSetGlobalSpawn(Cradle _main) {
		main = _main;
		filePath = Cradle.defaultPath + "GlobalSpawn.yml";
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		
		// This CURRENTLY a player-only command.
		if (sender instanceof Player) {
			Player player = (Player) sender;
			try {
				DataGlobalSpawn saveThis = new DataGlobalSpawn(player.getWorld().getName(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
				DataHandler gData = new DataHandler(filePath, saveThis);
				
				main.globalSpawn = player.getLocation();
				
				String result = ChatFormatter.format("&cCreated Global Spawn at " + player.getLocation().getX() + "," + player.getLocation().getY() + "," + player.getLocation().getZ(), false, false, "", "");
				sender.sendMessage(result);
			}
			catch(Error e) {
				String result = ChatFormatter.format("&cFailed to create spawn.", false, false, "", "");
				sender.sendMessage(result);
			}
		}
		else {
			sender.sendMessage("This is a player-only command.");
		}
		
		return true;
	}

}
