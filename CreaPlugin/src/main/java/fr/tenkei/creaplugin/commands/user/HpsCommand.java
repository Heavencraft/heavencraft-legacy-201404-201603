package fr.tenkei.creaplugin.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.tenkei.creaplugin.managers.HpsManager;
import fr.tenkei.creaplugin.managers.UserManager;
import fr.tenkei.creaplugin.utils.Stuff;

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

		int hps = Stuff.toUint(args[0]);

		if (hps < 0)
			throw new HeavenException("Le nombre est incorrect.");

		HpsManager.removeBalance(player.getName(), hps);
		UserManager.getUser(player.getName()).updateBalance(hps * HpsManager.TAUX_JETON);

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