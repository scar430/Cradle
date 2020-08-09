package Cradle.Chat.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CommandAlert implements CommandExecutor {

	private String alert = "";
	
	public CommandAlert(FileConfiguration config) {
		alert = (String) config.get("chat-alert");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		try {
			// If the player did not input 1 argument send error.
			if (args.length == 0) {
				sender.sendMessage("\u00A7cYou must specify a player and a message.");
				return true;
			}
			
			// Player we want to alert
			Player player = null;
			
			// Check that the player is present
			if (Bukkit.getServer().getPlayer(args[0]) != null) {
				
				// Store player since they're present.
				player = Bukkit.getServer().getPlayer(args[0]);
				
				// Check if a message was specified.
				if (args.length > 1) {
					
					// Consolidate all args into one String
					for (int i = 1; i < args.length; i++) {
						alert += args[i] + " ";
					}
					
					// Apply color codes to the alert message and store it.
					String alertMessage = ChatColor.translateAlternateColorCodes('&', alert);
					
					// Send alert.
					player.sendMessage(alertMessage);
					return true;
				}
				else {
					sender.sendMessage("\u00A7cYou must specify a message.");
					return true;
				}
			}
			else {
				// Player could not be found, probably offline.
				sender.sendMessage("\u00A7cPlayer could not be found or is offline.");
				return true;
			}
		}
		catch (Exception e){
			return false;
		}
	}
}
