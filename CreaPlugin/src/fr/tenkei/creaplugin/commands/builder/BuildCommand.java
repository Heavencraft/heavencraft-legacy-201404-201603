package fr.tenkei.creaplugin.commands.builder;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.managers.WorldsManager;
import fr.tenkei.creaplugin.utils.Message;

public class BuildCommand extends Command{

	public BuildCommand(MyPlugin plugin) {
		super("build", plugin);
	}

	protected void onPlayerCommand(Player player, String[] args)
			throws MyException {
		if(!player.hasPermission(MyPlugin.builder))
			return;
		
		player.teleport(WorldsManager.getTheTravaux().getSpawnLocation());
		player.setGameMode(GameMode.CREATIVE);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws MyException {
		Message.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		
	}
}
