package fr.heavencraft.aventure.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.aventure.Files;
import fr.heavencraft.aventure.commands.AventureCommand;

public class reloadRegion extends AventureCommand{
	public reloadRegion() {
		super("relrg");
	}
	
	private final static String FORMAT_NC = "�2[HA] �6%1$s";

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws Exception {
		Files.reloadRegions();
		player.sendMessage(String.format(FORMAT_NC, "Les regions on �t� mis a jour."));
		
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
