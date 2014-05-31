package fr.heavencraft.heavenrp.web;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Permissions;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.User;
import fr.heavencraft.heavenrp.general.users.UserProvider;

public class WebCommand extends HeavenCommand
{
	public WebCommand(String name, String permission)
	{
		super("web", Permissions.WEB_COMMAND);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		Utils.sendMessage(player, "Cette commande n'est utilisable que depuis la {console}.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length == 0)
			return;

		switch (args[0])
		{
		// /web po <joueur> <nombre de po>
			case "po": {
				if (args.length != 3)
					return;

				User user = UserProvider.getUserByName(args[1]);
				int delta = Utils.toUint(args[2]);

				user.updateBalance(delta);
				Utils.sendMessage(user.getName(), "Vous venez d'acheter {%1$s} pièces d'or sur la boutique.");
			}
		}
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}