package fr.heavencraft.rpg.zones;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.rpg.ChatUtil;
import fr.heavencraft.rpg.HeavenCommand;
import fr.heavencraft.rpg.HeavenRPG;
import fr.heavencraft.rpg.RPGFiles;
import fr.heavencraft.rpg.RPGpermissions;

public class ZoneCommand extends HeavenCommand {
	private final static String NO_SELECTION = "Vous devez d'abord faire une selection avec World Edit.";
	private final static String SELECTION_SET = "La zone a été ajoutée.";
	private final static String NO_PERMISSION = "Vous n'avez pas la permission";
	private final static String IN_ZONE_XXX = "Vous ètes dans la zone: {%1$s}.";
	private final static String ZONE_INFO = "{%1$s} \n Niveau: {%2$s}";
	private final static String IN_NO_ZONE = "Aucune zone ici.";
	
	public ZoneCommand()
	{
		super("zone");
	}
	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException {
		if (!player.hasPermission(RPGpermissions.ZONES))
		{
			ChatUtil.sendMessage(player, NO_PERMISSION);
			return;
		}


		if(args.length == 0)
		{
			if(ZoneManager.getZone(player.getLocation()) == null)
				ChatUtil.sendMessage(player, IN_NO_ZONE);
			else
				ChatUtil.sendMessage(player, IN_ZONE_XXX, ZoneManager.getZone(player.getLocation()).getName());
		} 
		else {

			if(args[0].equalsIgnoreCase("create"))
			{
				if(args.length != 3)
					sendUsage(player);
				else
				{
					Selection s = HeavenRPG.getWorldEdit().getSelection(player);
					if(s == null)
						ChatUtil.sendMessage(player, NO_SELECTION);
					else {
						
						ZoneManager.createZone(args[1], Integer.parseInt(args[2]), new CuboidSelection(s.getWorld(),s.getMinimumPoint(),s.getMaximumPoint()));	
						RPGFiles.saveZones();
						Bukkit.broadcastMessage(args[1]);
						ChatUtil.sendMessage(player, SELECTION_SET);
					}
				}		
				return;
			}
			else
			{
				if(ZoneManager.getZone(args[0]) != null)
					ChatUtil.sendMessage(player, ZONE_INFO, ZoneManager.getZone(args[0]).getName(), ZoneManager.getZone(args[0]).getZoneLevel() + "");
			}

		}

	}
	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException {
		ChatUtil.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");

	}
	@Override
	protected void sendUsage(CommandSender sender) {
		ChatUtil.sendMessage(sender, "/{zone} | Affiche la zone dans laquelle vous vous trouvez actuellement.");
		ChatUtil.sendMessage(sender, "/{zone} <nom de zone unique> | Affiche des informations sur les zones.");
		ChatUtil.sendMessage(sender, "/{zone} create <nom de la zone> <niveau> | Crée une nouvelle zone.");
	}

}
