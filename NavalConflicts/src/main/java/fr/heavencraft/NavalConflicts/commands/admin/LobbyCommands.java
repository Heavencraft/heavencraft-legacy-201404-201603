package fr.heavencraft.NavalConflicts.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.NavalConflicts.Utils;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Messages.Msgs;
import fr.heavencraft.NavalConflicts.commands.NavalCommand;
import fr.heavencraft.NavalConflicts.exceptions.NavalException;

public class LobbyCommands extends NavalCommand{
	public LobbyCommands()
	{
		super("lobby");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws NavalException {
		
		if (args.length == 0) {
			player.teleport(Lobby.getLocation());
			player.sendMessage(Msgs.Command_Lobby_Tp.getString());
			return;
		}
		
		if (args[0].equalsIgnoreCase("set"))
		{
			if (!player.hasPermission("NC.admin" )|| !player.isOp())
			{
				player.sendMessage(Msgs.Error_Misc_No_Permission.getString());	
				return;
			}
			else
			{
				Lobby.setLocation(player.getLocation());
				player.sendMessage(Msgs.Command_Lobby_Set.getString());
			}	
		}
		else if (args[0].equalsIgnoreCase("setleave"))
		{
			if (!player.hasPermission("NC.admin" )|| !player.isOp())
			{
				player.sendMessage(Msgs.Error_Misc_No_Permission.getString());	
				return;
			}
			else
			{
				Lobby.setLeave(player.getLocation());
				player.sendMessage(Msgs.Command_Leave_Location_Set.getString());
			}
		}
		else
		{
			player.sendMessage("Commande inconnue");
			return;
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws NavalException {
		Utils.sendMessage(sender, "Cette commande ne peut pas être utilisée depuis la {console}.");
		
	}

	@Override
	protected void sendUsage(CommandSender sender) {}
}
