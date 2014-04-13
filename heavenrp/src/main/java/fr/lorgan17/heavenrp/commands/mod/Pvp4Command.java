package fr.lorgan17.heavenrp.commands.mod;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.lorgan17.heavenrp.listeners.PVP4Manager;

public class Pvp4Command extends HeavenCommand
{
	public Pvp4Command()
	{
		super("pvp4", "heavenrp.moderator.pvp4");
	}
	
	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length == 0)
		{
			sendUsage(player);
			return;
		}
		
		if (args[0].equalsIgnoreCase("start"))
		{
			List<Player> players = new ArrayList<Player>();
			
			for (int i = 1; i != args.length; i++)
				players.add(Utils.getPlayer(args[i]));
			
			PVP4Manager.startBattle(players);
		}
		else if (args[0].equalsIgnoreCase("startwe"))
		{
			Selection selection = Utils.getWESelection(player);
			List<Player> players = new ArrayList<Player>();
			
			for (Player player2 : Bukkit.getOnlinePlayers())
				if (selection.contains(player2.getLocation()))
					players.add(player2);

			PVP4Manager.startBattle(players);
		}
		else if (args[0].equalsIgnoreCase("stop"))
		{
			PVP4Manager.stopBattle();
		}
		else if (args[0].equalsIgnoreCase("addspawn"))
		{
			PVP4Manager.addSpawn(player.getLocation());
			Utils.sendMessage(player, "Le point de spawn a été ajouté.");
		}
		else if (args[0].equalsIgnoreCase("resetspawn"))
		{
			PVP4Manager.resetSpawns();
			Utils.sendMessage(player, "Les points de spawn ont été supprimés.");
		}
	}
	
	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Utils.sendMessage(sender, "{/pvp4} start <joueur1> <joueur2> etc. : démarre le combat");
		Utils.sendMessage(sender, "{/pvp4} startwe : démarre le combat avec les joueurs présent dans la sélection");
		Utils.sendMessage(sender, "{/pvp4} addspawn : ajoute un point de spawn");
		Utils.sendMessage(sender, "{/pvp4} resetspawn : retire tous les points de spawn");
		Utils.sendMessage(sender, "{/pvp4} stop");
	}
}