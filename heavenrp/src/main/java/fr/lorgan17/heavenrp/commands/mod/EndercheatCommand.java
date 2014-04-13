package fr.lorgan17.heavenrp.commands.mod;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;

public class EndercheatCommand extends HeavenCommand {

	public EndercheatCommand()
	{
		super("endercheat", "heavenrp.moderator.endercheat");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length != 1)
		{
			sendUsage(player);
			return;
		}
		
		player.openInventory(Utils.getPlayer(args[0]).getEnderChest());
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}
	
	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{endercheat} <joueur>");
	}
}
