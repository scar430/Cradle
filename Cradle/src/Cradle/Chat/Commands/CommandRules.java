package Cradle.Chat.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class CommandRules implements CommandExecutor {

	String rules = "";
	
	public CommandRules(FileConfiguration config) {
		rules = (String) config.get("chat-rules");
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(rules);
		return true;
	}

}
