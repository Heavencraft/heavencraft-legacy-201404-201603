package fr.lorgan17.heavenrp.commands.admin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;

public class PoofCommand extends HeavenCommand
	implements Listener {

	private List<String> hiddenPlayers = new ArrayList<String>();
	
	public PoofCommand()
	{
		super("poof", "heavenrp.administrator.poof");
		Bukkit.getPluginManager().registerEvents(this, HeavenRP.getInstance());
	}


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) throws HeavenException
	{
		Player player = event.getPlayer();
		
		// Mise a jout de comment le joueur est perçu
		if (hiddenPlayers.contains(player.getName()))
		{
			Utils.sendMessage(player, "Vous êtes {invisible} !");
			
			for (Player p : Bukkit.getOnlinePlayers())
				p.hidePlayer(player);
		}
		else
		{
			for (Player p : Bukkit.getOnlinePlayers())
				p.showPlayer(player);
		}
		
		// Mise à jour de la vision du joueur
		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (hiddenPlayers.contains(p.getName()))
				player.hidePlayer(p);
			else
				player.showPlayer(p);
		}
	}
	
	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		String playerName = player.getName();
		
		if (hiddenPlayers.contains(player.getName()))
		{
			hiddenPlayers.remove(playerName);
			
			for (Player p : Bukkit.getOnlinePlayers())
				p.showPlayer(player);

			Utils.sendMessage(player, "Vous êtes maintenant {visible} !");
		}
		else
		{
			hiddenPlayers.add(playerName);
			
			for (Player p : Bukkit.getOnlinePlayers())
				p.hidePlayer(player);

			Utils.sendMessage(player, "Vous êtes maintenant {invisible} !");
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws HeavenException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void sendUsage(CommandSender sender) {
		// TODO Auto-generated method stub

	}

}
