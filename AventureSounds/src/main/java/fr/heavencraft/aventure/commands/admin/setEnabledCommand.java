package fr.heavencraft.aventure.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.aventure.Files;
import fr.heavencraft.aventure.commands.AventureCommand;

public class setEnabledCommand extends AventureCommand{
	public setEnabledCommand() {
		super("setEnabled");
	}

	private final static String FORMAT_NC = "§2[HA] §6%1$s";

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws Exception {
		Files.reloadRegions();
		if (args.length != 2) {
			player.sendMessage(String.format(FORMAT_NC, "/setEnabled <region> <boolean>"));
		}
		else
		{

			if(Files.getRegions().contains("Regions." + args[0] + ".Enable"))
			{
				if(args[1].equalsIgnoreCase("true"))
				{
					Files.getRegions().set("Regions." + args[0] + ".Enable", true);
					Files.saveRegions();
					player.sendMessage(String.format(FORMAT_NC, "Region activée."));
				}
				else if (args[1].equalsIgnoreCase("false"))
				{
					Files.getRegions().set("Regions." + args[0] + ".Enable", false);
					Files.saveRegions();
					player.sendMessage(String.format(FORMAT_NC, "Region désactivée."));
				}
				else
				{
					player.sendMessage(String.format(FORMAT_NC, "Erreur d'argument"));
				}
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
