package fr.heavencraft.rpg.zones;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.heavencraft.Utils.ChatUtil;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.rpg.HeavenCommand;
import fr.heavencraft.rpg.HeavenRPG;
import fr.heavencraft.rpg.RPGFiles;
import fr.heavencraft.rpg.RPGpermissions;

public class ZoneCommand extends HeavenCommand {
	private final static String NO_SELECTION = "Vous devez d'abord faire une selection avec World Edit.";
	private final static String SELECTION_SET = "La zone a été ajoutée.";
	private final static String NO_PERMISSION = "Vous n'avez pas la permission";
	private final static String FOUND_X_ZONES = "Vous ètes dans: {%1$s} zones:";
	private final static String ZONE_INFO = "{%1$s} \n Niveau: {%2$s}";
	private final static String IN_NO_ZONE = "Aucune zone ici.";
	private final static String ZONE_DELTED = "La zone {%1$s} a été supprimée.";
	private final static String ZONE_INVALID = "La zone {%1$s} n'existe pas!";
	private final static String MOB_TYPE_INVALID = "Le type de mob n'existe pas.";
	private final static String MOB_ATTR_INVALID = "Ce parametre n'existe pas.";
	private final static String NOT_ENOUGH_ARGS = "Arguents invalides!";
	
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
			ArrayList<Zone> foundZones = ZoneManager.getZones(player.getLocation());
			if(foundZones.size() == 0)
				ChatUtil.sendMessage(player, IN_NO_ZONE);
			else
			{
				ChatUtil.sendMessage(player, FOUND_X_ZONES, foundZones.size());
				for(Zone z : foundZones)
					ChatUtil.sendMessage(player, ChatColor.DARK_GREEN + "- " + z.get_name() + ChatColor.GOLD + " | lvl: " + z.get_zoneLevel());
			}
		} 
		else 
		{
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
						ChatUtil.sendMessage(player, SELECTION_SET);
					}
				}		
				return;
			}	
			else if (args[0].equalsIgnoreCase("delete"))
			{
				if(args.length != 2)
					sendUsage(player);
				else
				{
					if(ZoneManager.getZoneByName(args[1]) != null)
					{
						ZoneManager.removeZone(ZoneManager.getZoneByName(args[1]));
						ChatUtil.sendMessage(player, ZONE_DELTED, args[1]);
						return;
					}
					ChatUtil.sendMessage(player, ZONE_INVALID, args[1]);
				}
			}
			else if (args[0].equalsIgnoreCase("set"))
			{
				if(args.length < 3)
				{
					ChatUtil.sendMessage(player, "/zone set <zone> <mob> <param> <value>| Modifie un parametre de la zone.");
					ChatUtil.sendMessage(player, "<mob> | zombie/skeleton/spider/creeper");
					ChatUtil.sendMessage(player, "<param> | enabled/name/life/damage/head/chest/leg/boot");
					return;
				}		
				
				Zone zone = ZoneManager.getZoneByName(args[1]);
				if(zone == null)
				{
					ChatUtil.sendMessage(player, ZONE_INVALID, args[1]);
					return;
				}
				
				
				
				
				HashMap<String, EntityType> MobTypesHmap = new HashMap<String, EntityType>();
				MobTypesHmap.put("zombie", EntityType.ZOMBIE);
				MobTypesHmap.put("skeleton", EntityType.SKELETON);
				MobTypesHmap.put("spider", EntityType.SPIDER);
				MobTypesHmap.put("creeper", EntityType.CREEPER);
				
				if(!MobTypesHmap.containsKey(args[2]))
				{
					ChatUtil.sendMessage(player, MOB_TYPE_INVALID);
					return;
				}
				
				EntityType mobtype = MobTypesHmap.get(args[2]);
				
				if(args.length < 4)
				{
					ChatUtil.sendMessage(player, NOT_ENOUGH_ARGS);
					return;
				}
				
				HashMap<String, SecondaryMobParameter> ParamsHmap = new HashMap<String, SecondaryMobParameter>();
				ParamsHmap.put("enabled", SecondaryMobParameter.ENABLED);
				ParamsHmap.put("name", SecondaryMobParameter.NAME);
				ParamsHmap.put("life", SecondaryMobParameter.LIFE);
				ParamsHmap.put("damage", SecondaryMobParameter.DAMAGE);
				ParamsHmap.put("head", SecondaryMobParameter.HEAD);
				ParamsHmap.put("chest", SecondaryMobParameter.CHEST);
				ParamsHmap.put("leg", SecondaryMobParameter.LEG);
				ParamsHmap.put("boot", SecondaryMobParameter.BOOT);
				if(!ParamsHmap.containsKey(args[3]))
				{
					ChatUtil.sendMessage(player, MOB_ATTR_INVALID);
					return;
				}	
				
				switch(ParamsHmap.get(args[3]))
				{

				case HEAD:
					if(mobtype == EntityType.ZOMBIE)
						zone.getZombieAttribute().setHead(player.getItemInHand());
					else if(mobtype == EntityType.SKELETON)
						zone.getSkeletonAttribute().setHead(player.getItemInHand());
					else if(mobtype == EntityType.SPIDER)
						zone.getSpiderAttribute().setHead(player.getItemInHand());
					else if(mobtype == EntityType.CREEPER)
						zone.getCreeperAttribute().setHead(player.getItemInHand());
					break;
					
				case CHEST:
					if(mobtype == EntityType.ZOMBIE)
						zone.getZombieAttribute().setChest(player.getItemInHand());
					else if(mobtype == EntityType.SKELETON)
						zone.getSkeletonAttribute().setChest(player.getItemInHand());
					else if(mobtype == EntityType.SPIDER)
						zone.getSpiderAttribute().setChest(player.getItemInHand());
					else if(mobtype == EntityType.CREEPER)
						zone.getCreeperAttribute().setChest(player.getItemInHand());
					break;
					
				case LEG:
					if(mobtype == EntityType.ZOMBIE)
						zone.getZombieAttribute().setLeggings(player.getItemInHand());
					else if(mobtype == EntityType.SKELETON)
						zone.getSkeletonAttribute().setLeggings(player.getItemInHand());
					else if(mobtype == EntityType.SPIDER)
						zone.getSpiderAttribute().setLeggings(player.getItemInHand());
					else if(mobtype == EntityType.CREEPER)
						zone.getCreeperAttribute().setLeggings(player.getItemInHand());		
					break;
					
				case BOOT:
					if(mobtype == EntityType.ZOMBIE)
						zone.getZombieAttribute().setBoots(player.getItemInHand());
					else if(mobtype == EntityType.SKELETON)
						zone.getSkeletonAttribute().setBoots(player.getItemInHand());
					else if(mobtype == EntityType.SPIDER)
						zone.getSpiderAttribute().setBoots(player.getItemInHand());
					else if(mobtype == EntityType.CREEPER)
						zone.getCreeperAttribute().setBoots(player.getItemInHand());
					break;
					
				case DAMAGE:
					if(args.length < 5)
					{
						ChatUtil.sendMessage(player, NOT_ENOUGH_ARGS);
						return;
					}
					if(mobtype == EntityType.ZOMBIE)
						zone.getZombieAttribute().setDamage(Double.parseDouble(args[4]));
					else if(mobtype == EntityType.SKELETON)
						zone.getSkeletonAttribute().setDamage(Double.parseDouble(args[4]));
					else if(mobtype == EntityType.SPIDER)
						zone.getSpiderAttribute().setDamage(Double.parseDouble(args[4]));
					else if(mobtype == EntityType.CREEPER)
						zone.getCreeperAttribute().setDamage(Double.parseDouble(args[4]));
					break;
					
				case ENABLED:
					if(args.length < 5)
					{
						ChatUtil.sendMessage(player, NOT_ENOUGH_ARGS);
						return;
					}
					
					if(mobtype == EntityType.ZOMBIE)
						zone.getZombieAttribute().setEnabled(Boolean.parseBoolean(args[4]));
					else if(mobtype == EntityType.SKELETON)
						zone.getSkeletonAttribute().setEnabled(Boolean.parseBoolean(args[4]));
					else if(mobtype == EntityType.SPIDER)
						zone.getSpiderAttribute().setEnabled(Boolean.parseBoolean(args[4]));
					else if(mobtype == EntityType.CREEPER)
						zone.getCreeperAttribute().setEnabled(Boolean.parseBoolean(args[4]));
					break;	
					
				case LIFE:
					if(args.length < 5)
					{
						ChatUtil.sendMessage(player, NOT_ENOUGH_ARGS);
						return;
					}
					if(mobtype == EntityType.ZOMBIE)
						zone.getZombieAttribute().setLife(Double.parseDouble(args[4]));
					else if(mobtype == EntityType.SKELETON)
						zone.getSkeletonAttribute().setLife(Double.parseDouble(args[4]));
					else if(mobtype == EntityType.SPIDER)
						zone.getSpiderAttribute().setLife(Double.parseDouble(args[4]));
					else if(mobtype == EntityType.CREEPER)
						zone.getCreeperAttribute().setLife(Double.parseDouble(args[4]));
					break;
					
				case NAME:
					if(args.length < 5)
					{
						ChatUtil.sendMessage(player, NOT_ENOUGH_ARGS);
						return;
					}
					if(mobtype == EntityType.ZOMBIE)
						zone.getZombieAttribute().setCustomName(args[4]);
					else if(mobtype == EntityType.SKELETON)
						zone.getSkeletonAttribute().setCustomName(args[4]);
					else if(mobtype == EntityType.SPIDER)
						zone.getSpiderAttribute().setCustomName(args[4]);
					else if(mobtype == EntityType.CREEPER)
						zone.getCreeperAttribute().setCustomName(args[4]);
					break;			
				}
				
				
				
			}	
			else
				if(ZoneManager.getZoneByName(args[0]) != null)
					ChatUtil.sendMessage(player, ZONE_INFO, ZoneManager.getZoneByName(args[0]).getName(), ZoneManager.getZoneByName(args[0]).getZoneLevel() + "");
				else
					sendUsage(player);
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
		ChatUtil.sendMessage(sender, "/{zone} delete <nom de la zone> | Supprime une zone.");
		ChatUtil.sendMessage(sender, "/zone set <zone> <mob> <param> <value>| Modifie un parametre de la zone.");
		ChatUtil.sendMessage(sender, "<mob> | zombie/skeleton/spider/creeper");
		ChatUtil.sendMessage(sender, "<param> | enabled/name/life/damage/head/chest/leg/boot");
	}

}

enum SecondaryMobParameter {
	ENABLED("enabled"),
	NAME("customname"),
	LIFE("damage"),
	DAMAGE("life"),
	HEAD("head"),
	CHEST("chestplate"),
	LEG("leggings"),
	BOOT("boots");
	
	private String path;
	SecondaryMobParameter(String p)
	{
		setPath(p);
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
