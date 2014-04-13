package fr.heavencraft.heavennexus.listeners.sign;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.heavencraft.heavennexus.Utils;
import fr.heavencraft.heavennexus.exceptions.HeavenException;

public abstract class SignListener {

	private final String _tag;
	private final String _permission;
	
	public SignListener(String tag, String permission, JavaPlugin plugin)
	{
		_tag = "[" + tag + "]";
		_permission = permission;

		Bukkit.getPluginManager().registerEvents(new InternalListener(), plugin);
	}

	protected abstract boolean onSignPlace(Player player, SignChangeEvent event);
	
	protected abstract void onSignClick(Player player, Sign sign) throws HeavenException;
	
	private class InternalListener implements Listener
	{
		@EventHandler(ignoreCancelled = true)
		private void onSignChange(SignChangeEvent event)
		{
			Player player = event.getPlayer();
			
			if (player == null || !player.hasPermission(_permission) || !event.getLine(0).equalsIgnoreCase(_tag))
				return;
			
			if (onSignPlace(player, event))
			{
				event.setLine(0, ChatColor.GREEN + _tag);
				Utils.sendMessage(player, "Le panneau {%1$s} a �t� pos� correctement.", _tag);
			}
			else
			{
				event.setCancelled(true);
				event.getBlock().breakNaturally();
			}
		}
		
		@EventHandler(ignoreCancelled = true)
		public void onPlayerInteract(PlayerInteractEvent event)
		{
			if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
				return;
		
			Block block = event.getClickedBlock();
			
			if (block.getType() != Material.SIGN_POST && block.getType() != Material.WALL_SIGN)
				return;
			
			Sign sign = (Sign) block.getState();
			
			if (!sign.getLine(0).equals(ChatColor.GREEN + _tag))
				return;
			
			Player player = event.getPlayer();
			
			try
			{
				onSignClick(player, sign);
			}
			catch (HeavenException ex)
			{
				Utils.sendMessage(player, ex.getMessage());
			}
		}
	}
}