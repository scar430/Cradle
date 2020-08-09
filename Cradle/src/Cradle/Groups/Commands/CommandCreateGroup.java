package Cradle.Groups.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Cradle.Cradle;
import Cradle.Chat.Misc.ChatFormatter;

public class CommandCreateGroup implements CommandExecutor {

	private Cradle main = null;
	
	public CommandCreateGroup(Cradle _main) {
		main = _main;
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				String result = ChatFormatter.format("&cYou must specify a name for the group.", false, false, "", "");
				sender.sendMessage(result);
			}
			else {
				sender.sendMessage("You must specify a name for the group.");
			}
		}
		else {
			if (args[0] == "default") {
				if (sender instanceof Player) {
					String result = ChatFormatter.format("Groups can not be labeled as 'default', a default group is already defined.", false, false, "", "");
					sender.sendMessage(result);
				}
				else {
					sender.sendMessage("Groups can not be labeled as 'default', a default group is already defined.");
				}
			}
			else {
				
			}
		}
		
		return true;
	}

}
