package fr.heavencraft.NavalConflicts.commands.admin;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.NavalConflicts.NavalConflicts;
import fr.heavencraft.NavalConflicts.Utils;
import fr.heavencraft.NavalConflicts.Handlers.Arena;
import fr.heavencraft.NavalConflicts.Handlers.Lobby;
import fr.heavencraft.NavalConflicts.Handlers.LocationHandler;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayer;
import fr.heavencraft.NavalConflicts.Handlers.Player.NCPlayerManager;
import fr.heavencraft.NavalConflicts.Handlers.Player.Team;
import fr.heavencraft.NavalConflicts.Messages.Msgs;
import fr.heavencraft.NavalConflicts.commands.NavalCommand;
import fr.heavencraft.NavalConflicts.exceptions.NavalException;

public class ArenaCommands extends NavalCommand {
	public ArenaCommands() {
		super("arena");
	}

	private final static String FORMAT_NC = "§2[NC] §6%1$s";

	@Override
	protected void onPlayerCommand(Player player, String[] args)
			throws NavalException {
		NCPlayer ip = null;
		ip = NCPlayerManager.getNCPlayer(player);

		if (args.length == 0) {
			player.sendMessage(String.format(FORMAT_NC, "Aide: "));
			player.sendMessage(String.format(FORMAT_NC, "- arena list"));
			player.sendMessage(String.format(FORMAT_NC, "- arena create"));
			player.sendMessage(String.format(FORMAT_NC, "- arena edit"));
			player.sendMessage(String.format(FORMAT_NC, "- arena endedit"));
			player.sendMessage(String.format(FORMAT_NC,
					"- arena setspawn <red/blue>"));
			return;
		}

		// ////////////////////////////////////////////-LIST-//////////////////////////////////////////
		if (args[0].equalsIgnoreCase("list")) {
			player.sendMessage("");
			player.sendMessage("");
			for (Arena arena : Lobby.getArenas()) {
				player.sendMessage(String.format(FORMAT_NC, " - "
						+ ChatColor.GRAY + arena.getName()));
			}
		} 
		// ////////////////////////////////////////////-CREATE-//////////////////////////////////////////
		else if (args[0].equalsIgnoreCase("create")) {
			if (args.length != 3) {
				// Envoyer utilisation
				player.sendMessage(String.format(FORMAT_NC,
						"Creer une nouvelle arène a partir d'un monde"));
				player.sendMessage(String.format(FORMAT_NC,
						"/create [nom_arène]  [nom_world]."));
				return;
			} else {
				String arena = Utils.getWord(args[0]);
				if (Lobby.getArena(arena) != null)
					player.sendMessage(Msgs.Error_Arena_Already_Exists
							.getString());
				else {
					player.sendMessage("Preparation du monde: " + args[2]
							+ " pour arene: " + args[1]);
					Arena a = new Arena(arena);
					Lobby.addArena(a);

					NavalConflicts.Menus
					.destroyMenu(NavalConflicts.Menus.voteMenu);
					NavalConflicts.Menus.voteMenu = NavalConflicts.Menus
							.getVoteMenu();

					a.setCreator(player.getName());
					a.setName(args[1]);
					a.setWorldName(args[2]);

					ip.setCreating(arena);

					player.sendMessage(Msgs.Command_Arena_Created.getString(
							"<arena>", arena));
					Lobby.setEditedArena(a);
					player.teleport(a.getWorld().getSpawnLocation());
					return;
				}
			}
		}
		// ////////////////////////////////////////////-EDIT-//////////////////////////////////////////
		else if (args[0].equalsIgnoreCase("edit")) {
			
			if (Lobby.getEditedArena() != null)
			{
				player.sendMessage(String.format(FORMAT_NC, "Actuellement en edition: " + Lobby.getEditedArena().getName()));
				player.sendMessage(String.format(FORMAT_NC, "Quitter l'édition avec /arena endEdit"));
				return;
			}
			
			if (args.length != 2)
			{
				player.sendMessage(String .format(FORMAT_NC, "Editer une arène."));
				player.sendMessage(String.format(FORMAT_NC, "/edit [nom_arène]."));
				return;
			}
			else {
				Arena a = Lobby.getArena(args[1]);
				if (a != null && a.getInEdit() == false) {

					Lobby.setEditedArena(a);
					// passer l'arène en edition
					ip.setCreating(a.getName());
					player.teleport(a.getWorld().getSpawnLocation());
				} else if (a != null && a.getInEdit() == true) {
					//
				} else {
					player.sendMessage(String.format(FORMAT_NC, "Arène inconnue."));
					return;
				}
			}
		}
		// ////////////////////////////////////////////-ENDEDIT-//////////////////////////////////////////
		else if (args[0].equalsIgnoreCase("endedit")) {
			if (Lobby.getEditedArena() != null) {
				
				Lobby.getEditedArena().setInEdit(false);
				Lobby.getEditedArena().reset();
				Lobby.setEditedArena(null);
				ip.setCreating("");
				return;
				
			} else {
				player.sendMessage(String.format(FORMAT_NC,"Aucune arène en edition."));
			}

		}
		// ////////////////////////////////////////////-SPAWNS-//////////////////////////////////////////
		else if (args[0].equalsIgnoreCase("spawns"))
		{
			if (Lobby.getEditedArena() == null) {
				player.sendMessage(Msgs.Error_Arena_None_Set.getString());
			} else {
				Arena a = Lobby.getEditedArena();
				if (args.length == 2 && (args[1].equalsIgnoreCase("Global") || args[1].equalsIgnoreCase("Red") || args[1].equalsIgnoreCase("Blue")))
				{
					Team team = args[1].equalsIgnoreCase("Blue") ? Team.Blue : args[1].equalsIgnoreCase("Red") ? Team.Red : Team.Global;
					player.sendMessage(Msgs.Command_Spawn_Spawns.getString( "<team>", team.toString(), "<spawns>", String.valueOf(a.getExactSpawns(team).size())));
				} else {
					player.sendMessage(Msgs.Help_Spawns.getString());
				}
			}
		}
		// ////////////////////////////////////////////-SETSPAWN-//////////////////////////////////////////
		else if (args[0].equalsIgnoreCase("setspawn")) 
		{
			if (Lobby.getEditedArena() == null) {
				player.sendMessage(Msgs.Error_Arena_None_Set.getString());
			} else {
				if (args.length == 2 && (args[1].equalsIgnoreCase("Global") || args[1].equalsIgnoreCase("Red") || args[1].equalsIgnoreCase("Blue")))
				{
					Team team = args[1].equalsIgnoreCase("Blue") ? Team.Blue : args[1].equalsIgnoreCase("Red") ? Team.Red : Team.Global;

					Location l = player.getLocation();
					String s = LocationHandler.getLocationToString(l);
					Arena a = Lobby.getArena(ip.getCreating());
					List<String> list = a.getExactSpawns(team);
					list.add(s);
					a.setSpawns(list, team);

					NavalConflicts.Menus.destroyMenu(NavalConflicts.Menus.voteMenu);
					NavalConflicts.Menus.voteMenu = NavalConflicts.Menus.getVoteMenu();

					player.sendMessage(Msgs.Command_Spawn_Set.getString("<team>", team.toString(), "<spawn>", String.valueOf(list.size())));
				} else
					player.sendMessage(Msgs.Help_SetSpawn.getString());
			}			
		} 
		// ////////////////////////////////////////////-DELSPAWN-//////////////////////////////////////////
		else if (args[0].equalsIgnoreCase("delspawn")) {
			if (Lobby.getEditedArena() == null) {
				player.sendMessage(Msgs.Error_Arena_None_Set.getString());
			} 
			else
			{
				if (args.length == 2 && (args[1].equalsIgnoreCase("Global") || args[1].equalsIgnoreCase("Red") || args[1].equalsIgnoreCase("Blue")))
				{
					Team team = args[1].equalsIgnoreCase("Blue") ? Team.Blue  : args[1].equalsIgnoreCase("Red") ? Team.Red : Team.Global;

					Arena a = Lobby.getArena(ip.getCreating());
					int i = Integer.valueOf(args[1]) - 1;
					if (i < a.getSpawns(team).size())
					{
						List<String> spawns = a.getExactSpawns(team);
						spawns.remove(i);
						a.setSpawns(spawns, team);

						NavalConflicts.Menus.destroyMenu(NavalConflicts.Menus.voteMenu);
						NavalConflicts.Menus.voteMenu = NavalConflicts.Menus.getVoteMenu();

						player.sendMessage(Msgs.Command_Spawn_Deleted.getString("<team>", team.toString(), "<spawn>", String.valueOf(i + 1)));
					} else
						player.sendMessage(Msgs.Help_DelSpawn.getString());
				} else
					player.sendMessage(Msgs.Help_DelSpawn.getString());
			}
		}
	}



	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args)
			throws NavalException {
		Utils.sendMessage(sender,
				"Cette commande ne peut pas être utilisée depuis la {console}.");

	}

	@Override
	protected void sendUsage(CommandSender sender) {
	}
}
