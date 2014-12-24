package fr.heavencraft.heavenguard.bukkit.commands;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.api.HeavenGuardPermissions;
import fr.heavencraft.heavenguard.api.Region;
import fr.heavencraft.heavenguard.bukkit.HeavenGuard;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;

class DefineSubCommand implements SubCommand
{
	@Override
	public boolean hasPermission(CommandSender sender)
	{
		return sender.hasPermission(HeavenGuardPermissions.REGION_DEFINE);
	}

	@Override
	public void execute(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length < 2)
		{
			sendUsage(sender);
			return;
		}

		String name = args[1];
		Selection selection = DevUtil.getWESelection((Player) sender);
		Collection<OfflinePlayer> owners = new ArrayList<OfflinePlayer>();

		if (args.length != 2)
		{
			for (int i = 2; i != args.length; i++)
				owners.add(Bukkit.getOfflinePlayer(args[i]));
		}

		define(name, selection, owners);
		ChatUtil.sendMessage(sender, "La région {%1$s} a bien été créée.", name);
	}

	@Override
	public void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{region} define <protection>");
	}

	private static void define(String name, Selection selection, Collection<OfflinePlayer> owners) throws HeavenException
	{
		Location min = selection.getMinimumPoint();
		Location max = selection.getMaximumPoint();

		// Create the region
		HeavenGuard.getRegionProvider().createRegion(name, selection.getWorld().getName(), min.getBlockX(), min.getBlockY(),
				min.getBlockZ(), max.getBlockX(), max.getBlockY(), max.getBlockZ());

		// Add the initial owners
		if (!owners.isEmpty())
		{
			Region region = HeavenGuard.getRegionProvider().getRegionByName(name);

			for (OfflinePlayer owner : owners)
				region.addMember(owner.getUniqueId(), true);
		}
	}
}