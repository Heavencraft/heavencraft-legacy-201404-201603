package fr.tenkei.creaplugin.managers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.managers.entities.Region;
import fr.tenkei.creaplugin.managers.entities.User;
import fr.tenkei.creaplugin.utils.Message;
import fr.tenkei.creaplugin.utils.Stuff;

public class AVManager {
	
	private ManagersManager _manager;
	
	public AVManager(ManagersManager manager)
	{
		this._manager = manager;	
	}
	
	public void aVendre(SignChangeEvent event)
	{
		
		if(!event.getPlayer().hasPermission(MyPlugin.administrator))
			return;
		
		Region region = Region.getRegionByLocation(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ(), event.getBlock().getWorld()) ;
		
		if(region == null) {
			Message.sendMessage(event.getPlayer(), "{Aucune région ne peut être vendu ici..");
			return;
		}

		event.setLine(0, ChatColor.DARK_GREEN + "[AV]");
		event.setLine(2, "Prix de vente");
		event.setLine(3, region.getPrize() + " Jetons");		
	}
	
	public void achat(Sign sign, Player p) throws MyException
	{
		if(sign.getBlock().getWorld() != WorldsManager.getTheCreative()) {
			 Message.sendMessage(p, "{Vous avez a faire à un fraudeur !");
			return;
		}
		
		Region region = Region.getRegionByLocation(sign.getBlock().getX(), sign.getBlock().getY(), sign.getBlock().getZ(), sign.getBlock().getWorld()) ;
		
		if(region == null) {
			Message.sendMessage(p, "{Aucune région ne peut être vendu.");
			return;
		}else if(region.isRealMember(p.getName())) {
			Message.sendMessage(p, "{Tu essayes d'acheter ta propre parcelle ?");
			return;
		}
		
		int prix = Integer.parseInt( ChatColor.stripColor(sign.getLine(3)).replace(" Jetons", "") );
		
		User user = _manager.getUserManager().getUser(p.getName());
		
	    Block confirmBlock = user.getInteractBlock();

	    if ((confirmBlock == null) || !Stuff.blocksEquals(confirmBlock, sign.getBlock())) {
	      user.setInteractBlock(sign.getBlock());
	      Message.sendMessage(p, "Cliquez une seconde fois pour confirmer l'achat de cette parcelle pour {" + prix +"} Jetons.");
	      return;
	    }

	    user.setInteractBlock(null);

		user.updateBalance(-prix);
		
		// Achat comfirmé
		
		region.addMember(user, true);
		
		sign.getBlock().setType(Material.AIR);

		region.carre();
		
		Message.sendMessage(p, "Vous êtes maintenant l'heureux propriétaire de cette parcelle, cela vous a couté {" + prix + "} Jetons !");
		user.stateBalance();
	}
}
