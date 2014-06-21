package fr.heavencraft.aventure.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.heavencraft.aventure.Files;
import fr.heavencraft.aventure.HeavenAventure;
import fr.heavencraft.aventure.WorldGuardRegions.RegionEnterEvent;
import fr.heavencraft.aventure.WorldGuardRegions.RegionLeaveEvent;
import fr.heavencraft.webserver.SoundEffectsManager;

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
			
			Bukkit.broadcastMessage("Evt onRgEnter: " + e.getPlayer().getName() +  " Rg : " + e.getRegion().getId());
//			e.getPlayer().playSound(e.getPlayer().getLocation(), getSound(e.getRegion().getId()), 1, 1);
			SoundEffectsManager.playToPlayer(e.getPlayer(), getSound(e.getRegion().getId()));

			String grt = ChatColor.translateAlternateColorCodes('&', getGreeting(e.getRegion().getId()));
			if( grt.length() != 0)
				e.getPlayer().sendMessage(grt);
		}



		//	  HeavenAventure.getInstance().getServer().dispatchCommand(e.getPlayer(), "playsound custom.fanfare " + e.getPlayer().getName());

	}


	public boolean getRegionEnabled(String regionName)
	{
		if(Files.getRegions().contains("Regions." + regionName.toLowerCase() + ".Enable"))
		{
			if( Files.getRegions().getBoolean("Regions." + regionName.toLowerCase() + ".Enable") == true)
				return true;
			else
				return false;
		}
		else
		{
			//creer la region automatiquement.
			Files.getRegions().set("Regions." + regionName.toLowerCase() + ".Enable", false);
			Files.getRegions().set("Regions." + regionName.toLowerCase() + ".Sound", "");

			String greeting = "&b~ " +  regionName + " ~";
			greeting = greeting.replace('_', ' ');
			Files.getRegions().set("Regions." + regionName.toLowerCase() + ".Greeting", greeting);
			Files.saveRegions();
			return false;
		}
	}

	public String getSound(String regionName)
	{
		return Files.getRegions().getString("Regions." + regionName.toLowerCase() + ".Sound");
	}

	public String getGreeting(String regionName)
	{
		return Files.getRegions().getString("Regions." + regionName.toLowerCase() + ".Greeting");
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