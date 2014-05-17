package fr.tenkei.creaplugin.commands.user.teleport;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.managers.WorldsManager;
import fr.tenkei.creaplugin.utils.Message;

public class SpawnCommand extends Command {

	public SpawnCommand(MyPlugin plugin)
	{
		super("spawn", plugin);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws MyException
	{
		player.teleport(WorldsManager.getTheCreative().getSpawnLocation());
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws MyException
	{
		Message.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender) 
	{
		
	}
}
