package fr.heavencraft.aventure.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.heavencraft.aventure.Files;
import fr.heavencraft.aventure.HeavenAventure;
import fr.heavencraft.aventure.WorldGuardRegions.RegionEnterEvent;
import fr.heavencraft.aventure.WorldGuardRegions.RegionLeaveEvent;

public class PlayerListener implements Listener{
	public PlayerListener()
	{
		Bukkit.getPluginManager().registerEvents(this, HeavenAventure.getInstance());
	}

	@EventHandler
	public void onRegionEnter(RegionEnterEvent e)
	{

		if(getRegionEnabled(e.getRegion().getId())== true)
		{	

			e.getPlayer().playSound(e.getPlayer().getLocation(), getSound(e.getRegion().getId()), 1, 1);
			e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', getGreeting(e.getRegion().getId())));
		}



		//	  HeavenAventure.getInstance().getServer().dispatchCommand(e.getPlayer(), "playsound custom.fanfare " + e.getPlayer().getName());

	}


	public boolean getRegionEnabled(String regionName)
	{
		if(Files.getRegions().contains("Regions." + regionName + ".Enable"))
		{
			if( Files.getRegions().getBoolean("Regions." + regionName + ".Enable") == true)
				return true;
			else
				return false;
		}
		else
		{
			//creer la region automatiquement.
			Files.getRegions().set("Regions." + regionName + ".Enable", false);
			Files.getRegions().set("Regions." + regionName + ".Sound", "");
			
			String greeting = "&b~ " +  regionName + " ~";
			greeting = greeting.replace('_', ' ');
			Files.getRegions().set("Regions." + regionName + ".Greeting", greeting);
			Files.saveRegions();
			return false;
		}
	}

	public String getSound(String regionName)
	{
		return Files.getRegions().getString("Regions." + regionName + ".Sound");
	}

	public String getGreeting(String regionName)
	{
		return Files.getRegions().getString("Regions." + regionName + ".Greeting");
	}

		@EventHandler
		public void onRegionLeave(RegionLeaveEvent e)
		{
			
	//	  if (e.getRegion().getId().equals("jail") && e.isCancellable()) // you cannot cancel the event if the player left the region because he died
	//	  {
	//	    e.setCancelled(true);
	//	    e.getPlayer().sendMessage("You cannot leave the jail!");
	//	  }
		}
}
