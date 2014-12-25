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
	 * Teleport
	 */

	public static void teleportPlayer(final Player player, final Entity entity)
	{
		teleportPlayer(player, entity.getLocation());
	}

	public static void teleportPlayer(final Player player, final Location location)
	{
		// Bugfix for foodlevel changing after teleport on a different world
		if (!player.getWorld().equals(location.getWorld()))
		{
			final int foodLevel = player.getFoodLevel();
			final float saturation = player.getSaturation();

			Bukkit.getScheduler().runTaskLater(DevUtil.getPlugin(), new Runnable()
			{
				@Override
				public void run()
				{
					player.setFoodLevel(foodLevel);
					player.setSaturation(saturation);
				}
			}, 20);
		}

		if (player.isInsideVehicle() && player.getVehicle() instanceof Horse)
		{
			final Horse horse = (Horse) player.getVehicle();

			player.teleport(location);
			horse.teleport(player);

			ChatUtil.sendMessage(player, "Ton cheval a été téléporté avec toi. S'il n'est pas là, {déco reco}.");

			Bukkit.getScheduler().runTaskLater(DevUtil.getPlugin(), new Runnable()
			{
				@Override
				public void run()
				{
					horse.setHealth(horse.getMaxHealth());
					horse.setPassenger(player);
				}
			}, 20);
		}

		else
			player.teleport(location);
	}
}