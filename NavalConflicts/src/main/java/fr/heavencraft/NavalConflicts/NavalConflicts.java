package fr.heavencraft.NavalConflicts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.heavencraft.NavalConflicts.Utils.Severity;
import fr.heavencraft.NavalConflicts.Extras.Menus;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.NCClassManager;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;
import fr.heavencraft.NavalConflicts.Messages.Msgs;
import fr.heavencraft.NavalConflicts.Tools.Files;
import fr.heavencraft.NavalConflicts.commands.CommandManager;
import fr.heavencraft.NavalConflicts.listeners.ChatListener;
import fr.heavencraft.NavalConflicts.listeners.GameMechanicsListener;
import fr.heavencraft.NavalConflicts.listeners.PlayerListener;
import fr.heavencraft.NavalConflicts.listeners.RegisterUnregisterListener;
import fr.heavencraft.NavalConflicts.listeners.ScoreBoardToggleListener;

public class NavalConflicts extends JavaPlugin {
	private static NavalConflicts _instance;
	private static Connection _mainConnection;
	public static Menus Menus;
	
	public static NavalConflicts getInstance() {
		return _instance;
	}

	public static Connection getMainConnection() {
		try {
			if ((_mainConnection == null) || (_mainConnection.isClosed())) {
				_mainConnection = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/mc-db?user=mc-sql&password=xxxxxxxx&zeroDateTimeBehavior=convertToNull&?autoReconnect=true");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			Bukkit.shutdown();
		}
		return _mainConnection;
	}

	@Override
	public void onEnable() {

		Utils.broadcastConsole(Bukkit.getConsoleSender(), Severity.INFO, "Preparation de Naval Conflicts");

		_instance = this;
		
		
		Utils.broadcastConsole(Bukkit.getConsoleSender(), Severity.INFO, "Preparation des configuration.");
		// Creation des fichiers de config
		getConfig().options().copyDefaults(true);
		Files.getArenas().options().copyDefaults(true);
		Files.getShop().options().copyDefaults(true);
		Files.getPlayers().options().copyDefaults(true);
		Files.getMessages().options().copyDefaults(true);
		Files.getGrenades().options().copyDefaults(true);
		Files.getClasses().options().copyDefaults(true);
		Files.getSigns().options().copyDefaults(true);
		Files.saveAll();
				
				
		Utils.broadcastConsole(Bukkit.getConsoleSender(), Severity.INFO, "Preparation des listeners.");
		// Enregistrer les listeners
		new CommandManager();
		new ChatListener();
		new PlayerListener();
		new ScoreBoardToggleListener();
		new RegisterUnregisterListener();
		new GameMechanicsListener();

		Utils.broadcastConsole(Bukkit.getConsoleSender(), Severity.INFO, "Preparation des arenes et menus.");
		//Charger les arènes des configs
		Lobby.loadAllArenas();
		Menus = new Menus();
		
		NCClassManager.loadConfigClasses();
		
		Utils.broadcastConsole(Bukkit.getConsoleSender(), Severity.INFO, "Chargement terminé.");

	}

	@Override
	public void onDisable() {
		Utils.broadcastConsole(Bukkit.getConsoleSender(), Severity.INFO, "Déchargement en cours.");
		NavalConflicts.Menus.destroyAllMenus();
		try
		{
			// On disable reset players with everything from before
			if (!Lobby.getPlayersInGame().isEmpty())
				for (Player p : Bukkit.getOnlinePlayers())
					if (Lobby.isInGame(p))
					{
						p.sendMessage(Msgs.Error_Misc_Plugin_Unloaded.getString());
						NCPlayerManager.getNCPlayer(p).leaveNavalConflicts();
					}
		} catch (Exception e)
		{
			// If theres no one in NC it seems to not be able to find the
			// Lobby class when checking if the game is empty
		}
	}
	
	
	
}
