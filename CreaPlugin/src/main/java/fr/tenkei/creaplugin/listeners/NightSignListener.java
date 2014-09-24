package fr.tenkei.creaplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.scheduler.BukkitTask;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.listeners.sign.SignListener;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;
import fr.tenkei.creaplugin.CreaPermissions;
import fr.tenkei.creaplugin.users.User;
import fr.tenkei.creaplugin.users.UserProvider;
import fr.tenkei.creaplugin.utils.Stuff;

public class NightSignListener extends SignListener
{
	private static final long NIGHT_TIME = 20 * 20 * 60;
	private BukkitTask dayTask;

	public NightSignListener()
	{
		super("Nuit", CreaPermissions.NIGHT_SIGN);
	}

	@Override
	protected boolean onSignPlace(Player player, SignChangeEvent event) throws HeavenException
	{
		return true;
	}

	@Override
	protected void onSignClick(Player player, Sign sign) throws HeavenException
	{
		User user = UserProvider.getUserByName(player.getName());

		if ((user.getInteractBlock() == null) || !Stuff.blocksEquals(user.getInteractBlock(), sign.getBlock()))
		{
			user.setInteractBlock(sign.getBlock());
			ChatUtil.sendMessage(player, "Cliquez une seconde fois pour confirmer l'achat de la nuit pour 200 Jetons.");
			return;
		}

		user.setInteractBlock(null);

		user.updateBalance(-200);

		ChatUtil.broadcastMessage(player.getName() + "} vous souhaite une bonne nuit !");
		user.stateBalance();

		final World world = player.getWorld();
		world.setTime(18000L);

		if (dayTask != null)
		{
			dayTask.cancel();
		}

		dayTask = Bukkit.getScheduler().runTaskLater(DevUtil.getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				world.setTime(6000L);
			}
		}, NIGHT_TIME);
	}
}