package fr.heavencraft.railways;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.Utils.ChatUtil;
import fr.heavencraft.exceptions.HeavenException;

public class StationCommand extends HeavenCommand{

	public StationCommand() {
		super("Station");
	}

	private final static String STATION_SET = "[SHCF] Vous avez choisi {%1$s} comme destination.";
	
	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException {
		if(args.length != 1){
			sendUsage(player);
			return;
		}
			PlayerStationManager.addUser(player.getUniqueId(), args[0]);
			ChatUtil.sendMessage(player, STATION_SET, args[0]);
			return;
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException {
		ChatUtil.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
		
	}

	@Override
	protected void sendUsage(CommandSender sender) {
		ChatUtil.sendMessage(sender, "/{station} <Nom de la station> | Defini la station voulue.");
	}

}
