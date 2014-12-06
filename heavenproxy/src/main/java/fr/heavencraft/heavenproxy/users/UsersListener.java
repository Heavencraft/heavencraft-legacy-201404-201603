package fr.heavencraft.heavenproxy.users;

import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.chat.ChatManager;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;

public class UsersListener implements Listener
{
	private static final String TAG = "[UsersListener] ";
	private static final Logger log = Utils.getLogger();
	public static final int MINECRAFT_1_8 = 47;

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

		if (event.getConnection().getVersion() != MINECRAFT_1_8)
		{
			event.setCancelled(true);
			event.setCancelReason("§fHeaven§bcraft§r est en 1.8.1.\n\nMerci de vous connecter avec cette version.");

			log.info(TAG + "[onLogin] " + event.getConnection().getName() + " is not in 1.8.1.");
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPostLogin(PostLoginEvent event)
	{
		ProxiedPlayer player = event.getPlayer();

		String uuid = Utils.getUUID(player);
		String name = player.getName();

		try
		{
			User user = UserProvider.getUserByUuid(uuid);
			user.setName(name);
			user.setLastLogin(new Timestamp(new Date().getTime()));

			ChatManager.sendJoinMessage(name, player.getAddress().getAddress(), false);

			if (player.hasPermission("heavencraft.commands.modo"))
			{
				Utils.sendMessage(player, ChatColor.GREEN + "Vous êtes membre du staff, VOTEZ !");
				Utils.sendMessage(player, ChatColor.GREEN + "http://www.mcserv.org/Heavencraftfr_3002.html");
				Utils.sendMessage(player, ChatColor.GREEN + "http://mc-topserv.net/top/serveur.php?serv=46");
			}
		}
		catch (HeavenException ex)
		{
			UserProvider.createUser(uuid, name);

			ChatManager.sendJoinMessage(name, player.getAddress().getAddress(), true);
		}
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent event)
	{
		try
		{
			UserProvider.removeFromCache(UserProvider.getUserByName(event.getPlayer().getName()));
		}
		catch (HeavenException ex)
		{
			ex.printStackTrace();
		}
	}
}