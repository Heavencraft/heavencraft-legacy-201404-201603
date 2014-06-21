package fr.heavencraft.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.Permissions;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;

public class TpposCommand extends HeavenCommand
{
	public TpposCommand()
	{
		super("tppos", Permissions.TPPOS);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length != 3)
		{
			sendUsage(player);
			return;
		}

		int x = DevUtil.toInt(args[0]);
		int y = DevUtil.toInt(args[1]);
		int z = DevUtil.toInt(args[2]);

		player.teleport(new Location(player.getWorld(), x, y, z));
		ChatUtil.sendMessage(player, "Vous avez été téléporté en x = {%1$d}, y = {%2$d}, z = {%3$d}.", x, y, z);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		ChatUtil.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{tppos} <x> <y> <z>");
	}
}
