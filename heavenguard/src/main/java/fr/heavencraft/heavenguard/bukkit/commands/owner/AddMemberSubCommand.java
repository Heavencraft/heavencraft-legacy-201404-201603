package fr.heavencraft.heavenguard.bukkit.commands.owner;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.api.HeavenGuardPermissions;
import fr.heavencraft.heavenguard.api.Region;
import fr.heavencraft.heavenguard.api.RegionProvider;
import fr.heavencraft.utils.ChatUtil;

public class AddMemberSubCommand extends AbstractOwnerSubCommand
{
	public AddMemberSubCommand(RegionProvider regionProvider)
	{
		super(regionProvider, HeavenGuardPermissions.ADDMEMBER_COMMAND);
	}

	@Override
	public void execute(CommandSender sender, String regionName, String[] args) throws HeavenException
	{
		final Region region = regionProvider.getRegionByName(regionName);

		for (final String arg : args)
		{
			@SuppressWarnings("deprecation")
			final OfflinePlayer player = Bukkit.getOfflinePlayer(arg);

			region.addMember(player.getUniqueId(), false);
			ChatUtil.sendMessage(sender, "{%1$s} est maintenant membre de la protection {%2$s}.", player.getName(), regionName);
		}
	}

	@Override
	public void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/rg {addmember} <protection> <membre(s)>");
	}
}