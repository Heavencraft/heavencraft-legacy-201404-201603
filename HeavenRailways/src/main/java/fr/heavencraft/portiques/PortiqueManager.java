package fr.heavencraft.portiques;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.heavencraft.Utils.ChatUtil;
import fr.heavencraft.railways.HeavenRailway;

public class PortiqueManager implements Listener
{	
	
	public static List<Portique> portiquesOuverts = new ArrayList<Portique>();
	public static HashMap<String, Portique> portiquesOccupes = new HashMap<String, Portique>();
	private final static String YOU_HAVE_BEEN_CHARGED = "[Portique] Vous avez été chargé de {%1$d} PO(s).";
	private final static String PORTIQUE_INVALID_ACCOUNT = "[Portique] Le compte a crediter est invalide.";
	private final static String PORTIQUE_INVALID_PRICE = "[Portique] Le prix est invalide.";
	
	public PortiqueManager()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenRailway.getInstance());
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.isCancelled())
			return;
		Block block = event.getClickedBlock();
		Sign params = isPortique(block);
		if(params == null) 
			return;
		
		String account = params.getLine(1);
		if(account.trim().equalsIgnoreCase("")) {
			ChatUtil.sendMessage(event.getPlayer(), PORTIQUE_INVALID_ACCOUNT);
			return;
		}
		if(params.getLine(2).trim().equalsIgnoreCase("")) {
			ChatUtil.sendMessage(event.getPlayer(), PORTIQUE_INVALID_PRICE);
			return;
		}
		int price = Math.abs(Integer.parseInt(params.getLine(2)));
		
		if(price >= 0) {
			ChatUtil.sendMessage(event.getPlayer(), YOU_HAVE_BEEN_CHARGED, price);
		}
		
		Portique fenceGateportique = new Portique(block.getLocation(), block.getType());
		fenceGateportique.openPortique(event.getPlayer().getLocation());
		
		event.setCancelled(true);			
	}
	
	public Sign isPortique(Block block) {
		if (block == null)
			return null;
		Location loc = block.getLocation();
		
		loc.setY(Math.floor(loc.getY()) - 2.0D); // Just below the portique block.
		if(loc.getBlock() == null)
			return null;
		if (loc.getBlock().getType() != Material.WALL_SIGN && loc.getBlock().getType() != Material.SIGN && loc.getBlock().getType() != Material.SIGN_POST)
			return null;
		BlockState paramBlck = loc.getBlock().getState();
		Sign paramsSign = (Sign) paramBlck;
		if(!paramsSign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[portique]"))
			return null;
		
		return paramsSign;
	}
	
	
	@EventHandler 
	public void onPlayerMove(PlayerMoveEvent event) { 
		  if (event.isCancelled()) 
			  return; 
		  
		  if(portiquesOuverts.isEmpty())
			  return;
		  
		  Location from = event.getFrom(); 
		  Location to = event.getTo(); 
		  
		  // Player quits the portique
		  Portique occupedPortique = portiquesOccupes.get(event.getPlayer().getName());
		  if(occupedPortique != null){
			  if(occupedPortique.isPortique(to))
				  return;
			  else {
				  portiquesOccupes.remove(event.getPlayer().getName());
				  occupedPortique.closePortique();
				  return;
			  }			  
		  }

		  for(Portique port: portiquesOuverts){
			  if(port.isPortique(to)) {
				  
				  Location pushBackTo = from;
				  
				  // Le portique est occupe, refuser la personne
				  if(portiquesOccupes.containsValue(port) && !portiquesOccupes.containsKey(event.getPlayer().getName()))
				  {
					  event.setTo(pushBackTo);
					  return;
				  }
				  
				  portiquesOccupes.put(event.getPlayer().getName(), port);
				  return;
			  }
			  
		  }

	}
	
}
