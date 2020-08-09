package Cradle.Economy.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Cradle.Cradle;
import Cradle.Chat.Misc.ChatFormatter;

public class CommandBalance implements CommandExecutor {

	private Cradle main = null;
	
	public CommandBalance(Cradle _main) {
		main = _main;
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		
		// Only players have bank accounts.
		if (sender instanceof Player) {
			
			// Check if this player has an account, they should have one by default.
			if (main.EcoBuffer.containsKey(((Player) sender).getUniqueId().toString())) {
				
				String toFormat = main.config.getString("eco-currency-message") + main.getConfig().getString("eco-currency-symbol") + main.EcoBuffer.get(((Player) sender).getUniqueId().toString());
				String result = ChatFormatter.format(toFormat, false, false, "", "");
				sender.sendMessage(result);
			}
			
			// I'm speed-running development leave me alone.
			// This is basically an error and should create an account if they don't have one.
			// TO DO : Make default account if one isn't present.
			else {
				// This will be changed in the future.
				String result = ChatFormatter.format("&cContact an Admin, you should not be seeing this.", false, false, "", "");
				sender.sendMessage(result);
			}
		}
		else {
			// Server Console should use /apay not normal /pay.
			sender.sendMessage("Only Players can use this command.");
		}
		return true;
	}

}
