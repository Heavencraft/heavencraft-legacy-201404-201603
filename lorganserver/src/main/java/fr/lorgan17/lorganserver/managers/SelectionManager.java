package fr.lorgan17.lorganserver.managers;

import static fr.heavencraft.utils.DevUtil.registerListener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;

public class SelectionManager implements Listener
{
	private static final Map<String, Pair<Location, Location>> _selections = new HashMap<String, Pair<Location, Location>>();

	public SelectionManager()
	{
		registerListener(this);
	}

	public static void enable(String playerName)
	{
		if (!_selections.containsKey(playerName))
			_selections.put(playerName, new Pair<Location, Location>());
	}

	public static void disable(String playerName)
	{
		_selections.remove(playerName);
	}

	public static Pair<Location, Location> getSelection(String playerName) throws HeavenException
	{
		Pair<Location, Location> result = _selections.get(playerName);

		if (result == null || result.first == null || result.second == null)
			throw new HeavenException("Vous devez sélectionner votre zone avec un bâton.");

		return result;
	}

	public static int getPrice(String playerName)
	{
		if (!_selections.containsKey(playerName))
			return -1;

		Pair<Location, Location> selection = _selections.get(playerName);

		if (selection.first == null || selection.second == null)
			return -1;

		return (Math.abs(selection.first.getBlockX() - selection.second.getBlockX()) + 1)
				* (Math.abs(selection.first.getBlockZ() - selection.second.getBlockZ()) + 1);
	}

	private static void displayPrice(Player player)
	{
		int price = getPrice(player.getName());

		if (price != -1)
			ChatUtil.sendMessage(player, "La protection vous coûtera {" + price + "} pépites d'or.");
	}

	@EventHandler
	private void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		String playerName = player.getName();

		if (!_selections.containsKey(playerName))
			return;

		if (event.getItem() == null || event.getItem().getType() != Material.STICK)
			return;

		if (event.getClickedBlock() == null)
			return;

		Location location = event.getClickedBlock().getLocation();

		switch (event.getAction())
		{
			case LEFT_CLICK_BLOCK:
				_selections.get(playerName).first = location;
				event.setCancelled(true);
				ChatUtil.sendMessage(player, "Premier point placé en x = {" + location.getBlockX() + "}, z = {"
						+ location.getBlockZ() + "}.");

				displayPrice(player);
				break;
			case RIGHT_CLICK_BLOCK:
				_selections.get(playerName).second = location;
				event.setCancelled(true);
				ChatUtil.sendMessage(player, "Second point placé en x = {" + location.getBlockX() + "}, z = {"
						+ location.getBlockZ() + "}.");

				displayPrice(player);
				break;
			default:
				break;
		}
	}

	public static class Pair<A, B>
	{
		public A first;
		public B second;
	}
}