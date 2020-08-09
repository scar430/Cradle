package Cradle.Teleport.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Cradle.Chat.Misc.ChatFormatter;

public class CommandAdminTeleport implements CommandExecutor {

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		
		// Why the fuck would you send this from a console.
		if (!(sender instanceof Player)) return true;
		
		if (args.length == 0) {
			String result = ChatFormatter.format("&cYou need to specify a player.", false, false, "", "");
			sender.sendMessage(result);
			return true;
		}
		else {
			// Null player check.
			if (Bukkit.getServer().getPlayer(args[0]) == null) {
				
				// Player wasn't found, send error message.
				String result = ChatFormatter.format("&cPlayer could not be found, are they online?", false, false, "", "");
				sender.sendMessage(result);
				return true;
			}
			else {
				// Store this cause clutter.
				Player user = Bukkit.getServer().getPlayer(args[0]);
				
				// Tp the sender to the specified player.
				Player sUser = (Player) sender;

				// Teleport sender.
				sUser.teleport(user);
				
				// Send message indicating what happened
				String result = ChatFormatter.format("You force teleported to " + args[0] + ".", false, false, "", "");
				sender.sendMessage(result);
				
				return true;
			}
		}
	}

}
