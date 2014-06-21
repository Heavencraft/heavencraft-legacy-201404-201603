package fr.heavencraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.Profile;
import com.mojang.api.profiles.ProfileRepository;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.exceptions.PlayerNotConnectedException;
import fr.heavencraft.exceptions.UUIDNotFoundException;

public class PlayerUtil
{
	public static Player getPlayer(String playerName) throws PlayerNotConnectedException
	{
		@SuppressWarnings("deprecation")
		Player player = Bukkit.getPlayer(playerName);

		if (player == null)
			throw new PlayerNotConnectedException(playerName);

		return player;
	}

	public static String getExactName(String playerName)
	{
		try
		{
			return getPlayer(playerName).getName();
		}
		catch (PlayerNotConnectedException ex)
		{
			return playerName;
		}
	}

	/*
	 * UUID
	 */

	public static String getUUID(Player player)
	{
		return player.getUniqueId().toString().replaceAll("-", "");
	}

	public static String getUUID(String playerName) throws HeavenException
	{
		try
		{
			return getUUID(getPlayer(playerName));
		}
		catch (PlayerNotConnectedException ex)
		{
			ProfileRepository repository = new HttpProfileRepository("minecraft");
			Profile[] profiles = repository.findProfilesByNames(playerName);

			if (profiles.length == 1)
				return profiles[0].getId();
			else
				throw new UUIDNotFoundException(playerName);
		}
	}

	/*
	 * Teleport with horse
	 */

	public static void teleportPlayer(Player player, Entity entity)
	{
		teleportPlayer(player, entity.getLocation());
	}

	public static void teleportPlayer(final Player player, Location location)
	{
		if (player.isInsideVehicle() && player.getVehicle() instanceof Horse)
		{
			final Horse horse = (Horse) player.getVehicle();

			horse.eject();

			horse.teleport(location);
			horse.setHealth(horse.getMaxHealth());
			player.teleport(location);

			ChatUtil.sendMessage(player, "Ton cheval a été téléporté avec toi. S'il n'est pas là, {déco reco}.");

			horse.setPassenger(player);
		}

		else
			player.teleport(location);
	}
}