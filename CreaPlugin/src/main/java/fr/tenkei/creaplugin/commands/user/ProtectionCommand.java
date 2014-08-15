package fr.tenkei.creaplugin.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.tenkei.creaplugin.exceptions.NotOwnerException;
import fr.tenkei.creaplugin.managers.entities.Region;
import fr.tenkei.creaplugin.users.UserProvider;

public class ProtectionCommand extends HeavenCommand
{
	public ProtectionCommand()
	{
		super("protection");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length == 0)
		{
			sendUsage(player);
			return;
		}

		String playerName = player.getName();

		if (args[0].equalsIgnoreCase("supprimer") && args.length == 2)
		{
			int regionId = Integer.parseInt(args[1]);

			Region region = Region.getRegionById(regionId);

			if (!region.isMember(playerName, true))
				throw new NotOwnerException();

			region.delete();

			ChatUtil.sendMessage(player, "La protection {" + regionId + "} a été supprimée.");
		}
		else if (args[0].equalsIgnoreCase("ajouter") && args.length == 3)
		{
			int regionId = Integer.parseInt(args[1]);
			String user = args[2];

			Region region = Region.getRegionById(regionId);

			if (!region.isMember(playerName, true))
				throw new NotOwnerException();

			region.addMember(UserProvider.getUserByName(user), false);

			ChatUtil.sendMessage(player, "Le joueur {" + user + "} est désormais membre de la protection {" + regionId
					+ "}.");
		}
		else if (args[0].equalsIgnoreCase("enlever") && args.length == 3)
		{
			int regionId = Integer.parseInt(args[1]);
			String user = args[2];

			Region region = Region.getRegionById(regionId);

			if (!region.isMember(playerName, true))
				throw new NotOwnerException();

			region.removeMember(user);

			ChatUtil.sendMessage(player, "Le joueur {" + user + "} n'est plus membre de la protection {" + regionId
					+ "}.");
		}
		else
			sendUsage(player);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		ChatUtil.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{protection} ajouter <protection> <joueur> : ajoute un joueur à la protection.");
		ChatUtil.sendMessage(sender, "/{protection} enlever <protection> <joueur> : enlève un joueur de la protection.");
	}
}
