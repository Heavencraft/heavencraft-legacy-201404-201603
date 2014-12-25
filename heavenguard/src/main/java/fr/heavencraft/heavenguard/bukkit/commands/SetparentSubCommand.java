package fr.heavencraft.heavenguard.bukkit.commands;

import org.bukkit.command.CommandSender;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.api.HeavenGuardPermissions;
import fr.heavencraft.heavenguard.bukkit.HeavenGuard;
import fr.heavencraft.utils.ChatUtil;

public class SetparentSubCommand implements SubCommand
{
	@Override
	public boolean hasPermission(CommandSender sender)
	{
		return sender.hasPermission(HeavenGuardPermissions.REGION_SETPARENT);
	}

	@Override
	public void execute(CommandSender sender, String[] args) throws HeavenException
	{
		String child, parent = null;

		switch (args.length)
		{
			case 3:
				parent = args[2];

			case 2:
				child = args[1];
				break;

			default:
				sendUsage(sender);
				return;
		}

		HeavenGuard.getRegionProvider().getRegionByName(child).setParent(parent);
	}

	@Override
	public void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{region} setparent <protection> <protection parente>");
	}
}