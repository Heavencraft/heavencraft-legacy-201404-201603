package fr.lorgan17.heavenrp.commands.mod;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;

public class InventoryCommand extends HeavenCommand
{
	public InventoryCommand()
	{
		super("inventory", "heavenrp.moderator.inventory");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length == 1)
		{
			player.openInventory(Utils.getPlayer(args[0]).getInventory());
		}
	}
	
	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande ne peut pas être utilisée depuis la {console}.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "{/endercheat} <joueur> : ouvre l'inventaire du joueur.");
	}
}
