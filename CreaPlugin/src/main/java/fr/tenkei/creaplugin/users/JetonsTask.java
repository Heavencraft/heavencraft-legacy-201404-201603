package fr.tenkei.creaplugin.users;

import static fr.heavencraft.utils.DevUtil.getPlugin;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;

public class JetonsTask extends BukkitRunnable
{
	private static final long PERIOD = 18000; // 15 minutes : 20 * 60 * 15 ticks

	public JetonsTask()
	{
		runTaskTimer(getPlugin(), PERIOD, PERIOD);
	}

	@Override
	public void run()
	{
		try
		{
			Collection<? extends Player> players = Bukkit.getOnlinePlayers();
			int nbJetons = players.size() > 5 ? players.size() : 5;

			for (Player player : players)
			{
				UserProvider.getUserByName(player.getName()).updateBalance(nbJetons);
				ChatUtil.sendMessage(player, "Vous venez de recevoir {%1$s} jetons.", nbJetons);
			}
		}
		catch (HeavenException ex)
		{
			ex.printStackTrace();
		}
	}
}