package fr.heavencraft.HellCraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import fr.heavencraft.HellCraft.HellCraft;
import fr.heavencraft.HellCraft.Utils;
import fr.heavencraft.HellCraft.exceptions.HellException;

public abstract class HellCommand implements CommandExecutor
{
	public HellCommand(String name)
	{
		this(name, "");
	}

	public HellCommand(String name, String permission)
	{
		try
		{
			final PluginCommand command = HellCraft.getInstance().getCommand(name);

			command.setExecutor(this);
			command.setPermissionMessage("");
			command.setPermission(permission);
		}
		catch (final Exception localException)
		{
		}
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		try
		{
			if ((sender instanceof Player))
				onPlayerCommand((Player) sender, args);
			else
				onConsoleCommand(sender, args);
		}
		catch (final HellException ex)
		{
			Utils.sendMessage(sender, ex.getMessage());
		}

		return true;
	}

	protected abstract void onPlayerCommand(Player paramPlayer, String[] paramArrayOfString) throws HellException;

	protected abstract void onConsoleCommand(CommandSender paramCommandSender, String[] paramArrayOfString) throws HellException;

	protected abstract void sendUsage(CommandSender paramCommandSender);
}