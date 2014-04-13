package fr.lorgan17.heavenrp.commands.admin;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;

public class CreacheatCommand extends HeavenCommand {

	public CreacheatCommand()
	{
		super("creacheat", "heavenrp.administrator.creacheat");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		switch (player.getGameMode())
		{
			case SURVIVAL:
				player.setGameMode(GameMode.CREATIVE);
				break;
			case CREATIVE:
				player.setGameMode(GameMode.SURVIVAL);
				break;
			default:
				break;
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{creacheat}");
	}
}
