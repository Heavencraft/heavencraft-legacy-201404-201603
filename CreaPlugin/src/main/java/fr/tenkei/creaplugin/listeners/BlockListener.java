package fr.tenkei.creaplugin.listeners;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.SignChangeEvent;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.managers.ManagersManager;

public class BlockListener implements Listener {

	ManagersManager _managers;
	
	public BlockListener(MyPlugin plugin){	
		Bukkit.getPluginManager().registerEvents(this, plugin);
		_managers = plugin.getManagers();
	}
	
	private String getMsgColor(String message)
    {
            if (message.contains("&"))
            {
                    Matcher matcher = Pattern.compile("\\&([0-9A-Ja-j])").matcher(message);
                    message = matcher.replaceAll("§$1");
            }
            return message;
    }
	
	@EventHandler(ignoreCancelled = true)
	public void onSignChange(SignChangeEvent event)
	{
		  String s1 = event.getLine(0);
          String s2 = event.getLine(1);
          String s3 = event.getLine(2);
          String s4 = event.getLine(3);
           
          Player p = event.getPlayer();
          
          if (p.hasPermission(MyPlugin.builder) || p.hasPermission(MyPlugin.archiModo)){ // Tout le monde en gros
                   event.setLine(0, getMsgColor(s1));
                   event.setLine(1, getMsgColor(s2));
                   event.setLine(2, getMsgColor(s3));
                   event.setLine(3, getMsgColor(s4));
           }
          
  		if (s1.equalsIgnoreCase("[AV]"))
  			_managers.getAVManager().aVendre(event);
	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockPhysics(BlockPhysicsEvent event)
	{
		
		if (event.getChangedType().equals(Material.PORTAL))
		{
			event.setCancelled(true);
		}
	}
	
	/*@EventHandler(ignoreCancelled = true)
	private void onBlockPistonExtend(BlockPistonExtendEvent event)
	{
		for (Block block : event.getBlocks())
			if (block.getType() == Material.SKULL_ITEM)
			{
				event.setCancelled(true);
				return;
			}
	}

	@EventHandler(ignoreCancelled = true)
	private void onBlockPistonRetract(BlockPistonRetractEvent event)
	{
			if (event.getBlock().getType() == Material.SKULL_ITEM)
			{
				event.setCancelled(true);
				return;
			}
	 }*/
}
