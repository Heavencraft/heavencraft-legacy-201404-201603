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

		// Insultes
		bannedWords.add("batard");
		bannedWords.add("biatch");
		bannedWords.add("bitch");
		bannedWords.add("bounioul");
		bannedWords.add("calice");
		bannedWords.add("câlice");
		bannedWords.add("caliss");
		bannedWords.add("calisse");
		bannedWords.add("catin");
		bannedWords.add("ciboire");
		bannedWords.add("conasse");
		bannedWords.add("connard");
		bannedWords.add("connasse");
		bannedWords.add("cris");
		bannedWords.add("crisse");
		bannedWords.add("dumbass");
		bannedWords.add("encule");
		bannedWords.add("enculé");
		bannedWords.add("enculer");
		bannedWords.add("fdp");
		bannedWords.add("fuck");
		bannedWords.add("fucking");
		bannedWords.add("fucktard");
		bannedWords.add("gtfo");
		bannedWords.add("katin");
		bannedWords.add("lopette");
		bannedWords.add("marde");
		bannedWords.add("merde");
		bannedWords.add("merdeux");
		bannedWords.add("motherfucker");
		bannedWords.add("niquer");
		bannedWords.add("osti");
		bannedWords.add("pd");
		bannedWords.add("pédé");
		bannedWords.add("putain");
		bannedWords.add("putin");
		bannedWords.add("pute");
		bannedWords.add("salaud");
		bannedWords.add("salop");
		bannedWords.add("salopard");
		bannedWords.add("salope");
		bannedWords.add("stfu");
		bannedWords.add("tabarnac");
		bannedWords.add("tabarnak");
		bannedWords.add("tabarnaque");
		bannedWords.add("tbk");
		bannedWords.add("tg");
		bannedWords.add("tageule");
		bannedWords.add("tayeul");
		bannedWords.add("tayeule");

		// Serveurs
		bannedWords.add("desticraft");
		bannedWords.add("historycraft");
		bannedWords.add("minefield");
		bannedWords.add("thecraft");

		log.info(TAG + "Initialized");
	}

	@EventHandler
	public void onPlayerChat(ChatEvent event)
	{
		if (event.isCancelled())
			return;

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
		for (String word : message.split(" "))
			for (String bannedWord : bannedWords)
				if (word.equalsIgnoreCase(bannedWord))
				{
					MuteManager.mutePlayer(playerName, 5);
					Utils.sendMessage(player, "Vous avez été mute pour {5} minutes par {le Prof. Chen}.");

					event.setCancelled(true);
					return;
				}
	}
}