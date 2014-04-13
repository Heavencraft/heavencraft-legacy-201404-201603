package fr.heavencraft.heavenrp.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.UsersManager;

public class SethomeCommand extends HeavenCommand {

	public SethomeCommand()
	{
		super("sethome");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		int nb;
		
		if (args.length == 0)
			nb = 1;
		
		else if (args.length == 1)
			nb = Utils.toUint(args[0]);
		
		else
		{
			sendUsage(player);
			return;
		}
		
		UsersManager.getByName(player.getName()).setHome(nb, player.getLocation());

        Utils.sendMessage(player, "Votre {home %1$d} a bien été configuré.", nb);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{home}");
		Utils.sendMessage(sender, "/{home} <nombre>");
		Utils.sendMessage(sender, "/{sethome}");
		Utils.sendMessage(sender, "/{sethome} <nombre>");
	}
}
