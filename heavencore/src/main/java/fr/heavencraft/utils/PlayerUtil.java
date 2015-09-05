package fr.heavencraft.utils;

import org.bukkit.Bukkit;
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
		final Player player = Bukkit.getPlayer(playerName);

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
		catch (final PlayerNotConnectedException ex)
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
		catch (final PlayerNotConnectedException ex)
		{
			final ProfileRepository repository = new HttpProfileRepository("minecraft");
			final Profile[] profiles = repository.findProfilesByNames(playerName);

			if (profiles.length == 1)
				return profiles[0].getId();
			else
				throw new UUIDNotFoundException(playerName);
		}
	}
}