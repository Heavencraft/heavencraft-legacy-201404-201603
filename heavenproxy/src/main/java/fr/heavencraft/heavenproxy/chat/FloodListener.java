package fr.heavencraft.heavenproxy.chat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.ban.BanCommand;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;

public class FloodListener implements Listener
{
	private static final String TAG = "[FloodListener] ";

	private final static String WARNING = "§d[de Prof. Chen]§r Arrête d'écrire le même message.";

	private final Logger log = Utils.getLogger();

	private final List<String> toIgnore = new ArrayList<String>();
	private final Map<String, String> _history = new HashMap<String, String>();
	private final Map<String, Calendar> _timestamps = new HashMap<String, Calendar>();
	private final Map<String, Integer> _counter = new HashMap<String, Integer>();

	public FloodListener()
	{
		Utils.registerListener(this);

		// I'm chatting on my iPhone using Minecraft Connect! Check it out, it's
		// free :)
		// I'm chatting on my iPhone using Minecraft Connect! Check it out, it's
		// free :)
		// I'm chatting on my iPad using Minecraft Connect! Check it out, it's
		// free :)
		// I'm chatting on my iPad using Minecraft Connect! Check it out, it's
		// free :)
		// I'm chatting on my iPod touch using Minecraft Connect! Check it out,
		// it's free :)
		// I'm chatting on my iPod touch using Minecraft Connect! Check it out,
		// it's free :)
		toIgnore.add("minecraft connect");

		// I'm chatting on my iPhone using MC Connect for Minecraft! Check it
		// out, it's free :)
		// I'm chatting on my iPad using MC Connect for Minecraft! Check it out,
		// it's free :)
		// I'm chatting on my iPod touch using MC Connect for Minecraft! Check
		// it out, it's free :)
		toIgnore.add("mc connect");

		// connected with an iPhone using MineChat
		// connected with an iPad using MineChat
		// connected with an iPod touch using MineChat
		toIgnore.add("minechat");

		// Connected via "MC Chat" for Android!
		toIgnore.add("mc chat");

		// Connected from Hyperchat v2.6.04 on a Samsung GT-I9505
		// Connected from Hyperchat v2.6.07 on a HTC One S
		toIgnore.add("hyperchat");

		log.info(TAG + "Initialized");
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onChat(ChatEvent event)
	{
		if (event.isCancelled())
			return;

		if (!(event.getSender() instanceof ProxiedPlayer))
			return;

		final String message = event.getMessage().toLowerCase();

		// Si c'est une commande autre que /m, /me ou /send -> on fait rien
		if (event.isCommand() && !message.startsWith("/m ") && !message.startsWith("/msg ") && !message.startsWith("/t ")
				&& !message.startsWith("/tell ") && !message.startsWith("/w ") && !message.startsWith("/me ")
				&& !message.startsWith("/envoyer "))
			return;

		// Si c'est un des messages à filter -> on annule le message
		for (final String keyword : toIgnore)
		{
			if (message.contains(keyword))
			{
				event.setCancelled(true);
				return;
			}
		}

		final ProxiedPlayer player = (ProxiedPlayer) event.getSender();
		final String playerName = player.getName();

		// BUGFIX : Si c'est pour la banque Semi-RP -> on fait rien
		if (player.getServer().getInfo().getName().equalsIgnoreCase("semirp") && Utils.isInteger(message))
		{
			return;
		}

		final String lastMessage = _history.get(playerName);
		final Calendar lastTimestamp = _timestamps.get(playerName);

		if (lastTimestamp != null)
			lastTimestamp.add(Calendar.SECOND, 15);

		if (lastMessage != null && lastMessage.equals(message) && lastTimestamp.after(Calendar.getInstance()))
		{
			event.setCancelled(true);

			final Integer counter = _counter.get(playerName) + 1;
			_counter.put(playerName, counter);

			switch (counter)
			{
				case 1:
				case 2:
					// KickCommand.kickPlayer(player, "le Prof. Chen",
					// "Ce n'est pas le moment de flooder !");
					Utils.sendMessage(player, WARNING);
					break;
				case 3:
					try
					{
						BanCommand.banPlayer(playerName, "le Prof. Chen", String.format("Flood abusif : '%1$s'", message));
					}
					catch (final HeavenException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
			}
		}
		else
		{
			_history.put(playerName, message);
			_counter.put(playerName, 0);
		}

		_timestamps.put(playerName, Calendar.getInstance());
	}
}
