package fr.lorgan17.lorganserver.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.PlayerUtil;
import fr.lorgan17.lorganserver.managers.WorldsManager;

public class SpawnCommand extends HeavenCommand
{
	public SpawnCommand()
	{
		super("spawn");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		PlayerUtil.teleportPlayer(player, WorldsManager.getSpawn());
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		ChatUtil.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}