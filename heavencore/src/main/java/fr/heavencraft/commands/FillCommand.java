package fr.heavencraft.commands;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.Permissions;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;

public class FillCommand extends HeavenCommand
{
	public FillCommand()
	{
		super("fill", Permissions.FILL);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		onConsoleCommand(player, args);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length != 8)
		{
			sendUsage(sender);
			return;
		}

		World world;

		if (sender instanceof Player)
			world = ((Player) sender).getWorld();
		else if (sender instanceof BlockCommandSender)
			world = ((BlockCommandSender) sender).getBlock().getWorld();
		else
			return;

		int x1 = DevUtil.toInt(args[0]);
		int y1 = DevUtil.toInt(args[1]);
		int z1 = DevUtil.toInt(args[2]);
		int x2 = DevUtil.toInt(args[3]);
		int y2 = DevUtil.toInt(args[4]);
		int z2 = DevUtil.toInt(args[5]);
		Material tile = Material.matchMaterial(args[6]);

		int tmp;

		if (x2 < x1)
		{
			tmp = x1;
			x1 = x2;
			x2 = tmp;
		}

		if (y2 < y1)
		{
			tmp = y1;
			y1 = y2;
			y2 = tmp;
		}

		if (z2 < z1)
		{
			tmp = z1;
			z1 = z2;
			z2 = tmp;
		}

		for (int x = x1; x <= x2; x++)
			for (int y = y1; y <= y2; y++)
				for (int z = z1; z <= z2; z++)
					world.getBlockAt(x, y, z).setType(tile);
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{fill} <x1> <y1> <z1> <x2> <y2> <z2> <TileName> replace");
	}
}