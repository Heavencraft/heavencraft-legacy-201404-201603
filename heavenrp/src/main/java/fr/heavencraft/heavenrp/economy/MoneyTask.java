package fr.heavencraft.heavenrp.economy;

import static fr.heavencraft.utils.DevUtil.getPlugin;

import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.UserProvider;

public class MoneyTask extends BukkitRunnable
{
	private static final long PERIOD = 12000; // 10 minutes : 20 * 60 * 10 ticks

	public MoneyTask()
	{
		runTaskTimer(getPlugin(), PERIOD, PERIOD);
	}

	@Override
	public void run()
	{
		try
		{
			Player[] players = Bukkit.getOnlinePlayers();
			int amount = getAmount();

			for (Player player : players)
			{
				UserProvider.getUserByName(player.getName()).updateBalance(amount);
			}
		}
		catch (HeavenException ex)
		{
			ex.printStackTrace();
		}
	}

	private static int getAmount()
	{
		Calendar date = Calendar.getInstance();

		switch (date.get(Calendar.DAY_OF_WEEK))
		{
			case Calendar.SATURDAY:
			case Calendar.SUNDAY:
				return date.get(Calendar.HOUR_OF_DAY) < 18 ? 2 : 3;

			default:
				return date.get(Calendar.HOUR_OF_DAY) < 18 ? 1 : 2;
		}
	}
}