package fr.lorgan17.maya.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lorgan17.maya.MayaCommand;
import fr.lorgan17.maya.MayaPlugin;

public class TpposCommnand extends MayaCommand
{
	public TpposCommnand()
	{
		super("tppos");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args)
	{
		if (args.length != 3)
		{
			sendUsage(player);
			return;
		}

		int x = Integer.parseInt(args[0]);
		int y = Integer.parseInt(args[1]);
		int z = Integer.parseInt(args[2]);
		
		Location l = new Location(player.getWorld(), x, y, z);
		player.teleport(l);
	}

	@Override
	public void onConsoleCommand(CommandSender sender, String[] args)
	{
	}

	@Override
	public void sendUsage(CommandSender sender)
	{
		MayaPlugin.sendMessage(sender, "/{tppos} <x> <y> <z>");
	}
}