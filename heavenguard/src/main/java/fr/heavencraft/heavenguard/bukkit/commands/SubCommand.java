package fr.heavencraft.heavenguard.bukkit.commands;

import org.bukkit.command.CommandSender;

import fr.heavencraft.exceptions.HeavenException;

public interface SubCommand
{
	boolean hasPermission(CommandSender sender);

	void execute(CommandSender sender, String[] args) throws HeavenException;

	void sendUsage(CommandSender sender);
}