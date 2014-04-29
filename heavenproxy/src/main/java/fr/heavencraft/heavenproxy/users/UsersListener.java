package fr.heavencraft.heavenproxy.users;

import java.util.logging.Logger;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.ProtocolConstants;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.ban.BanManager;
import fr.heavencraft.heavenproxy.chat.ChatManager;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;

public class UsersListener implements Listener
{
	private static final String TAG = "[UsersListener] ";
	
	private final Logger log = Utils.getLogger();
	
	public UsersListener()
	{
		Utils.registerListener(this);

		log.info(TAG + "Initialized");
	}
	

	@EventHandler
	public void onLogin(LoginEvent event)
	{
		if (event.isCancelled())
			return;
		
		log.info(TAG + event);
		
		if (event.getConnection().getVersion() != ProtocolConstants.MINECRAFT_1_7_2)
		{
			event.setCancelled(true);
			event.setCancelReason("§fHeaven§bcraft§r est en 1.7.5.\n\nMerci de vous connecter avec cette version.");
		}
	}
	
	@EventHandler
	public void onPostLogin(PostLoginEvent event)
	{
		log.info(TAG + event);
		
		ProxiedPlayer player = event.getPlayer();
		String name = player.getName();

		try
		{
			UsersManager.getUserByName(name);
			UsersManager.updateUser(player);

			ChatManager.sendJoinMessage(name, player.getAddress().getAddress(), false);
			
			if (player.hasPermission("heavencraft.commands.modo"))
			{
				Utils.sendMessage(player, ChatColor.GREEN + "Vous êtes membre du staff, VOTEZ !");
				Utils.sendMessage(player, ChatColor.GREEN +"http://mc-topserv.net/top/serveur.php?serv=46");
			}
		}
		catch (HeavenException ex)
		{
			UsersManager.createUser(player);

			ChatManager.sendJoinMessage(name, player.getAddress().getAddress(), true);
		}
	}
}