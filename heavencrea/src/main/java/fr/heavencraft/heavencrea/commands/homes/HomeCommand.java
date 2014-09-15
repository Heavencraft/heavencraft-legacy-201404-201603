package fr.heavencraft.heavencrea.commands.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavencrea.users.UserProvider;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;
import fr.heavencraft.utils.PlayerUtil;

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
			nb = DevUtil.toUint(args[0]);
		else
		{
			sendUsage(player);
			return;
		}

		PlayerUtil.teleportPlayer(player, UserProvider.getUserByName(player.getName()).getHome(nb));
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		ChatUtil.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "Commandes des points d'habitation :");
		ChatUtil.sendMessage(sender, "/{home}");
		ChatUtil.sendMessage(sender, "/{home} <numéro>");
		ChatUtil.sendMessage(sender, "/{sethome}");
		ChatUtil.sendMessage(sender, "/{sethome} <nombre>");
		ChatUtil.sendMessage(sender, "/{buyhome} <nombre>");
	}
}