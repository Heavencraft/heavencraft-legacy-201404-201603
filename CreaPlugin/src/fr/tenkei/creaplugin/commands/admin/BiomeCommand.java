package fr.tenkei.creaplugin.commands.admin;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.managers.WorldsManager;
import fr.tenkei.creaplugin.utils.Message;

public class BiomeCommand extends Command{

	public BiomeCommand(MyPlugin plugin) {
		super("tpbiome", plugin);
	}

	protected void onPlayerCommand(Player player, String[] args)
			throws MyException {
		if(!player.hasPermission(MyPlugin.administrator))
			return;
		
		player.teleport(WorldsManager.getTheCreativeBiome().getSpawnLocation());
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
