package fr.heavencraft.heavenguard.bukkit.commands;

import org.bukkit.command.CommandSender;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.api.HeavenGuardPermissions;
import fr.heavencraft.heavenguard.bukkit.HeavenGuard;
import fr.heavencraft.utils.ChatUtil;

public class RemoveSubCommand implements SubCommand
{
	@Override
	public boolean hasPermission(CommandSender sender)
	{
		return sender.hasPermission(HeavenGuardPermissions.REGION_REMOVE);
	}

	@Override
	public void execute(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length != 2)
		{
			sendUsage(sender);
			return;
		}

		remove(sender, args[1].toLowerCase());
	}

	@Override
	public void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{region} remove <protection>");
	}

	private static void remove(CommandSender sender, String name) throws HeavenException
	{
		HeavenGuard.getRegionProvider().deleteRegion(name);
		ChatUtil.sendMessage(sender, "La protection {%1$s} a bien été supprimée.", name);
	}
}