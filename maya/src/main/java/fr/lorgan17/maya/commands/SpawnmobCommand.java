package fr.lorgan17.maya.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.lorgan17.maya.MayaCommand;
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
		if (args.length != 1)
		{
			sendUsage(player);
			return;
		}

		Location location = player.getLocation();
		EntityType entityType = EntityType.fromName(args[0]);

		if (entityType == null)
		{
			MayaPlugin.sendMessage(player, "Le mob {" + args[0] + "} n'existe pas.");
		}
		else if (!entityType.isSpawnable())
		{
			MayaPlugin.sendMessage(player, "Vous ne pouvez pas faire spawner un {" + entityType.name().toLowerCase() + "}.");
		}
		else
		{
			location.getWorld().spawnEntity(location, entityType);
			MayaPlugin.sendMessage(player, "Vous avez fait spawner un {" + entityType.name().toLowerCase() + "}.");
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
		MayaPlugin.sendMessage(sender, "{/spawnmob} <mob> : faire spawner un mob.");
	}
}