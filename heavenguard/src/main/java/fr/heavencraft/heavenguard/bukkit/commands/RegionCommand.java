package fr.heavencraft.heavenguard.bukkit.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.bukkit.commands.members.AddMemberSubCommand;
import fr.heavencraft.heavenguard.bukkit.commands.members.AddOwnerSubCommand;
import fr.heavencraft.heavenguard.bukkit.commands.members.RemoveMemberSubCommand;
import fr.heavencraft.heavenguard.bukkit.commands.members.RemoveOwnerSubCommand;

public class RegionCommand extends HeavenCommand
{
	private final Map<String, SubCommand> subCommands = new HashMap<String, SubCommand>();

	public RegionCommand()
	{
		super("region");

		subCommands.put("define", new DefineSubCommand());
		// subCommands.put("redefine", new RedefineSubCommand());
		subCommands.put("info", new InfoSubCommand());
		subCommands.put("setparent", new SetparentSubCommand());
		subCommands.put("remove", new RemoveSubCommand());

		subCommands.put("addmember", new AddMemberSubCommand());
		subCommands.put("removemember", new RemoveMemberSubCommand());
		subCommands.put("addowner", new AddOwnerSubCommand());
		subCommands.put("removeowner", new RemoveOwnerSubCommand());
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length == 0)
		{
			sendUsage(player);
			return;
		}

		SubCommand subCommand = subCommands.get(args[0].toLowerCase());

		if (subCommand == null)
		{
			sendUsage(player);
			return;
		}

		subCommand.execute(player, args);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		for (SubCommand subCommand : subCommands.values())
			subCommand.sendUsage(sender);
	}

	// private class RedefineSubCommand implements SubCommand
	// {
	// private static final String REDEFINE_HELP =
	// "/{region} redefine <protection>";
	//
	// @Override
	// public void execute(Player player, String[] args) throws HeavenException
	// {
	// if (args.length != 2)
	// {
	// sendUsage(player);
	// return;
	// }
	//
	// HeavenGuard.getRegionManager().redefineRegion(player, args[1]);
	// }
	//
	// @Override
	// public void sendUsage(CommandSender sender)
	// {
	// ChatUtil.sendMessage(sender, REDEFINE_HELP);
	// }
	// }
}