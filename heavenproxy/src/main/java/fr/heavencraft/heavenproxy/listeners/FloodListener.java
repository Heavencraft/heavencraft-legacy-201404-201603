package fr.heavencraft.heavenproxy.listeners;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.ban.BanCommand;
import fr.heavencraft.heavenproxy.commands.KickCommand;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class FloodListener implements Listener
{
	private final static String WARNING = "§d[de Prof. Chen]§r Arrêtes d'écrire le même message.";

	private List<String> toIgnore = new ArrayList<String>();
	private List<String> bannedWords = new ArrayList<String>();

	Map<String, String> _history = new HashMap<String, String>();
	Map<String, Calendar> _timestamps = new HashMap<String, Calendar>();
	Map<String, Integer> _counter = new HashMap<String, Integer>();

	public FloodListener(Plugin plugin)
	{
		plugin.getProxy().getPluginManager().registerListener(plugin, this);

		toIgnore.add("I'm chatting on my iPhone using Minecraft Connect! Check it out, it's free :)".toLowerCase());
		toIgnore.add("I'm chatting on my iPhone using Minecraft Connect!  Check it out, it's free :)".toLowerCase());
		toIgnore.add("I'm chatting on my iPad using Minecraft Connect! Check it out, it's free :)".toLowerCase());
		toIgnore.add("I'm chatting on my iPad using Minecraft Connect!  Check it out, it's free :)".toLowerCase());
		toIgnore.add("I'm chatting on my iPod touch using Minecraft Connect! Check it out, it's free :)".toLowerCase());
		toIgnore.add("I'm chatting on my iPod touch using Minecraft Connect!  Check it out, it's free :)".toLowerCase());
		toIgnore.add("connected with an iPhone using MineChat".toLowerCase());
		toIgnore.add("connected with an iPad using MineChat".toLowerCase());
		toIgnore.add("connected with an iPod touch using MineChat".toLowerCase());
		toIgnore.add("Connected via \"MC Chat\" for Android!".toLowerCase());

		bannedWords.add("batar");	// Batard, batar
		bannedWords.add("connass");
		bannedWords.add("encul");	// encule, enculé, enculer
		bannedWords.add("fuck");	// fuck, fucking, fucktard, motherfucker
		bannedWords.add("salau");	// salaud
		bannedWords.add("salop");	// salop, salope, salopard

		bannedWords.add("putin");
		bannedWords.add("putain");
		bannedWords.add("bitch");
		bannedWords.add("biatch");
		bannedWords.add("dumbass");
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onChat(ChatEvent event)
	{
		if (event.isCancelled())
			return;

		if (!(event.getSender() instanceof ProxiedPlayer))
			return;

		String message = event.getMessage().toLowerCase();

		// Si c'est une commande autre que /m, /me ou /send -> on fait rien
		if (event.isCommand() && !message.startsWith("/m ") && !message.startsWith("/msg ")
				&& !message.startsWith("/t ") && !message.startsWith("/tell ") && !message.startsWith("/w ")
				&& !message.startsWith("/me ") && !message.startsWith("/send "))
			return;

		// Si c'est un des messages à filter -> on annule le message
		if (toIgnore.contains(message))
		{
			event.setCancelled(true);
			return;
		}

		ProxiedPlayer player = (ProxiedPlayer) event.getSender();
		String playerName = player.getName();

		// Mots interdits
		for (String bannedWord : bannedWords)
			if (message.contains(bannedWord))
			{
				KickCommand.kickPlayer(player, "le Prof. Chen", "Insulte");
				event.setCancelled(true);
				return;
			}
		
		// BUGFIX : Si c'est pour la banque Semi-RP -> on fait rien
		if (player.getServer().getInfo().getName().equalsIgnoreCase("semirp") && Utils.isInteger(message))
		{
			return;
		}

		String lastMessage = _history.get(playerName);
		Calendar lastTimestamp = _timestamps.get(playerName);

		if (lastTimestamp != null)
			lastTimestamp.add(Calendar.SECOND, 15);

		if (lastMessage != null && lastMessage.equals(message) && lastTimestamp.after(Calendar.getInstance()))
		{
			event.setCancelled(true);

			Integer counter = _counter.get(playerName) + 1;
			_counter.put(playerName, counter);

			switch (counter) {
			case 1:
			case 2:
				// KickCommand.kickPlayer(player, "le Prof. Chen",
				// "Ce n'est pas le moment de flooder !");
				player.sendMessage(WARNING);
				break;
			case 3:
				BanCommand.banPlayer(playerName, "le Prof. Chen",
						String.format("Flood abusif : '%1$s'", message));
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
