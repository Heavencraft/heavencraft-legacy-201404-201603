package fr.heavencraft.heavenguard.bukkit.commands.members;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.bukkit.HeavenGuard;
import fr.heavencraft.heavenguard.bukkit.commands.SubCommand;
import fr.heavencraft.utils.ChatUtil;

public class RemoveOwnerSubCommand implements SubCommand
{
	@Override
	public boolean hasPermission(CommandSender sender)
	{
		return true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length != 3)
		{
			sendUsage(sender);
			return;
		}

		String name = args[1];

		@SuppressWarnings("deprecation")
		OfflinePlayer owner = Bukkit.getOfflinePlayer(args[2]);

		HeavenGuard.getRegionProvider().getRegionByName(name).removeMember(owner.getUniqueId(), true);
		ChatUtil.sendMessage(sender, "{%1$s} n'est plus propriétaire de la protection {%2$s}.", owner.getName(), name);
	}

	@Override
	public void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{region} removeowner <protection> <propriétaire>");
	}
}