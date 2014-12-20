package fr.lorgan17.maya;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class MayaCommand implements CommandExecutor
{
	public MayaCommand(String commandName)
	{
		MayaPlugin.getInstance().getCommand(commandName).setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		try
		{
			if (sender instanceof Player)
				onPlayerCommand((Player) sender, args);
			else
				onConsoleCommand(sender, args);
		}
		catch (Throwable t)
		{
			MayaPlugin.sendMessage(sender, t.getMessage());
		}

		return false;
	}

	public abstract void onPlayerCommand(Player player, String[] args);

	public abstract void onConsoleCommand(CommandSender sender, String[] args);

	public abstract void sendUsage(CommandSender sender);
}