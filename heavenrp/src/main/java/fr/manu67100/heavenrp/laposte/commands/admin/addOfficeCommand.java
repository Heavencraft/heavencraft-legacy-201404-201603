package fr.manu67100.heavenrp.laposte.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.RPPermissions;
import fr.heavencraft.utils.ChatUtil;
import fr.manu67100.heavenrp.laposte.Files;
import fr.manu67100.heavenrp.laposte.handlers.PostOfficeManager;

public class addOfficeCommand extends HeavenCommand{

	public addOfficeCommand() {
		super("addPoste");
	}

	private final static String FORMAT_POSTE = "§4[§6La Poste§4] §6%1$s";

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException {
		if(player.hasPermission(RPPermissions.POSTE_ADMIN))
		{
			if (args.length != 1) 
			{
				player.sendMessage(String.format(FORMAT_POSTE, "Veuillez spécifier la région."));
				return;
			}

			//jouter la region de la config.
			if(Files.getRegions().contains("Regions." + args[0].toLowerCase() + ".Enable") == false)
			{
				Files.getRegions().set("Regions." + args[0].toLowerCase() + ".Enable", true);
				Files.getRegions().set("Regions." + args[0].toLowerCase() + ".Sound", "");
				Files.saveRegions();
				PostOfficeManager.LoadOffices();		
			}
			else
				player.sendMessage(String.format(FORMAT_POSTE, "La région existe déjà."));
		}
		else
			player.sendMessage(String.format(FORMAT_POSTE, "Vous n'avez pas les permissions."));


	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException {
		ChatUtil.sendMessage(sender, "Cette commande ne peut pas être utilisée depuis la {console}.");
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		ChatUtil.sendMessage(sender, "/addposte <region> Pour ajouter la poste.");
	}

}
