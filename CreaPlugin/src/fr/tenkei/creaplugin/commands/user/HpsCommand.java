package fr.tenkei.creaplugin.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.managers.HpsManager;
import fr.tenkei.creaplugin.utils.Message;
import fr.tenkei.creaplugin.utils.Stuff;

public class HpsCommand extends Command {

	public HpsCommand(MyPlugin plugin) {
		super("hps", plugin);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws MyException
	{
		if (args.length != 1){
			sendUsage(player);
			return;
		}

		int hps = Stuff.toUint(args[0]);
		
		if (hps < 0)
			throw new MyException("Le nombre est incorrect.");
		
		
		HpsManager.removeBalance(player.getName(), hps);
		getUser().updateBalance(hps * HpsManager.TAUX_JETON);

		Message.sendMessage(player, hps + " HPs ont été retirés de votre compte");
		Message.sendMessage(player, "Vous avez reçu {" + hps * HpsManager.TAUX_JETON + "} Jetons.");
	}


	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws MyException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Message.sendMessage(sender, "/{hps} <nombre de HPs a convertir>");
		Message.sendMessage(sender, "Le taux est de {" + HpsManager.TAUX_JETON + "} Jetons par HP.");
		try {
			Message.sendMessage(sender, "Vous avez {" + HpsManager.getBalance(sender.getName()) + "} HPs sur votre compte.");
		} catch (MyException e) { e.printStackTrace(); }
	}
}