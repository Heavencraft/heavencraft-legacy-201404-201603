package fr.heavencraft.heavencrea.commands.money;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavencrea.hps.HpsManager;
import fr.heavencraft.heavencrea.users.UserProvider;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;

public class HpsCommand extends HeavenCommand
{

	public HpsCommand()
	{
		super("hps");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length != 1)
		{
			sendUsage(player);
			return;
		}

		int hps = DevUtil.toUint(args[0]);

		if (hps < 0)
			throw new HeavenException("Le nombre est incorrect.");

		HpsManager.removeBalance(player.getName(), hps);
		UserProvider.getUserByName(player.getName()).updateBalance(hps * HpsManager.TAUX_JETON);

		ChatUtil.sendMessage(player, "%1$s HPs ont été retirés de votre compte", hps);
		ChatUtil.sendMessage(player, "Vous avez reçu {%1$s} Jetons.", hps * HpsManager.TAUX_JETON);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		// TODO Auto-generated method stub
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{hps} <nombre de HPs a convertir>");
		ChatUtil.sendMessage(sender, "Le taux est de {" + HpsManager.TAUX_JETON + "} Jetons par HP.");
		try
		{
			ChatUtil.sendMessage(sender, "Vous avez {" + HpsManager.getBalance(sender.getName())
					+ "} HPs sur votre compte.");
		}
		catch (HeavenException e)
		{
			e.printStackTrace();
		}
	}
}