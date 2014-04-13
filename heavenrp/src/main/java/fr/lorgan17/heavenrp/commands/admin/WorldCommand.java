package fr.lorgan17.heavenrp.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;

public class WorldCommand extends HeavenCommand
{

	public WorldCommand()
	{
		super("world", "heavenrp.administrator.world");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length == 0)
		{
			sendUsage(player);
			return;
		}
		
		if (args[0].equalsIgnoreCase("tp") && args.length == 2)
		{
			World world = Bukkit.getWorld(args[1]);
			
			if (world == null)
				throw new HeavenException("Le monde {%1$s} n'existe pas.", args[1]);
			
			Location l = player.getLocation();
			l.setWorld(world);
			player.teleport(l);
		}
		else if (args[0].equalsIgnoreCase("copyentity") && args.length == 3)
		{
			World origin = Bukkit.getWorld(args[1]);
			
			if (origin == null)
				throw new HeavenException("Le monde {%1$s} n'existe pas.", args[1]);
			
			World destination = Bukkit.getWorld(args[2]);

			if (destination == null)
				throw new HeavenException("Le monde {%1$s} n'existe pas.", args[2]);
			
			for (Animals animal : origin.getEntitiesByClass(Animals.class))
			{
				Location l = animal.getLocation();
				l.setWorld(destination);
				animal.teleport(l);
			}
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{world} tp <nom>");
		//Utils.sendMessage(sender, "/{world} copyentity <origine> <destination>");
	}
}