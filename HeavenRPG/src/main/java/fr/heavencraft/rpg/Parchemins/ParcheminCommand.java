package fr.heavencraft.rpg.Parchemins;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.rpg.ChatUtil;
import fr.heavencraft.rpg.HeavenCommand;
import fr.heavencraft.rpg.Parchemins.Parchemins.ParcheminAuraDeLaBienfaisansce;
import fr.heavencraft.rpg.Parchemins.Parchemins.ParcheminLePetitPetDuNord;
import fr.heavencraft.rpg.Parchemins.Parchemins.ParcheminLeSouffleDuNecromentien;
import fr.heavencraft.rpg.Parchemins.Parchemins.ParcheminPousseeQuantique;
import fr.heavencraft.rpg.Parchemins.Parchemins.ParcheminTonnereDivin;

public class ParcheminCommand extends HeavenCommand {

	public ParcheminCommand() {
		super("parchemin");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException {
		
		if(args.length == 1)
		{
			if(args[0].equalsIgnoreCase("1"))
				player.getInventory().addItem(new ParcheminLePetitPetDuNord().getItem());
			else if(args[0].equalsIgnoreCase("2"))
				player.getInventory().addItem(new ParcheminTonnereDivin().getItem());
			else if(args[0].equalsIgnoreCase("3"))
				player.getInventory().addItem(new ParcheminPousseeQuantique().getItem());
			else if(args[0].equalsIgnoreCase("4"))
				player.getInventory().addItem(new ParcheminAuraDeLaBienfaisansce().getItem());
			else if(args[0].equalsIgnoreCase("5"))
				player.getInventory().addItem(new ParcheminLeSouffleDuNecromentien().getItem());
			return;	
		}
		
		
		sendUsage(player);
		
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException {
		ChatUtil.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		ChatUtil.sendMessage(sender, "/{parchemin} <1,2,3,4,5> | Donne un parchemin.");
	}

}
