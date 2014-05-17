package fr.tenkei.creaplugin.commands.user.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.managers.entities.User;
import fr.tenkei.creaplugin.utils.Message;

public class RejoindreCommand extends Command{

	public RejoindreCommand(MyPlugin plugin) {
		super("rejoindre", plugin);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args)
			throws MyException {

		if (args.length != 1){
			sendUsage(player);
			return;
		}

		Player otherPlayer = Bukkit.getPlayer(args[0]);

		if (otherPlayer == null) {
			Message.sendMessage(player, "Le joueur {" + args[0] + "} n'existe pas.");
			return;
		}

		if (otherPlayer.getWorld() != player.getWorld()){
			Message.sendMessage(player, "Le joueur {" + otherPlayer.getDisplayName() + "} n'est pas dans le même monde que vous.");
			return;
		}

		User otherUser = getUser(otherPlayer.getName());
		otherUser.setTeleportRequestName(player.getName());

		Message.sendMessage(otherPlayer, "Le joueur {" + player.getDisplayName() + "} souhaite se téléporter vers vous. Tapez {/accepter} pour accepter.");
		Message.sendMessage(player, "Une invitation de téléportation a été envoyée vers {" + otherPlayer.getDisplayName() + "}.");
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws MyException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void sendUsage(CommandSender sender) {
		Message.sendMessage(sender, "{/rejoindre} <joueur>");
	}
}
