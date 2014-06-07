package fr.tenkei.creaplugin.commands.user.teleport;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.managers.entities.User;
import fr.tenkei.creaplugin.utils.Message;

public class AccepterCommand extends Command{

	public AccepterCommand(MyPlugin plugin) {
		super("accepter", plugin);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args)
			throws MyException {
		
		User user = getUser();
				
		String teleportRequestName = user.getTeleportRequestName();
		
		if (teleportRequestName.isEmpty()) {
			Message.sendMessage(player, "Vous n'avez aucune demande de téléportation en attente.");
			return;
		}

		user.setTeleportRequestName("");

		Player otherPlayer = getPlayer(teleportRequestName);

		if (otherPlayer == null) {
			Message.sendMessage(player, "Vous n'avez aucune demande de téléportation en attente.");
			return;
		}

		if (otherPlayer.getWorld() != player.getWorld()) {
			Message.sendMessage(player, "Le joueur {" + otherPlayer.getDisplayName() + "} n'est pas dans le même monde que vous.");
			return;
		}

		otherPlayer.teleport(player);

		Message.sendMessage(otherPlayer, "Vous êtes téléporté vers {" + player.getDisplayName() + "}.");
		Message.sendMessage(player,"Vous venez de téléporter {" + otherPlayer.getDisplayName() + "} vers vous.");
	}


	@Override
	protected void sendUsage(CommandSender sender) {
		
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws MyException {
	
	}
}
