package fr.lorgan17.lorganserver.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.lorgan17.lorganserver.OriginesPermissions;
import fr.lorgan17.lorganserver.managers.WorldsManager;

public class SetspawnCommand extends HeavenCommand
{
	public SetspawnCommand()
	{
		super("setspawn", OriginesPermissions.SETSPAWN);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		WorldsManager.setSpawn(player.getLocation());
		ChatUtil.sendMessage(player, "L'emplacement du spawn a bien été changé.");
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
