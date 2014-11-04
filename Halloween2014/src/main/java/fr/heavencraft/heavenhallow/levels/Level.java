package fr.heavencraft.heavenhallow.levels;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum Level {
	
	LOBBY("Lobby", new Location(Bukkit.getWorld("halloween"), -285, 6, -1348)),
	THEBEGIN("La Tour Antique", new Location(Bukkit.getWorld("halloween"), -365, 147, -1038)),
	THEFOREST("Misty Forest", new Location(Bukkit.getWorld("halloween"), -382, 10, -1148)),
	THETIGER("Le Tigre Blanc", new Location(Bukkit.getWorld("halloween"), -308, 17, -1381)),
	THEMOON("La Lune", new Location(Bukkit.getWorld("halloween"), -377, 61, -1438)),
	THEFALL("L'effondrement", new Location(Bukkit.getWorld("halloween"), -368, 83, -1655));
	
	private final String stageName;
	private final Location spawnLoc;
	Level(String levelname, Location spawn)
	{
		this.stageName = levelname;
		this.spawnLoc = spawn;
	}
	public String getStageName() {
		return stageName;
	}
	public Location getSpawnLoc() {
		return spawnLoc;
	}
}
