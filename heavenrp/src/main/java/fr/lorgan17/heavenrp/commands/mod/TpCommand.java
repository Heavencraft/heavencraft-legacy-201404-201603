package fr.lorgan17.heavenrp.commands.mod;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;

public class TpCommand extends HeavenCommand {

	public TpCommand()
	{
		super("tp", "heavenrp.moderator.tp");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		Player toTeleport;
		Player destination;
		
		switch (args.length)
		{
			case 1:
				toTeleport = player;
				destination = Utils.getPlayer(args[0]);
				Utils.sendMessage(player, "Téléportation vers {%1$s}.", destination.getName());
				break;
			case 2:
				toTeleport = Utils.getPlayer(args[0]);
				destination = Utils.getPlayer(args[1]);
				Utils.sendMessage(player,"Téléportation de {%1$s} vers {%2$s}.", toTeleport.getName(), destination.getName());
				break;
			default:
				sendUsage(player);
				return;
		}
		
		if (!destination.hasPermission("heavenrp.administrator.notp"))
			toTeleport.teleport(destination);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "/{tp} <joueur>");
		Utils.sendMessage(sender, "/{tp} <joueur1> <joueur2>");
	}
}
