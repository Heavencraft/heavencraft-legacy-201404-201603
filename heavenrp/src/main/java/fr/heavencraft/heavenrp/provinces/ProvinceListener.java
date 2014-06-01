package fr.heavencraft.heavenrp.provinces;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.UserProvider;
import fr.heavencraft.heavenrp.provinces.ProvincesManager.Province;

public class ProvinceListener implements Listener
{
	public ProvinceListener()
	{
		Utils.registerListener(this);
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlayerJoin(PlayerJoinEvent event) throws HeavenException
	{
		Player player = event.getPlayer();

		Province province = UserProvider.getUserByName(player.getName()).getProvince();

		if (province == null)
			Utils.sendMessage(player, "Vous n'avez pas encore choisi de province.");
	}

	// @EventHandler
	// private void onNameTag(AsyncPlayerReceiveNameTagEvent event) throws HeavenException
	// {
	// String playerName = event.getNamedPlayer().getName();
	//
	// if (playerName.length() > 14)
	// {
	// return;
	// }
	//
	// Province province = UserProvider.getUserByName(playerName).getProvince();
	//
	// if (province == null)
	// {
	// return;
	// }
	//
	// event.setTag(province.getColor() + playerName);
	// }
}