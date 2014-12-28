package fr.manu67100.heavenrp.laposte.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;

import fr.heavencraft.Utils;
import fr.heavencraft.heavenrp.HeavenRP;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.PlayerUtil;
import fr.manu67100.heavenrp.laposte.handlers.Colis;
import fr.manu67100.heavenrp.laposte.handlers.MenuItem;
import fr.manu67100.heavenrp.laposte.handlers.PopupMenu;
import fr.manu67100.heavenrp.laposte.handlers.PopupMenuAPI;
import fr.manu67100.heavenrp.laposte.handlers.PosteUtils;

public class PostOfficeSignListener implements Listener
{
	private final String _permission;
	private final String _tag;
	private final static String FORMAT_POSTE = "§4[§6La Poste§4] §6%1$s";

	public PostOfficeSignListener()
	{
		_permission = "LaPoste.admin";
		_tag = "[" + "Poste" + "]";
		Bukkit.getPluginManager().registerEvents(this, HeavenRP.getInstance());
	}

	@EventHandler(ignoreCancelled = true)
	private void onSignChange(SignChangeEvent event)
	{
		Player player = event.getPlayer();

		if (player == null || (!player.hasPermission(_permission) && player.isOp() == false)
				|| !event.getLine(0).equalsIgnoreCase(_tag))
			return;

		event.setLine(0, ChatColor.GREEN + _tag);
		ChatUtil.sendMessage(player, "Le panneau {%1$s} de poste a été placé correctement.", _tag);
		return;

	}

	@EventHandler(ignoreCancelled = false)
	public void PostSignClick(PlayerInteractEvent e)
	{

		if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		if (!Material.SIGN_POST.equals(e.getClickedBlock().getType())
				&& !Material.WALL_SIGN.equals(e.getClickedBlock().getType())
				&& !Material.SIGN.equals(e.getClickedBlock().getType()))
			return;

		Sign sign = (Sign) e.getClickedBlock().getState();

		if (!sign.getLine(0).equals(ChatColor.GREEN + _tag))
			return;

		ArrayList<Colis> mesColis = new ArrayList<Colis>();

		for (String id : PosteUtils.getColisRecus(PlayerUtil.getUUID(e.getPlayer())))
		{
			mesColis.add(new Colis(Integer.parseInt(id)));
		}

		PopupMenu menuMesColis = PopupMenuAPI.createMenu("Mes Colis Recus", 2);
		int index = 0;
		for (final Colis colis : mesColis)
		{
			MenuItem bouton = new MenuItem("Colis de " + colis.getExpediteur(), new MaterialData(Material.CHEST))
			{
				@Override
				public void onClick(Player player)
				{
					// Le joueur a t'il suffisament de place dans l'inventaire?
					if (Utils.getEmptySlots(player.getInventory()) >= colis.EmplacementsNecessaire())
					{
						colis.openColis(player);
						getMenu().closeMenu(player);
					}
					else
					{
						player.sendMessage(String.format(FORMAT_POSTE,
								"Vous n'avez pas assez de place dans votre inventaire."));
					}
				}
			};

			bouton.setDescriptions(Utils.wrapWords("Colis de: " + colis.getExpediteur(), 40));
			menuMesColis.addMenuItem(bouton, index);
			index++;
		}

		menuMesColis.setExitOnClickOutside(true);
		menuMesColis.switchMenu(e.getPlayer(), menuMesColis);
	}

	

}
