package fr.tenkei.creaplugin.commands.admin;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.tenkei.creaplugin.CreaPermissions;
import fr.tenkei.creaplugin.managers.WorldsManager;

public class SetspawnCommand extends HeavenCommand
{
	public SetspawnCommand()
	{
		super("setspawn", CreaPermissions.SETSPAWN);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		Location newLoc = player.getLocation();
		WorldsManager.setSpawn(newLoc, player.getWorld());
		ChatUtil.sendMessage(player, "L'emplacement du spawn du monde {%1$s} a bien été changé.", player.getWorld()
				.getName());
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
