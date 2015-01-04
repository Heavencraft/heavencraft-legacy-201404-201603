package fr.heavencraft.heavenrp.scoreboards;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.general.users.UserProvider;
import fr.heavencraft.heavenrp.provinces.ProvincesManager;
import fr.heavencraft.heavenrp.provinces.ProvincesManager.Province;
import fr.heavencraft.utils.ChatUtil;

public class ProvinceScoreboards {
	private static ScoreboardManager manager = Bukkit.getScoreboardManager();
	private static Scoreboard board = manager.getMainScoreboard();


	// Liste des couleurs par provinces:
	/*
	 * Heavenland: Bleu Foncé (Bleu modo)
	 * Zephir: Bleu Ciel
	 * Azur: Rouge
	 * Chansor: Vert (Vert Comm Manager)
	 * Feador: Violet
	 * Enkidiev: Orange
	 * 
	 */


	public ProvinceScoreboards() {
		// Verifier si l'équipe existe déjà:
		if(!teamInCollection("Heavenland"))
			ProvinceScoreboards.board.registerNewTeam("Heavenland");
		if(!teamInCollection("Zephir"))
			ProvinceScoreboards.board.registerNewTeam("Zephir");
		if(!teamInCollection("Azur"))
			ProvinceScoreboards.board.registerNewTeam("Azur");
		if(!teamInCollection("Chansor"))
			ProvinceScoreboards.board.registerNewTeam("Chansor");
		if(!teamInCollection("Feador"))
			ProvinceScoreboards.board.registerNewTeam("Feador");
		if(!teamInCollection("Enkidiev"))
			ProvinceScoreboards.board.registerNewTeam("Enkidiev");

		// Apply Scoreboards visibility
		for(Team t : board.getTeams())
			if(t.getName() == "Heavenland" || t.getName() == "Zephir" || t.getName() == "Azur" 
			|| t.getName() == "Chansor" || t.getName() == "Feador" || t.getName() == "Enkidiev")
				t.setNameTagVisibility(NameTagVisibility.ALWAYS);	

	}

	private boolean teamInCollection(String teamName)
	{
		for(Team t : board.getTeams())
		{
			if(t.getName().equalsIgnoreCase(teamName))
				return true;
			ChatUtil.broadcastMessage(t.getName() + " == ? " + teamName + " res: " + (t.getName() == teamName));
		}

		return false;
	}

	public static Set<Team> getTeams(){
		return ProvinceScoreboards.board.getTeams();
	}

	public static Team getTeam(String name)
	{
		for(Team team: ProvinceScoreboards.getTeams())
			if(team.getName().equalsIgnoreCase(name))
				return team;
		return null;
	}

	public static void applyTeamColor(Player p) {
		Province prov = null;
		try {
			prov = ProvincesManager.getProvinceByUser(UserProvider.getUserByName(p.getName()).getId());
		} catch (HeavenException e) {
			e.printStackTrace();
			return;
		}

		// Verifier si le joueur est présent dans une équipe, et le retirer.
		for(Team t : getTeams())
			if(t.getName() == "Heavenland" || t.getName() == "Zephir" || t.getName() == "Azur" 
			|| t.getName() == "Chansor" || t.getName() == "Feador" || t.getName() == "Enkidiev")
				for(OfflinePlayer ofp: t.getPlayers())
					if(ofp.getName() == p.getName())
						t.removePlayer(ofp);

		// If null, do nothing. --> player does not have any province
		if(prov == null)
		{

			return;
		} else {

			switch(prov.getId()){
			case 1:
				// Zephir
				getTeam("Zephir").addPlayer(Bukkit.getOfflinePlayer(p.getName()));	
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard teams option Zephir color aqua");
				break;
			case 3:
				// Chansor
				getTeam("Chansor").addPlayer(Bukkit.getOfflinePlayer(p.getName()));	
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard teams option Chansor color dark_green");
				break;
			case 4:
				// Azur
				getTeam("Azur").addPlayer(Bukkit.getOfflinePlayer(p.getName()));	
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard teams option Azur color red");
				break;
			case 5:
				// Heavenland
				getTeam("Heavenland").addPlayer(Bukkit.getOfflinePlayer(p.getName()));	
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard teams option Heavenland color dark_blue");
				break;
			case 6:
				// Enkidiev
				getTeam("Enkidiev").addPlayer(Bukkit.getOfflinePlayer(p.getName()));
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard teams option Enkidiev color gold");
				break;
			case 7:
				// Feador
				getTeam("Feador").addPlayer(Bukkit.getOfflinePlayer(p.getName()));	
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "scoreboard teams option Feador color dark_purple");
				break;
			default:
				break;
			}
		}
	}



}
