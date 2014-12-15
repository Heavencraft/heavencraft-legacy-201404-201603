package fr.lorgan17.maya.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.lorgan17.maya.MayaPlugin;

public class ButcherCommand extends MayaCommand
{
	public ButcherCommand()
	{
		super("butcher");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args)
	{
	}

	@Override
	public void onConsoleCommand(CommandSender sender, String[] args)
	{
		for (World world : Bukkit.getWorlds())
			for (Entity entity : world.getEntities())
				if (entity.getType() != EntityType.PLAYER)
					entity.remove();
		
		MayaPlugin.sendMessage(sender, "Removed all entities.");
	}

	@Override
	public void sendUsage(CommandSender sender)
	{
	}
}