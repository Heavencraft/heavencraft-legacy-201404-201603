package fr.heavencraft.NavalConflicts.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.NavalConflicts.Utils;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayer;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;
import fr.heavencraft.NavalConflicts.Messages.Msgs;
import fr.heavencraft.NavalConflicts.commands.NavalCommand;
import fr.heavencraft.NavalConflicts.exceptions.NavalException;

public class LeaveCommand extends NavalCommand{

	public LeaveCommand()
	{
		super("leave");
	}
	
	@Override
	protected void onPlayerCommand(Player player, String[] args) throws NavalException
	{
		NCPlayer ip = NCPlayerManager.getNCPlayer(player);
		
		if (!Lobby.isInGame(player))
			player.sendMessage(Msgs.Error_Game_Not_In.getString());

		else
			ip.leaveNavalConflicts();
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws NavalException {
		Utils.sendMessage(sender, "Cette commande ne peut pas être utilisée depuis la {console}.");
		}

	@Override
	protected void sendUsage(CommandSender sender) {}
}
