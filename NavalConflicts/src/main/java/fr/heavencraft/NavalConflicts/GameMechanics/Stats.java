package fr.heavencraft.NavalConflicts.GameMechanics;

import fr.heavencraft.NavalConflicts.Tools.Files;

public class Stats {

	public enum StatType
	{
		kills, deaths, points, score, killstreak, time;
	};

	/**
	 * From a StatType get the value for the player
	 * 
	 * @param type
	 *            - The StatType
	 * @param user
	 *            - The player
	 * @return the value
	 */
	public static int getStat(StatType type, String user) {
		if (type == StatType.kills)
			return getKills(user);
		else if (type == StatType.deaths)
			return getDeaths(user);
		else if (type == StatType.points)
			return getPoints(user);
		else if (type == StatType.score)
			return getScore(user);
		else if (type == StatType.killstreak)
			return getHighestKillStreak(user);
		else if (type == StatType.time)
			return getPlayingTime(user);
		else
			return 0;
	}

	/**
	 * Checks if we're setting MySQL or Player.yml
	 * 
	 * @param name
	 * @return HighestKillStreak
	 */
	public static int getHighestKillStreak(String name) {
		name = name.toLowerCase();
		return Files.getPlayers().getInt("Players." + name + ".HighestKillStreak");
	}

	/**
	 * Checks if we're setting MySQL or Player.yml
	 * 
	 * @param name
	 * @param highestKillStreak
	 */

	public static void setHighestKillStreak(String name, Integer highestKillStreak) {
		name = name.toLowerCase();
		Files.getPlayers().set("Players." + name + ".HighestKillStreak", highestKillStreak);
		Files.savePlayers();
		
	}

	/**
	 * Checks if we're setting MySQL or Player.yml
	 * 
	 * @param name
	 * @return PlayingTime
	 */
	public static int getPlayingTime(String name) {
		name = name.toLowerCase();
		return Files.getPlayers().getInt("Players." + name + ".PlayingTime");
	}

	/**
	 * Checks if we're setting MySQL or Player.yml
	 * 
	 * @param name
	 * @param PlayingTime
	 */

	public static void setPlayingTime(String name, long l) {
		name = name.toLowerCase();
		Files.getPlayers().set("Players." + name + ".PlayingTime", l);
		Files.savePlayers();
		
	}

	/**
	 * Checks if we're setting MySQL or Player.yml
	 * 
	 * @param name
	 * @return Kills
	 */
	public static int getKills(String name) {
		name = name.toLowerCase();
		return Files.getPlayers().getInt("Players." + name + ".Kills");
	}

	/**
	 * Checks if we're setting MySQL or Player.yml
	 * 
	 * @param name
	 * @param kills
	 */

	public static void setKills(String name, Integer kills) {
		name = name.toLowerCase();
		Files.getPlayers().set("Players." + name + ".Kills", kills);
		Files.savePlayers();
		
	}

	// Get the deaths from the location required
	public static int getDeaths(String name) {
		name = name.toLowerCase();
		return Files.getPlayers().getInt("Players." + name + ".Deaths");
	}

	/**
	 * Checks if we're setting MySQL or Player.yml
	 * 
	 * @param name
	 * @param deaths
	 */

	public static void setDeaths(String name, Integer deaths) {
		name = name.toLowerCase();
		Files.getPlayers().set("Players." + name + ".Deaths", deaths);
		Files.savePlayers();
		
	}

	/**
	 * Checks if we're going MySQL or Player.yml
	 * 
	 * @param name
	 * @return the players Score
	 */
	public static int getScore(String name) {
		name = name.toLowerCase();
		return Files.getPlayers().getInt("Players." + name + ".Score");
	}

	/**
	 * Checks if we're setting MySQL or Player.yml
	 * 
	 * @param name
	 * @param score
	 */
	public static void setScore(String name, Integer score) {
		name = name.toLowerCase();
		Files.getPlayers().set("Players." + name + ".Score", score);
		Files.savePlayers();
	
	}

	/**
	 * Checks if we're going MySQL or Player.yml
	 * 
	 * @param name
	 * @return the players Score
	 */
	public static int getPoints(String name) {
		name = name.toLowerCase();
		return Files.getPlayers().getInt("Players." + name + ".Points");
	}

	/**
	 * Checks if we're setting MySQL or Player.yml
	 * 
	 * @param name
	 * @param points
	 */
	public static void setPoints(String name, Integer points) {
		name = name.toLowerCase();
		Files.getPlayers().set("Players." + name + ".Points", points);
		Files.savePlayers();
		
	}

//	/**
//	 * 
//	 * Gets the value of the stat for the player's name
//	 * 
//	 * @param name
//	 * @param stat
//	 * @return value
//	 */
//	private static Integer getMySQLStats(String name, String stat) {
//		name = name.toLowerCase();
//		return MySQLManager.getInt("Infected", stat, name);
//	}

//	/**
//	 * Sets the value of the stat to the player's name
//	 * 
//	 * @param name
//	 * @param stat
//	 * @param value
//	 */
//	private static void setMySQLStats(String name, String stat, int value) {
//		name = name.toLowerCase();
//		MySQLManager.update("Infected", stat, value, name);
//	}
	
}
