package Cradle.Chat.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CommandBroadcast implements CommandExecutor {

	// Base string
	private String broadcast = "";
	
	public CommandBroadcast(FileConfiguration config) {
		broadcast = (String) config.get("chat-broadcast");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		// Make sure the Sender sent something to be broadcasted
		if(args.length > 0) {
			
			// Consolidate all args into one String
			for (String arg : args) {
				broadcast += arg + " ";
			}
			
			// Apply color codes and store message.
			String broadcastMessage = ChatColor.translateAlternateColorCodes('&', broadcast);
			
			// Send the message to broadcast to all players.
			for(Player player : Bukkit.getServer().getOnlinePlayers()) {
				player.sendMessage(broadcastMessage);
			}
			return true;
		}
		else {
			sender.sendMessage("\u00A7cYou must enter a message to broadcast.");
			return true;
		}
	}
}
