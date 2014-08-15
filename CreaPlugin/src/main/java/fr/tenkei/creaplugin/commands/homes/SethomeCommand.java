package fr.tenkei.creaplugin.commands.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;
import fr.tenkei.creaplugin.users.UserProvider;

public class SethomeCommand extends HeavenCommand
{
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
			nb = DevUtil.toUint(args[0]);

		else
		{
			sendUsage(player);
			return;
		}

		UserProvider.getUserByName(player.getName()).setHome(nb, player.getLocation());

		ChatUtil.sendMessage(player, "Votre {home %1$d} a bien été configuré.", nb);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		ChatUtil.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{home}");
		ChatUtil.sendMessage(sender, "/{home} <nombre>");
		ChatUtil.sendMessage(sender, "/{sethome}");
		ChatUtil.sendMessage(sender, "/{sethome} <nombre>");
	}
}