package fr.lorgan17.heavenrp.commands.mod;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;

public class TphereCommand extends HeavenCommand {

	public TphereCommand()
	{
		super("tphere", "heavenrp.moderator.tphere");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length != 1)
		{
			sendUsage(player);
			return;
		}

		Player toTeleport = Utils.getPlayer(args[0]);
		
		toTeleport.teleport(player);
		Utils.sendMessage(toTeleport, "Vous avez été téléporté par {%1$s}.", player.getName());
		Utils.sendMessage(player, "Téléportation de {%1$s}.", toTeleport.getName());
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws HeavenException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void sendUsage(CommandSender sender) {
		// TODO Auto-generated method stub

	}

}
