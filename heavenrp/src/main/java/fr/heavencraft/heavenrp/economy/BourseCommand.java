package fr.heavencraft.heavenrp.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.heavenrp.general.users.UserProvider;

public class BourseCommand extends HeavenCommand
{
	private final static String PURSE_MESSAGE = "Vous comptez le nombre de pièces d'or dans votre bourse...";
	private final static String PURSE_EMPTY = "Malheureusement, elle est vide... :-(";
	private final static String PURSE_SUCCESS = "Fantastique ! Vous avez {%1$s} pièces d'or !";
	private final static String PURSE_FAIL = "Vous avez perdu le compte... Faites /bourse pour recompter.";

	public BourseCommand()
	{
		super("bourse");
	}

	@Override
	protected void onPlayerCommand(final Player player, final String[] args) throws HeavenException
	{
		Utils.sendMessage(player, PURSE_MESSAGE);
		final int balance = UserProvider.getUserByName(player.getName()).getBalance();

		Bukkit.getScheduler().runTaskLaterAsynchronously(HeavenRP.getInstance(), new Runnable()
		{
			@Override
			public void run()
			{
				if (balance == 0)
					Utils.sendMessage(player, PURSE_EMPTY);
				else if (HeavenRP.Random.nextInt(5) == 0)
					Utils.sendMessage(player, PURSE_FAIL);
				else
					Utils.sendMessage(player, PURSE_SUCCESS, balance);
			}
		}, 40);
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