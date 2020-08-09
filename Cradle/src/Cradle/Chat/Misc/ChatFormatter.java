package Cradle.Chat.Misc;

import net.md_5.bungee.api.ChatColor;

public class ChatFormatter {

	public static String format(String toFormat, Boolean greenTextEnabled, Boolean tagEnabled, String mentionChatColor, String greenTextColor) {
		
		String message = toFormat;
		
		if (greenTextEnabled == true || tagEnabled == true) {
			
			// Split so we can individually look at each word.
			String[] toProcess = message.split(" ");
			
			// Formatting player tags.
			if (tagEnabled == true) {
				
				// We have to check each word.
				for(int i = 0; i < toProcess.length; i++) {
					
					// Read for tag indicator.
					if (toProcess[i].startsWith("@")) {
						
						String temp = mentionChatColor;
						temp += toProcess[i] + "&r";
						
						// This will be processed into a normal message later.
						toProcess[i] = temp;
					}
				}
			}
			
			if (greenTextEnabled == true) {
				// We have to check each word.
				for(int i = 0; i < toProcess.length; i++) {
					
					// Read for tag indicator.
					if (toProcess[i].startsWith(">")) {
						
						String temp = greenTextColor;
						temp += toProcess[i];
						
						// This will be processed into a normal message later.
						toProcess[i] = temp;
					}
				}
			}
			
			message = "";
			
			// Process the message into something readable
			for (String word : toProcess) {
				message += word + " ";
			}
		}
		
		String formatted = ChatColor.translateAlternateColorCodes('&', message);
		
		return formatted;
	}
}
