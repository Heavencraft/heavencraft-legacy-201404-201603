package fr.manu67100.heavenrp.laposte.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.RPPermissions;
import fr.heavencraft.utils.ChatUtil;
import fr.manu67100.heavenrp.laposte.Files;


public class listPosteCommand extends HeavenCommand{
	public listPosteCommand() {
		super("listPoste");
	}

	private final static String FORMAT_POSTE = "§4[§6La Poste§4] §6%1$s";

	@Override
	protected void onPlayerCommand(Player player, String[] args)
			throws HeavenException {
		
		if(!player.hasPermission(RPPermissions.POSTE_ADMIN))
			{
			player.sendMessage(String.format(FORMAT_POSTE, "Vous n'avez pas les permissions."));
			return;
			}
		
		Files.reloadRegions();
		
		if(Files.getRegions().getConfigurationSection("Regions") != null)
		{
			player.sendMessage(String.format(FORMAT_POSTE, "Regions disponibles"));
			for (String a : Files.getRegions().getConfigurationSection("Regions").getKeys(false))
			{
				player.sendMessage(String.format(FORMAT_POSTE, a));
			}
		}
		else
		{
			player.sendMessage(String.format(FORMAT_POSTE, "Aucune region."));
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws HeavenException {
		ChatUtil.sendMessage(sender, "Cette commande ne peut pas être utilisée depuis la {console}.");
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		ChatUtil.sendMessage(sender, "/listposte Pour voir les bureaux de poste.");
	}
}
