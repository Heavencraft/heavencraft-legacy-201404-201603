package fr.heavencraft.heavengame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class HeavenGame extends JavaPlugin
{
	public enum ServerType
	{
		INFECTED("Infected"),
		TNT_RUN("TNT Run"),
		MARIO_KART("Mario Kart"),
		HUNGER_GAMES("Hunger Games"),
		DEFAULT("Heavencraft");

		private final String _name;

		private ServerType(String name)
		{
			_name = name;
		}

		public String getName()
		{
			return _name;
		}
	}

	private static ServerType _serverType;

	@Override
	public void onEnable()
	{
		super.onEnable();

		if (Bukkit.getPluginManager().getPlugin("Infected") != null)
			_serverType = ServerType.INFECTED;
		else if (Bukkit.getPluginManager().getPlugin("TNTRun") != null)
			_serverType = ServerType.TNT_RUN;
		else if (Bukkit.getPluginManager().getPlugin("MarioKart") != null)
			_serverType = ServerType.MARIO_KART;
		else if (Bukkit.getPluginManager().getPlugin("SurvivalGames") != null)
			_serverType = ServerType.HUNGER_GAMES;
		else
			_serverType = ServerType.DEFAULT;

		new ServerListener(this);
	}

	public static Location getSpawn()
	{
		return new Location(Bukkit.getWorld("world"), 0.5, 4, 0.5);
	}

	public static ServerType getServerType()
	{
		return _serverType;
	}
}