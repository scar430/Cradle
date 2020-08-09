package Cradle.Chat.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class CommandHelp implements CommandExecutor {

	String help = "";
	
	public CommandHelp(FileConfiguration config) {
		help = config.getString("chat-help");
	}
	
	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(help);
		return true;
	}

}
