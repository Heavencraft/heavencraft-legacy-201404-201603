package fr.lorgan17.heavenrp.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.worlds.WorldsManager;

public class TravauxCommand extends HeavenCommand
{

	public TravauxCommand()
	{
		super("travaux", "heavenrp.administrator.travaux");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		player.teleport(WorldsManager.getTravaux().getSpawnLocation());
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
