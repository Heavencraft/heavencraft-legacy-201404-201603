package fr.heavencraft.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.Permissions;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;

public class GcCommand extends HeavenCommand
{
	public GcCommand()
	{
		super("gc", Permissions.GC);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		onConsoleCommand(player, args);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		System.gc();

		ChatUtil.sendMessage(sender, "Le {Garbage Collector} a été appelé.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}