package fr.heavencraft.commands;

import static fr.heavencraft.utils.ChatUtil.sendMessage;
import static fr.heavencraft.utils.PlayerUtil.teleportPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.Permissions;
import fr.heavencraft.exceptions.HeavenException;

public class TpworldCommand extends HeavenCommand
{
	public TpworldCommand()
	{
		super("tpworld", Permissions.TPWORLD);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length != 1)
		{
			sendUsage(player);
			return;
		}

		World world = Bukkit.getWorld(args[0]);

		if (world == null)
			throw new HeavenException("Le monde {%1$s} n'existe pas.", args[0]);

		Location location = player.getLocation();
		location.setWorld(world);

		teleportPlayer(player, location);
		sendMessage(player, "Vous avez été téléporté dans le monde {%1$s}.", world.getName());
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		sendMessage(sender, "/{tpworld} <monde>");
	}
}