package fr.heavencraft.heavenrp.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.UserProvider;

public class HomeCommand extends HeavenCommand
{
	public HomeCommand()
	{
		super("home");
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

		Utils.teleportPlayer(player, UserProvider.getUserByName(player.getName()).getHome(nb));
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "Commandes des points d'habitation :");
		Utils.sendMessage(sender, "/{home}");
		Utils.sendMessage(sender, "/{home} <numéro>");
		Utils.sendMessage(sender, "/{sethome}");
		Utils.sendMessage(sender, "/{sethome} <nombre>");
		Utils.sendMessage(sender, "/{buyhome} <nombre>");
	}
}