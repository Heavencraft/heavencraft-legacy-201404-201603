package fr.heavencraft.heavencrea.commands.teleport;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavencrea.worlds.WorldsManager;
import fr.heavencraft.utils.ChatUtil;

public class SpawnCommand extends HeavenCommand
{
	public SpawnCommand()
	{
		super("spawn");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		player.teleport(WorldsManager.getTheCreative().getSpawnLocation());
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