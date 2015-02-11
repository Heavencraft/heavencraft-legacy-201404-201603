package fr.heavencraft.heavenrp.scoreboards;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ProvinceTeam
{
	private static final Map<Integer, ProvinceTeam> provincesById = new HashMap<Integer, ProvinceTeam>();

	public static final ProvinceTeam Zephir = new ProvinceTeam(1, "Zephir", "aqua");
	public static final ProvinceTeam Chansor = new ProvinceTeam(3, "Chansor", "dark_green");
	public static final ProvinceTeam Azur = new ProvinceTeam(4, "Azur", "red");
	public static final ProvinceTeam Heavenland = new ProvinceTeam(5, "Heavenland", "dark_blue");
	public static final ProvinceTeam Enkidiev = new ProvinceTeam(6, "Enkidiev", "gold");
	public static final ProvinceTeam Feador = new ProvinceTeam(7, "Feador", "dark_purple");

	public static Collection<ProvinceTeam> getAllTeams()
	{
		return provincesById.values();
	}

	public static ProvinceTeam getTeamById(int id)
	{
		return provincesById.get(id);
	}

	private final String name;
	private final String command;
	private Team team;

	private ProvinceTeam(int id, String name, String color)
	{
		this.name = name;
		this.command = String.format("scoreboard teams option %1$s color %2$s", name, color);
		provincesById.put(id, this);
	}

	public void register(Scoreboard scoreboard)
	{
		team = scoreboard.registerNewTeam(name);
		team.setNameTagVisibility(NameTagVisibility.ALWAYS);
	}

	public void addPlayer(OfflinePlayer player)
	{
		team.addPlayer(player);

		// TODO : check with Manu if this command is mandatory.
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
	}

	public void removePlayer(OfflinePlayer player)
	{
		team.removePlayer(player);
	}
}