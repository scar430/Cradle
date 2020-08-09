package Cradle.Economy.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Cradle.Cradle;
import Cradle.Chat.Misc.ChatFormatter;

public class CommandAdminPay implements CommandExecutor {
	
	Cradle main = null;
	
	public CommandAdminPay(Cradle _main) {
		main = _main;
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		
		// Lazy way to do this but if args are not present then throw error message.
		if (args.length < 2) {
			// Send error message.
			String result = ChatFormatter.format("&cYou must specify a player and an amount.", false, false, "", "");
			sender.sendMessage(result);
			return false;
		}
		else {
			// Check if player was returned.
			if (Bukkit.getServer().getPlayer(args[0]) != null) {
				
				// Add changes to the players balance.
				main.EcoBuffer.put(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString(), main.EcoBuffer.get(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString()) + Double.parseDouble(args[1]));
				
				// Send message indicating they were given money.
				String result = ChatFormatter.format("You have received " + main.config.get("eco-currency-symbol") + args[1], false, false, "", "");
				Bukkit.getServer().getPlayer(args[0]).sendMessage(result);
				
				// Send message to sender that the money was sent.
				String resultSender = ChatFormatter.format("You sent " + args[0] + ", " + main.config.get("eco-currency-symbol") + args[1], false, false, "", "");
				sender.sendMessage(resultSender);
				return true;
			}
			else {
				String result = ChatFormatter.format("&cPlayer could not be found, are they online?", false, false, "", "");
				sender.sendMessage(result);
				return true;
			}
		}
	}

}
