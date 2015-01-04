package fr.heavencraft.heavenguard.bukkit.commands;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.command.CommandSender;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.api.Region;
import fr.heavencraft.heavenguard.bukkit.HeavenGuard;
import fr.heavencraft.utils.ChatUtil;

public class InfoSubCommand implements SubCommand
{

	@Override
	public boolean hasPermission(CommandSender sender)
	{
		return true; // Everybody can do /rg info
	}

	@Override
	public void execute(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length != 2)
		{
			sendUsage(sender);
			return;
		}

		Region region = HeavenGuard.getRegionProvider().getRegionByName(args[1]);

		ChatUtil.sendMessage(sender, "Protection : %1$s", region.getName());
		ChatUtil.sendMessage(sender, "Coordonnées : [{%1$s %2$s %3$s}] -> [{%4$s %5$s %6$s}] ({%7$s})", //
				region.getMinX(), region.getMinY(), region.getMinZ(), //
				region.getMaxX(), region.getMaxY(), region.getMaxZ(), //
				region.getWorld());

		Region parent = region.getParent();
		if (parent != null)
			ChatUtil.sendMessage(sender, "Parent : %1$s", parent.getName());

		Collection<UUID> owners = region.getMembers(true);
		if (!owners.isEmpty())
		{
			StringBuilder str = new StringBuilder();

			for (Iterator<UUID> it = owners.iterator(); it.hasNext();)
			{
				str.append(HeavenGuard.getInstance().getUniqueIdProvider().getNameFromUniqueId(it.next()));

				if (it.hasNext())
					str.append(", ");
			}

			ChatUtil.sendMessage(sender, "Propriétaires : %1$s", str.toString());
		}

		Collection<UUID> members = region.getMembers(false);
		if (!members.isEmpty())
		{
			StringBuilder str = new StringBuilder();

			for (Iterator<UUID> it = members.iterator(); it.hasNext();)
			{
				str.append(HeavenGuard.getInstance().getUniqueIdProvider().getNameFromUniqueId(it.next()));

				if (it.hasNext())
					str.append(", ");
			}

			ChatUtil.sendMessage(sender, "Membres : %1$s", str.toString());
		}
	}

	@Override
	public void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{region} info <protection>");
	}
}