package fr.lorgan17.maya.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.lorgan17.maya.MayaPlugin;

public class SpawnmobCommand extends MayaCommand
{
	public SpawnmobCommand()
	{
		super("spawnmob");
	}

	@Override
	public void onPlayerCommand(Player player, String[] args)
	{
		
		int number;
		
		switch (args.length)
		{
			case 1:
				number = 1;
				break;
			case 2:
				number = Integer.parseInt(args[1]);
				break;
			default:
				sendUsage(player);
				return;
		}
		
		if (number > 5)
			number = 5;
		
		Location location = player.getLocation();
		EntityType entityType = EntityType.fromName(args[0]);
		
		if (entityType == null)
			MayaPlugin.sendMessage(player, "Le mob {" + args[0] + "} n'existe pas.");
		else
		{
			for (int i = 0; i != number; i++)
				location.getWorld().spawnEntity(location, entityType);
			
			MayaPlugin.sendMessage(player, "Vous avez fait spawner {" + number + " " + entityType.name().toLowerCase() + "(s)}.");
		}
	}

	@Override
	public void onConsoleCommand(CommandSender sender, String[] args)
	{
		MayaPlugin.sendMessage(sender, "Cette commande n'est pas disponible depuis la console.");
	}

	@Override
	public void sendUsage(CommandSender sender)
	{
		MayaPlugin.sendMessage(sender, "{/spawnmob} <mob> <nombre> : faire spawner des mobs.");
	}
}