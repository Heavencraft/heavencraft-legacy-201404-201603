package fr.heavencraft.aventure.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.aventure.Files;
import fr.heavencraft.aventure.commands.AventureCommand;

public class setGreetingCommand extends AventureCommand{
	public setGreetingCommand() {
		super("setgreeting");
	}

	private final static String FORMAT_NC = "�2[HA] �6%1$s";

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws Exception {
		Files.reloadRegions();


		if (args.length <= 1) {
			player.sendMessage(String.format(FORMAT_NC, "/setgreeting <region> <string>"));
			player.sendMessage(String.format(FORMAT_NC, "Pour enlever le message: ''  (apostphes)"));
		}
		else
		{


			if(Files.getRegions().contains("Regions." + args[0].toLowerCase() + ".Enable"))
			{
				Files.getRegions().set("Regions." + args[0].toLowerCase() + ".Enable", true);			
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++){
					sb.append(args[i]).append(" ");
				}
				String allArgs = sb.toString().trim();

				
				if (allArgs.equalsIgnoreCase("''"))
						Files.getRegions().set("Regions." + args[0].toLowerCase() + ".Greeting", ""); //message vide
				else
					Files.getRegions().set("Regions." + args[0].toLowerCase() + ".Greeting", allArgs);
				Files.saveRegions();

				player.sendMessage(String.format(FORMAT_NC, "Message de la r�gion mis a jour."));
			}
			else
			{
				player.sendMessage(String.format(FORMAT_NC, "Region " + args[0].toLowerCase() + " inconnue."));
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
