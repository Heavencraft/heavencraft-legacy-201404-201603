package fr.heavencraft.commands;

import static fr.heavencraft.utils.ChatUtil.sendMessage;
import static fr.heavencraft.utils.DevUtil.registerListener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.heavencraft.Permissions;
import fr.heavencraft.exceptions.HeavenException;

public class PoofCommand extends HeavenCommand implements Listener
{
	private final List<String> hiddenPlayers = new ArrayList<String>();

	public PoofCommand()
	{
		super("poof", Permissions.POOF);
		registerListener(this);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) throws HeavenException
	{
		Player player = event.getPlayer();

		// Update how the player is seen
		if (hiddenPlayers.contains(player.getName()))
		{
			sendMessage(player, "Vous êtes {invisible} !");

			for (Player p : Bukkit.getOnlinePlayers())
				p.hidePlayer(player);
		}
		else
		{
			for (Player p : Bukkit.getOnlinePlayers())
				p.showPlayer(player);
		}

		// Update who the player can see
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

			sendMessage(player, "Vous êtes maintenant {visible} !");
		}
		else
		{
			hiddenPlayers.add(playerName);

			for (Player p : Bukkit.getOnlinePlayers())
				p.hidePlayer(player);

			sendMessage(player, "Vous êtes maintenant {invisible} !");
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}