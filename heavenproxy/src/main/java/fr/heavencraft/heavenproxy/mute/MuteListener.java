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
	private final List<String> replaceWords = new ArrayList<String>();

	public MuteListener()
	{
		Utils.registerListener(this);

		// Insultes
		bannedWords.add("batard");
		bannedWords.add("biatch");
		bannedWords.add("betch");
		bannedWords.add("betsch");
		bannedWords.add("betsh");
		bannedWords.add("bitch");
		bannedWords.add("bite");
		bannedWords.add("bounioul");
		bannedWords.add("catin");
		bannedWords.add("ciboire");
		bannedWords.add("con");
		bannedWords.add("conasse");
		bannedWords.add("connard");
		bannedWords.add("connasse");
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
		bannedWords.add("tg");
		bannedWords.add("tageule");
		bannedWords.add("tayeul");
		bannedWords.add("tayeule");

		// Serveurs
		bannedWords.add("desticraft");
		bannedWords.add("historycraft");
		bannedWords.add("minefield");
		bannedWords.add("thecraft");

		// Mots de remplacement
		replaceWords.add("poney");
		replaceWords.add("poulette");
		replaceWords.add("fraise");
		replaceWords.add("lait demi-écrémé");
		replaceWords.add("pika");
		replaceWords.add("[censuré]");
		replaceWords.add("cuniculiculture");
		replaceWords.add("compote");
		replaceWords.add("balançoire");
		replaceWords.add("pastèque");

		log.info(TAG + "Initialized");
	}

	@EventHandler
	public void onPlayerChat(ChatEvent event)
	{
		if (event.isCancelled())
			return;

		if (!(event.getSender() instanceof ProxiedPlayer))
			return;

		final String message = event.getMessage().toLowerCase();
		final boolean isPrivateMessage = message.startsWith("/m ") || message.startsWith("/msg ")
				|| message.startsWith("/t ") || message.startsWith("/tell ") || message.startsWith("/w ");

		// Si c'est une commande autre que /m, /me ou /send -> on fait rien
		if (event.isCommand() && !isPrivateMessage && !message.startsWith("/me ")
				&& !message.startsWith("/envoyer "))
			return;

		final ProxiedPlayer player = (ProxiedPlayer) event.getSender();
		final String playerName = player.getName();

		// Si le joueur est mute
		if (MuteManager.isMuted(playerName))
		{
			event.setCancelled(true);
			return;
		}

		// Mots interdits
		for (final String word : removePunctuation(message).split(" "))
		{
			if (bannedWords.contains(word))
			{
				// Le cas du "merde"
				if (isPrivateMessage && word.equals("merde"))
					continue;

				MuteManager.mutePlayer(playerName, 3);
				Utils.sendMessage(player,
						"Vous avez été mute pour {3} minutes par {le Prof. Chen} pour avoir dit {%1$s}.", word);

				event.setCancelled(true);
				return;
			}
		}
	}

	private static String removePunctuation(String str)
	{
		return str.replaceAll("[^\\p{L}\\w\\s]", "");
	}
}