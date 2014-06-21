package fr.heavencraft.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.Permissions;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.PlayerUtil;

public class TphereCommand extends HeavenCommand
{
	public TphereCommand()
	{
		super("tphere", Permissions.TPHERE);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length != 1)
		{
			sendUsage(player);
			return;
		}

		Player toTeleport = PlayerUtil.getPlayer(args[0]);

		PlayerUtil.teleportPlayer(toTeleport, player);
		ChatUtil.sendMessage(toTeleport, "Vous avez été téléporté par {%1$s}.", player.getName());
		ChatUtil.sendMessage(player, "Téléportation de {%1$s}.", toTeleport.getName());
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{

	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{tphere} <joueur>");
	}
}