package Cradle.Chat.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CommandPrivateMessage implements CommandExecutor {
	
	private String received = "";
	private String sent = "";
	
	public CommandPrivateMessage(FileConfiguration config) {
		received = (String) config.get("chat-private-message-received");
		sent = (String) config.get("chat-private-message-sent");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		try {
			// Check if the user parsed in any arguments at all.
			if (args.length == 0) {
				sender.sendMessage("\u00A7cYou must specify a player.");
				return true;
			}
			
			// Check if the user parsed in any message.
			if (args.length < 2) {
				sender.sendMessage("\u00A7cYou must specify a message to send.");
				return true;
			}
			
			// Use this to store the player who is being messaged.
			Player receiver = null;
			
			// Check if messaged player is present
			if (Bukkit.getServer().getPlayer(args[0]) != null) {
				
				// Store the user being messaged.
				receiver = Bukkit.getServer().getPlayer(args[0]);
				
				// Start the base of our message. 
				// This will be used to consolidate all arguments into one message.
				// * TO DO : Make the base color configurable.
				/*
				String mReciever = "\u00A7r" + sender.getName() + "\u00A7e -> \u00A7r" + "me" + " \u00A77> \u00A7r";
				String mSender = "\u00A7r" + "me" + "\u00A7e -> \u00A7r" + reciever.getName() + " \u00A77> \u00A7r";
				*/
				
				String mReceiver = received.replace("%SENDER", sender.getName());
				String mSender = sent.replace("%RECEIVED", receiver.getName());
				
				String message = "";
				
				// Consolidate all args into one String
				for (int i = 1; i < args.length; i++) {
					message += args[i] + " ";
				}
				
				// Apply color codes.
				String receivedMessage = ChatColor.translateAlternateColorCodes('&', mReceiver + message);
				String senderMessage = ChatColor.translateAlternateColorCodes('&', mSender + message);
				
				// The message the receiver will get.
				receiver.sendMessage(receivedMessage);
				
				// The message the sender will get.
				sender.sendMessage(senderMessage);
				
				return true;
			}
			else {
				sender.sendMessage("\u00A7cPlayer could not be found or is offline.");
				return true;
			}
		}
		catch(Error e) {
			return false;
		}
	}

}
