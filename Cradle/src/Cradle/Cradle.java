package Cradle;

import java.io.File;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import Cradle.Chat.Commands.CommandAlert;
import Cradle.Chat.Commands.CommandBroadcast;
import Cradle.Chat.Commands.CommandHelp;
import Cradle.Chat.Commands.CommandPrivateMessage;
import Cradle.Chat.Commands.CommandRules;
import Cradle.Chat.Events.SendChat;
import Cradle.Chat.Events.SendMOTD;
import Cradle.Chat.Events.SendOnlineStatusChange;
import Cradle.Economy.Commands.CommandAdminPay;
import Cradle.Economy.Commands.CommandBalance;
import Cradle.Economy.Commands.CommandPay;
import Cradle.Economy.Events.CreateAccount;
import Cradle.Teleport.Commands.CommandAdminTeleport;
import Cradle.Teleport.Commands.CommandSetGlobalSpawn;
import Cradle.Teleport.Commands.CommandSpawn;
import Cradle.Teleport.Commands.CommandTeleport;
import Cradle.Teleport.Commands.CommandTeleportAccept;
import Cradle.Teleport.Data.DataGlobalSpawn;
import Cradle.Teleport.Data.DataHandler;
import Cradle.Teleport.Events.OnJoin;
import Cradle.Teleport.Events.Respawn;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Cradle extends JavaPlugin {
	
	// *** WARNING : I'm speed-running development, handle with care, some features may break in some situations.
	
	public static final String defaultPath = "plugins//Cradle//data//";
	
	public final String version = "Alpha 1.0 [EXPERIMENTAL]";
	
	// Take a wild guess what this is :^)
	public FileConfiguration config = this.getConfig();
	
	/* VAULT STUFF */
	
	private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
	
	/* TP STUFF */
	// All current tp requests on a per-user basis.
    // One tp request per user.
	public HashMap<String,String> tpQueue = new HashMap<String,String>();
	
	/* CRADLE SPECIFIC BUFFERS */
	
	// All loaded groups.
	// Group : Permissions
	public HashMap<String, ArrayList<String>> groupPerms = new HashMap<String, ArrayList<String>>();
	// Group : Users
	public HashMap<String, ArrayList<String>> groupUsers = new HashMap<String, ArrayList<String>>();
	
	// Economy Buffer, Cradle specific, this is not for third party plugins or whatever bullshit you included with Vault.
	// UUID:Balance
	public HashMap<String, Double> EcoBuffer = new HashMap<String, Double>();
	
	public static Location globalSpawn = null;
	
	@Override
	public void onEnable() {
		
		/* Start message */
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "\r\n   _____ _____            _____  _      ______ \r\n" + 
				"  / ____|  __ \\     /\\   |  __ \\| |    |  ____|\r\n" + 
				" | |    | |__) |   /  \\  | |  | | |    | |__   \r\n" + 
				" | |    |  _  /   / /\\ \\ | |  | | |    |  __|  \r\n" + 
				" | |____| | \\ \\  / ____ \\| |__| | |____| |____ " + "	" + ChatColor.RED + "Version : " + version + ChatColor.GREEN + "\r\n" + 
				"  \\_____|_|  \\_\\/_/    \\_\\_____/|______|______|	" + ChatColor.GOLD + "Author  : scar430" +
				"\r\n");
		
		/* CONFIG */
		
		this.saveDefaultConfig();
		
		/* DATA */
		
		File file = new File(defaultPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		if (DataHandler.loadData(defaultPath + "GlobalSpawn.yml") != null) {
			DataGlobalSpawn spawnLoc = DataHandler.loadData(defaultPath + "GlobalSpawn.yml");
			globalSpawn = new Location(Bukkit.getWorld(spawnLoc.worldname), spawnLoc.x, spawnLoc.y, spawnLoc.z);
			
		}
		
		/* VAULT */
		
        setupPermissions();
        setupChat();
		
		/* CHAT */
		
		if (config.getBoolean("chat-enabled") == true) {
			
			// Commands 
			
			this.getCommand("broadcast").setExecutor(new CommandBroadcast(config));
			this.getCommand("alert").setExecutor(new CommandAlert(config));
			this.getCommand("pm").setExecutor(new CommandPrivateMessage(config));
			this.getCommand("rules").setExecutor(new CommandRules(config));
			this.getCommand("help").setExecutor(new CommandHelp(config));
			
			// Events
			
			getServer().getPluginManager().registerEvents(new SendMOTD(config), this);
			getServer().getPluginManager().registerEvents(new SendChat(this), this);
		    getServer().getPluginManager().registerEvents(new SendOnlineStatusChange(config), this);
		}
		
		/* ECONOMY */
		
		if (config.getBoolean("eco-enabled") == true) {
			
			// Commands
			
			this.getCommand("balance").setExecutor(new CommandBalance(this));
			this.getCommand("pay").setExecutor(new CommandPay(this));
			this.getCommand("apay").setExecutor(new CommandAdminPay(this));
			
			// Events
			
			getServer().getPluginManager().registerEvents(new CreateAccount(this, config), this);
		}
		
		/* TELEPORT */
		
		if (config.getBoolean("tp-enabled") == true) {
			
			// Events
			
			getServer().getPluginManager().registerEvents(new Respawn(config.getBoolean("hard-respawn")), this);
			getServer().getPluginManager().registerEvents(new OnJoin(), this);
			
			// Commands
			
			this.getCommand("atp").setExecutor(new CommandAdminTeleport());
			this.getCommand("tp").setExecutor(new CommandTeleport(this));
			this.getCommand("tpaccept").setExecutor(new CommandTeleportAccept(this));
			this.getCommand("setglobalspawn").setExecutor(new CommandSetGlobalSpawn(this));
			this.getCommand("spawn").setExecutor(new CommandSpawn(this));
		}
	}
	
	@Override
	public void onDisable() {
		
	}
	
	/* VAULT */
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    
    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
    
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    
    public static Economy getEconomy() {
        return econ;
    }
    
    public static Permission getPermissions() {
        return perms;
    }
    
    public static Chat getChat() {
        return chat;
    }
}
