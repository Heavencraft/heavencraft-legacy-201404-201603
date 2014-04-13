package fr.lorgan17.heavenrp.commands.mod;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;

public class TpposCommand extends HeavenCommand {

	public TpposCommand()
	{
		super("tppos", "heavenrp.moderator.tppos");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length != 3)
		{
			sendUsage(player);
			return;
		}

		int x = Utils.toInt(args[0]);
		int y = Utils.toInt(args[1]);
		int z = Utils.toInt(args[2]);
		
		player.teleport(new Location(player.getWorld(), x, y, z));
		Utils.sendMessage(player, "Vous avez été téléporté en x = {%1$d}, y = {%2$d}, z = {%3$d}.", x, y, z);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{tppos} <x> <y> <z>");
	}
}
