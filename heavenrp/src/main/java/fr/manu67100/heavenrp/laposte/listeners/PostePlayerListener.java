package fr.manu67100.heavenrp.laposte.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.manu67100.heavenrp.laposte.handlers.PosteUtils;

public class PostePlayerListener implements Listener{
	public PostePlayerListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenRP.getInstance());
	}
	private final static String FORMAT_POSTE = "§4[§6La Poste§4] §6%1$s";
	
	@EventHandler(priority = EventPriority.LOWEST)
	private void onPlayerJoin(PlayerJoinEvent event) throws HeavenException
	{
		if(PosteUtils.getColisRecus(event.getPlayer().getUniqueId().toString()).size() != 0)
		{
			event.getPlayer().sendMessage(String.format(FORMAT_POSTE, "Vous avez un colis en attente, vous pouvez le récuperer à la poste!"));
		}
	}
}
