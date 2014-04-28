package fr.heavencraft.laposte.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.laposte.Files;
import fr.heavencraft.laposte.commands.LaPosteCommand;

public class listPosteCommand extends LaPosteCommand{
	public listPosteCommand() {
		super("listPoste");
	}

	private final static String FORMAT_NC = "§2[HA] §6%1$s";

	@Override
	protected void onPlayerCommand(Player player, String[] args)
			throws Exception {
		Files.reloadRegions();
		
		if(Files.getRegions().getConfigurationSection("Regions") != null)
		{
			player.sendMessage(String.format(FORMAT_NC, "Regions disponibles"));
			for (String a : Files.getRegions().getConfigurationSection("Regions").getKeys(false))
			{
				player.sendMessage(String.format(FORMAT_NC, a));
			}
		}
		else
		{
			player.sendMessage(String.format(FORMAT_NC, "Aucune region."));
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws Exception {

	}

	@Override
	protected void sendUsage(CommandSender sender) {

	}
}
