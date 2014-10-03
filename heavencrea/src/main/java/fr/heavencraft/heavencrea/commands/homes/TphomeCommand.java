package fr.heavencraft.heavencrea.commands.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavencrea.CreaPermissions;
import fr.heavencraft.heavencrea.users.User;
import fr.heavencraft.heavencrea.users.UserProvider;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;
import fr.heavencraft.utils.PlayerUtil;

public class TphomeCommand extends HeavenCommand
{
	public TphomeCommand()
	{
		super("tphome", CreaPermissions.TPHOME);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		User user;
		int nb = 1;

		switch (args.length)
		{
			case 2:
				nb = DevUtil.toUint(args[1]);
			case 1:
				user = UserProvider.getUserByName(args[0]);
				break;
			default:
				sendUsage(player);
				return;
		}

		PlayerUtil.teleportPlayer(player, user.getHome(nb));
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		ChatUtil.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{tphome} <joueur>");
		ChatUtil.sendMessage(sender, "/{tphome} <joueur> <numéro>");
	}
}