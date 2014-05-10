package fr.heavencraft.aventure.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.aventure.Files;
import fr.heavencraft.aventure.commands.AventureCommand;

public class setSoundCommand extends AventureCommand{
	public setSoundCommand() {
		super("setSound");
	}

	private final static String FORMAT_NC = "§2[HA] §6%1$s";

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws Exception {
		Files.reloadRegions();
		if (args.length != 2) {
			player.sendMessage(String.format(FORMAT_NC, "/setsound <region> <string>"));
		}
		else
		{
			if(Files.getRegions().contains("Regions." + args[0].toLowerCase() + ".Enable"))
			{
				Files.getRegions().set("Regions." + args[0].toLowerCase() + ".Enable", true);
				Files.getRegions().set("Regions." + args[0].toLowerCase() + ".Sound", args[1]);
				Files.saveRegions();
				player.sendMessage(String.format(FORMAT_NC, "Le son de la region a été mis a jour."));
			}
			else
			{
				player.sendMessage(String.format(FORMAT_NC, "Region " + args[0] + " inconnue."));
			}
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
