package fr.heavencraft.laposte.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.laposte.commands.LaPosteCommand;
import fr.heavencraft.laposte.handlers.PostOfficeManager;

public class reloadRegion extends LaPosteCommand{
	public reloadRegion() {
		super("reloadposte");
	}
	
	private final static String FORMAT_POSTE = "§4[§6La Poste§4] §6%1$s";

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws Exception {
		
		if(player.hasPermission("LaPoste.admin") || player.isOp())
		{
			PostOfficeManager.LoadOffices();
			player.sendMessage(String.format(FORMAT_POSTE, "Les regions on été mis a jour."));
		}
		else
			player.sendMessage(String.format(FORMAT_POSTE, "Vous n'avez pas les permissions."));
		
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws Exception {
		
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		
	}
}
