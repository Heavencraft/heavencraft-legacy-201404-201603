package fr.heavencraft.heavenmuseum.commands;

import static fr.heavencraft.utils.ChatUtil.sendMessage;
import static fr.heavencraft.utils.PlayerUtil.teleportPlayer;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenmuseum.managers.WorldsManager;

public class SpawnCommand extends HeavenCommand
{
	public SpawnCommand()
	{
		super("spawn");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		teleportPlayer(player, WorldsManager.getSpawn());
		sendMessage(player, "Vous avez été téléporté au spawn du musée.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}