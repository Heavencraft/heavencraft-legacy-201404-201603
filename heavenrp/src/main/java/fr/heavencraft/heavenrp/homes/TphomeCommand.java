package fr.heavencraft.heavenrp.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Permissions;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.UsersManager;
import fr.heavencraft.heavenrp.general.users.UsersManager.User;

public class TphomeCommand extends HeavenCommand
{
	public TphomeCommand()
	{
		super("tphome", Permissions.TPHOME_COMMAND);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		User user;
		int nb = 1;

		switch (args.length)
		{
			case 2:
				nb = Utils.toUint(args[1]);
			case 1:
				user = UsersManager.getByName(args[0]);
				break;
			default:
				sendUsage(player);
				return;
		}

		Utils.teleportPlayer(player, user.getHome(nb));
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{tphome} <joueur>");
		Utils.sendMessage(sender, "/{tphome} <joueur> <numéro>");
	}
}