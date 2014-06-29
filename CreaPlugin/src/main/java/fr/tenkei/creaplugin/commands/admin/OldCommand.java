package fr.tenkei.creaplugin.commands.admin;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.tenkei.creaplugin.CreaPermissions;
import fr.tenkei.creaplugin.managers.WorldsManager;

public class OldCommand extends HeavenCommand
{
	public OldCommand()
	{
		super("tpold", CreaPermissions.OLD);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		player.teleport(WorldsManager.getTheCreativeOld().getSpawnLocation());
		player.setGameMode(GameMode.CREATIVE);
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