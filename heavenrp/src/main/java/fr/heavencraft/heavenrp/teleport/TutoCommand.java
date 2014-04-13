package fr.heavencraft.heavenrp.teleport;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.worlds.WorldsManager;

public class TutoCommand extends HeavenCommand {

	public TutoCommand()
	{
		super("tuto");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		//player.teleport(WorldsManager.getTutoLocation());
		Utils.teleportPlayer(player, WorldsManager.getTutoLocation());
		Utils.sendMessage(player, "Vous avez été téléporté au tutoriel.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}
