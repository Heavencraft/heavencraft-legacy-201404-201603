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

import fr.heavencraft.Utils.BankAccountsManager;
import fr.heavencraft.Utils.BankAccountsManager.BankAccount;
import fr.heavencraft.Utils.ChatUtil;
import fr.heavencraft.Utils.User;
import fr.heavencraft.Utils.UserProvider;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.railways.HeavenRailway;

public class PortiqueManager implements Listener
{	
	
	public static List<Portique> portiquesOuverts = new ArrayList<Portique>();
	public static HashMap<String, Portique> portiquesOccupes = new HashMap<String, Portique>();
	private final static String YOU_HAVE_BEEN_CHARGED = "[Portique] Vous avez été chargé de {%1$d} PO(s).";
	private final static String PORTIQUE_INVALID_ACCOUNT = "[Portique] Le compte a crediter est invalide.";
	private final static String PORTIQUE_INVALID_ACCOUNT_FORMAT = "[Portique] Le compte a crediter est dans le mauvait format: <U/T/W>:<nom>.";
	private final static String PORTIQUE_INVALID_ACCOUNT_TYPE = "[Portique] Le type de compte a crediter est invalide: U (user) / T (town) / E (entreprise).";
	private final static String PORTIQUE_INVALID_PRICE = "[Portique] Le prix est invalide.";
	private final static String PORTIQUE_NO_MONEY = "[Portique] Vous n'avez pas assez d'argent.";
	private final static String PORTIQUE_UNKNOWN_USER = "[Portique] Erreur inconnue, vous ne semblez pas exiter...";
	
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
		
		String account[] = params.getLine(1).split(":");
		
		BankAccount creditedAccount = null;
		
		if(account.length != 2) {
			ChatUtil.sendMessage(event.getPlayer(), PORTIQUE_INVALID_ACCOUNT_FORMAT);
			return;
		} else {
			if(!account[0].equalsIgnoreCase("U") && !account[0].equalsIgnoreCase("T") && !account[0].equalsIgnoreCase("E")) {
				ChatUtil.sendMessage(event.getPlayer(), PORTIQUE_INVALID_ACCOUNT_TYPE);
				return;
			}
			try
			{
				creditedAccount = BankAccountsManager.getBankAccount(account[1], BankAccountsManager.BankAccountType.getByCode(account[0]));
			}
			catch (HeavenException e)
			{
				ChatUtil.sendMessage(event.getPlayer(), PORTIQUE_INVALID_ACCOUNT);
				return;
			}
		}
		
		if(creditedAccount == null) {
			ChatUtil.sendMessage(event.getPlayer(), PORTIQUE_INVALID_ACCOUNT);
			return;
		}
		
		//TODO verifier que l'argent est bien débité
		User sender = null;
		try
		{
			sender = UserProvider.getUserByName(event.getPlayer().getName());
		}
		catch (HeavenException e1)
		{
			e1.printStackTrace();
			ChatUtil.sendMessage(event.getPlayer(), PORTIQUE_UNKNOWN_USER);
			return;
		}
		if(sender == null)
		{
			ChatUtil.sendMessage(event.getPlayer(), PORTIQUE_UNKNOWN_USER);
			return;
		}
		
		if(params.getLine(2).trim().equalsIgnoreCase("")) {
			ChatUtil.sendMessage(event.getPlayer(), PORTIQUE_INVALID_PRICE);
			return;
		}
		
		int price = Math.abs(Integer.parseInt(params.getLine(2)));
		if(sender.getBalance() <= price)
		{
			ChatUtil.sendMessage(event.getPlayer(), PORTIQUE_NO_MONEY);
			return;
		}
		
		if(price >= 0) {
			ChatUtil.sendMessage(event.getPlayer(), YOU_HAVE_BEEN_CHARGED, price);
			try
			{
				sender.updateBalance(-price);
				creditedAccount.updateBalance(price);
			}
			catch (HeavenException e)
			{
				e.printStackTrace();
				ChatUtil.sendMessage(event.getPlayer(), PORTIQUE_INVALID_ACCOUNT);
				return;
			}
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
