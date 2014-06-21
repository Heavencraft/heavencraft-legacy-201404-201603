package fr.heavencraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.DevUtil;

public abstract class HeavenCommand implements CommandExecutor
{
	public HeavenCommand(String name)
	{
		this(name, "");
	}

	public HeavenCommand(String name, String permission)
	{
		PluginCommand command = DevUtil.getPlugin().getCommand(name);

		command.setExecutor(this);
		command.setPermission(permission);
		command.setPermissionMessage("");
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

		catch (HeavenException ex)
		{
			ChatUtil.sendMessage(sender, ex.getMessage());
		}

		return true;
	}

	protected abstract void onPlayerCommand(Player player, String[] args) throws HeavenException;

	protected abstract void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException;

	protected abstract void sendUsage(CommandSender sender);
}