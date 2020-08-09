package Cradle.Teleport.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import Cradle.Cradle;
import Cradle.Chat.Misc.ChatFormatter;

public class CommandTeleportAccept implements CommandExecutor {

	private Cradle main = null;
	
	public CommandTeleportAccept(Cradle _main) {
		main = _main;
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		
		// Player-only command.
		if (!(sender instanceof Player)) return true;
		
		// Check if the user has a teleport request
		if (main.tpQueue.containsKey(sender.getName())) {
			
			// Message indicating the request was accepted.
			String result = ChatFormatter.format("&cYou accepted the teleport request.", false, false, "", "");
			sender.sendMessage(result);
			
			String requestAccepted = ChatFormatter.format("&cYour teleport request was accepted.", false, false, "", "");
			Bukkit.getServer().getPlayer(main.tpQueue.get(sender.getName())).sendMessage(requestAccepted);
			
			// Teleport the person who made the request to the person accepting the request.
			Bukkit.getServer().getPlayer(main.tpQueue.get(sender.getName())).teleport((Entity) sender);
		}
		else {
			// No request was found
			String result = ChatFormatter.format("&cYou have no teleport requests.", false, false, "", "");
			sender.sendMessage(result);
			return true;
		}
		
		return true;
	}

}
