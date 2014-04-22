package fr.heavencraft.aventure.commands.admin;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.aventure.Files;
import fr.heavencraft.aventure.commands.AventureCommand;

public class listRegionsCommand extends AventureCommand{
	public listRegionsCommand() {
		super("listRegions");
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
		// TODO Auto-generated method stub

	}

	@Override
	protected void sendUsage(CommandSender sender) {
		// TODO Auto-generated method stub

	}
}
