package fr.tenkei.creaplugin.commands.admin;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.managers.WorldsManager;
import fr.tenkei.creaplugin.utils.Message;


public class SetspawnCommand extends Command {

	public SetspawnCommand(MyPlugin plugin)
	{
		super("setspawn", plugin);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws MyException
	{
		Location newLoc = player.getLocation();
		WorldsManager.setSpawn(newLoc, player.getWorld());
		Message.sendMessage(player, "L'emplacement du spawn du monde " + player.getWorld().getName() + " a bien été changé.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws MyException
	{
		
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		
	}
}
