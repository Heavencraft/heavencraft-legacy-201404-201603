package fr.lorgan17.maya.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Skeleton;

import fr.lorgan17.maya.MayaCommand;
import fr.lorgan17.maya.MayaPlugin;
import fr.lorgan17.maya.managers.ProtectionManager;

public class DevenirCommand extends MayaCommand
{
	public DevenirCommand()
	{
		super("devenir");
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

		if (ProtectionManager.isProtected(location.getBlock()))
			return;

		switch (args[0].toLowerCase())
		{
			case "petit":
			case "minuscule":
				location.getWorld().spawnEntity(location, EntityType.GIANT);
				break;

			case "immortel":
			case "invincible":
				player.damage(Double.MAX_VALUE);
				break;

			case "con":
			case "stupide":
				MayaPlugin.sendMessage(player, "Rien besoin de changer, tu es parfait comme tu es !");
				break;

			case "lapin":
			case "lapinou":
			case "lapinou13":
			case "minilapinou":
				for (int i = 0; i != 10; i++)
				{
					Rabbit rabbit = (Rabbit) location.getWorld().spawnEntity(location, EntityType.RABBIT);
					rabbit.setCustomName("lapinou13");
				}
				break;

			case "suicidaire":
			case "terroriste":
			case "kamikaze":
			case "mort":
			case "creeper":
			case "zombie":
				location.getWorld().createExplosion(location, 12);
				break;

			case "modo":
			case "moderateur":
			case "modérateur":
				player.setHealth(1);
				player.getInventory().clear();
				location.getBlock().setType(Material.LAVA);
				break;

			case "op":
			case "admin":
			case "adminstrateur":
				for (int i = 0; i != 4; i++)
				{
					Skeleton skeleton = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);
					skeleton.setCustomName("Waiting for OP");
					skeleton.setTarget(player);
				}
				player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "[Server: Opped " + player.getName() + "]");
				break;

			default:
				MayaPlugin.sendMessage(player, "Vous êtes désormais {" + args[0] + "} !");
				break;
		}
	}

	@Override
	public void onConsoleCommand(CommandSender sender, String[] args)
	{
	}

	@Override
	public void sendUsage(CommandSender sender)
	{
		MayaPlugin.sendMessage(sender, "{/devenir} <???> : devenir un ???.");
	}
}