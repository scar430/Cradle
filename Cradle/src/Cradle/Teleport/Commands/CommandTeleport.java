package Cradle.Teleport.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import Cradle.Cradle;
import Cradle.Chat.Misc.ChatFormatter;

public class CommandTeleport implements CommandExecutor {

	private Cradle main = null;
	
	public CommandTeleport(Cradle _main) {
		main = _main;
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		
		// This is a player-only command
		if(!(sender instanceof Player)) return true;
		
		if (args.length == 0) {
			String result = ChatFormatter.format("&cYou need to specify a player.", false, false, "", "");
			sender.sendMessage(result);
			return true;
		}
		else {
			// Check if player is online.
			if (Bukkit.getServer().getPlayer(args[0]) != null) {
				
				// Message indicating tp request was sent.
				String result = ChatFormatter.format("&cYou sent a teleport request to " + args[0], false, false, "", "");
				sender.sendMessage(result);
				
				String recipientResult = ChatFormatter.format("&c" + sender.getName() + " sent you a teleport request.", false, false, "", "");
				sender.sendMessage(recipientResult);
				
				// Addd request to queue
				main.tpQueue.put(args[0], sender.getName());
				
				// Schedule task for teleport request to expire.
				BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		        scheduler.scheduleSyncDelayedTask(main, new Runnable() {
		            @Override
		            public void run() {
		            	// Remove the teleport request
		                main.tpQueue.remove(Bukkit.getServer().getPlayer(args[0]).getName().toString());
		            }
		        }, 60000);
		        return true;
			}
			else {
				// Error message for player not found.
				String result = ChatFormatter.format("&cPlayer could not be found, are they online?.", false, false, "", "");
				sender.sendMessage(result);
				return true;
			}
		}
	}

}
