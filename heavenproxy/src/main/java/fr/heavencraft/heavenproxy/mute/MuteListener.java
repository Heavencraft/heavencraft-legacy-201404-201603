package fr.heavencraft.heavenproxy.mute;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import fr.heavencraft.heavenproxy.Utils;

public class MuteListener implements Listener
{
	private static final String TAG = "[MuteListener] ";

	private final Logger log = Utils.getLogger();
	private final List<String> bannedWords = new ArrayList<String>();

	public MuteListener()
	{
		Utils.registerListener(this);

		bannedWords.add("batar"); // Batard, batar
		bannedWords.add("connass");
		bannedWords.add("encul"); // encule, enculé, enculer
		bannedWords.add("fuck"); // fuck, fucking, fucktard, motherfucker
		bannedWords.add("salau"); // salaud
		bannedWords.add("salop"); // salop, salope, salopard

		bannedWords.add("putin");
		bannedWords.add("putain");
		bannedWords.add("bitch");
		bannedWords.add("biatch");
		bannedWords.add("dumbass");

		log.info(TAG + "Initialized");
	}

	@EventHandler
	public void onPlayerChat(ChatEvent event)
	{
		if (event.isCancelled())
			return;

		// log.info(TAG + event);

		if (!(event.getSender() instanceof ProxiedPlayer))
			return;

		String message = event.getMessage().toLowerCase();

		// Si c'est une commande autre que /m, /me ou /send -> on fait rien
		if (event.isCommand() && !message.startsWith("/m ") && !message.startsWith("/msg ")
				&& !message.startsWith("/t ") && !message.startsWith("/tell ") && !message.startsWith("/w ")
				&& !message.startsWith("/me ") && !message.startsWith("/envoyer "))
			return;

		ProxiedPlayer player = (ProxiedPlayer) event.getSender();
		String playerName = player.getName();

		// Si le joueur est mute
		if (MuteManager.isMuted(playerName))
		{
			event.setCancelled(true);
			return;
		}

		// Mots interdits
		for (String bannedWord : bannedWords)
			if (message.contains(bannedWord))
			{
				MuteManager.mutePlayer(playerName, 5);
				Utils.sendMessage(player, "Vous avez été mute pour {5} minutes.");

				event.setCancelled(true);
				return;
			}
	}
}