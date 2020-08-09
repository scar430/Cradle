package Cradle.Economy.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Cradle.Cradle;
import Cradle.Chat.Misc.ChatFormatter;

public class CommandPay implements CommandExecutor {

	private Cradle main = null;
	
	public CommandPay(Cradle _main) {
		main = _main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		// Server should not be using this, as of 7/21/2020. 
		// This will throw an error since the server does not have an account since it technically never "joined" the game.
		
		// Is player check. 
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can use this command.");
			return true;
		}
		
		try {
			// Lazy way of checking if the args are present.
			if (args.length < 2) {
				// Send error message cause the command sender was a dope.
				String result = ChatFormatter.format("&cYou must specify a player and an amount.", false, false, "", "");
				sender.sendMessage(result);
				return true;
			}
			else
			{
				// Check if player is present.
				if(Bukkit.getServer().getPlayer(args[0]) == null) {
					// Player not present, send error message.
					String result = ChatFormatter.format("&cPlayer could not be found, are they online?.", false, false, "", "");
					sender.sendMessage(result);
					return true;
				}
				else {
					
					// Save these cause other whys it is insane trying to read this.
					String recipientID = Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString();
					Player recipient = Bukkit.getServer().getPlayer(args[0]);
					
					// Check for account, kinda lazily made. If the account isn't present it should make one by default but doesn't currently. Fuck it.
					if (main.EcoBuffer.containsKey(recipientID)) {
						
						// Check if the player has the funds for this transaction.
						if (main.EcoBuffer.get(Bukkit.getPlayer(sender.getName()).getUniqueId().toString()) - Double.parseDouble(args[1]) > 0.0) {
							
							// Make appropriate changes to the players account.
							main.EcoBuffer.put(recipientID, main.EcoBuffer.get(recipientID) + Double.parseDouble(args[1]));
							
							// Tell the recipient of the payment that they were paid.
							String resultReceived = ChatFormatter.format(sender.getName() + " has paid you " + main.config.getString("eco-currency-symbol") + args[1], false, false, "", "");
							recipient.sendMessage(resultReceived);
							
							// Tell the payer that they paid the person.
							String resultSent = ChatFormatter.format("You paid " + recipient.getDisplayName() + " " + main.config.get("eco-currency-symbol") + args[1], false, false, "", "");
							recipient.sendMessage(resultSent);
						}
						else {
							// Player didn't have the funds, send message indicating so to this broke BITCH.
							String result = ChatFormatter.format("&cYou don't have the funds for that transaction.", false, false, "", "");
							sender.sendMessage(result);
						}
					}
					return true;
				}
			}
		}
		// This might be thrown, oh well.
		catch (Error e){
			return false;
		}
	}
}
