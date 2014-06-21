package fr.heavencraft.heavenrp.commands.teleport;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.worlds.WorldsManager;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.PlayerUtil;

public class TutoCommand extends HeavenCommand
{

	public TutoCommand()
	{
		super("tuto");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		// player.teleport(WorldsManager.getTutoLocation());
		PlayerUtil.teleportPlayer(player, WorldsManager.getTutoLocation());
		ChatUtil.sendMessage(player, "Vous avez été téléporté au tutoriel.");
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
