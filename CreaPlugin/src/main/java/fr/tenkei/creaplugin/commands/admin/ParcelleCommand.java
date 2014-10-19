package fr.tenkei.creaplugin.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;
import fr.tenkei.creaplugin.CreaPermissions;
import fr.tenkei.creaplugin.managers.entities.Region;

public class ParcelleCommand extends HeavenCommand
{
	public ParcelleCommand()
	{
		super("parcelle", CreaPermissions.PARCELLE);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length == 0)
		{
			sendUsage(player);
			return;
		}

		switch (args[0])
		{
			case "clear":
				if (args.length == 2)
				{
					Region.getRegionById(DevUtil.toInt(args[1])).removeAllMembersAndOwners();
				}
				break;

			default:
				sendUsage(player);
				break;
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "Commande d'administration des parcelles");
		ChatUtil.sendMessage(sender, "/{parcelle} clear <protection> : retire les membres d'une parcelle.");
	}
}