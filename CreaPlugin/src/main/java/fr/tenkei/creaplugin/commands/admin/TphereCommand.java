package fr.tenkei.creaplugin.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.utils.Message;

public class TphereCommand extends Command{

	public TphereCommand(MyPlugin plugin) {
		super("tphere", plugin);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args)
			throws MyException {
		
		if(!player.hasPermission(MyPlugin.administrator))
			return;
		
		if (args.length == 1)
        {
                Player otherPlayer = Bukkit.getPlayer(args[0]);
                if (otherPlayer != null)
                {
                        otherPlayer.teleport(player.getLocation());
                        Message.sendMessage(player, "Téléportation de {" + otherPlayer.getDisplayName() + "}.");
                }
                else
                	Message.sendMessage(player, "Le joueur {" + args[0] + "} n'existe pas.");
        }
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws MyException {
	
	}

	@Override
	protected void sendUsage(CommandSender sender) {
	
	}

}
