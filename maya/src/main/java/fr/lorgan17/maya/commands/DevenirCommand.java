package fr.lorgan17.maya.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;

import fr.lorgan17.maya.MayaCommand;
import fr.lorgan17.maya.MayaPlugin;

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

		switch (args[0].toLowerCase())
		{

		case "con":
		case "stupide":
			MayaPlugin.sendMessage(player, "Rien besoin de changer, tu es parfait comme tu es !");
			break;

		case "suicidaire":
		case "terroriste":
		case "kamikaze":
		case "mort":
		case "zombie":
			location.getWorld().createExplosion(location, 12);
			player.damage(100);
			break;

		case "modo":
			location.add(0, 2, 0).getBlock().setType(Material.LAVA);
			player.playSound(location, Sound.VILLAGER_YES, 0, 0);
			MayaPlugin.sendMessage(player, "Veuillez patienter..");
			break;

		case "admin":
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
		// TODO Auto-generated method stub

	}

	@Override
	public void sendUsage(CommandSender sender)
	{
		// TODO Auto-generated method stub

	}
}