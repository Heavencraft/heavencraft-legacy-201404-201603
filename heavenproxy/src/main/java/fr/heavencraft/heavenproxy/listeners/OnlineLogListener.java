package fr.heavencraft.heavenproxy.listeners;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map.Entry;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import fr.heavencraft.heavenproxy.HeavenProxy;
import fr.heavencraft.heavenproxy.Utils;

public class OnlineLogListener implements Listener
{
	private final static String NEXUS = "nexus";
	private final static String SRP = "semirp";
	private final static String ORIGINES = "origines";
	private final static String CREAFUN = "creafun";
	private final static String FACTIONS = "factions";
	private final static String ULTRAHARD = "ultrahard";

	// Jeux
	private final static String INFECTED = "infected";
	private final static String MARIOKART = "mariokart";
	private final static String TNTRUN = "tntrun";
	private final static String BACKBONE = "backbone";
	private final static String PAINTBALL = "paintball";

	private final static int NEXUS_ID = 0;
	private final static int SRP_ID = 1;
	private final static int ORIGINES_ID = 2;
	private final static int CREAFUN_ID = 3;
	private final static int FACTIONS_ID = 4;
	private final static int GAMES_ID = 5;
	private final static int ULTRAHARD_ID = 6;

	public OnlineLogListener()
	{
		Utils.registerListener(this);
	}

	@EventHandler
	public void onPostLogin(PostLoginEvent event)
	{
		Update();
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent event)
	{
		Update();
	}

	private void Update()
	{
		String players;
		int nbPlayers;

		for (Entry<String, ServerInfo> server : ProxyServer.getInstance().getServers().entrySet())
		{
			players = "";
			nbPlayers = server.getValue().getPlayers().size();

			for (ProxiedPlayer player : server.getValue().getPlayers())
				players += (players == "" ? "" : " ") + player.getName();

			UpdateDatabase(getIdFromName(server.getKey()), players, nbPlayers);
		}
	}

	private static int getIdFromName(String name)
	{
		if (name.equals(NEXUS))
			return NEXUS_ID;
		else if (name.equals(SRP))
			return SRP_ID;
		else if (name.equals(ORIGINES))
			return ORIGINES_ID;
		else if (name.equals(CREAFUN))
			return CREAFUN_ID;
		else if (name.equals(FACTIONS))
			return FACTIONS_ID;
		else if (name.equals(ULTRAHARD))
			return ULTRAHARD_ID;
		else if (name.equals(INFECTED))
			return GAMES_ID;
		else if (name.equals(MARIOKART))
			return GAMES_ID;
		else if (name.equals(TNTRUN))
			return GAMES_ID;
		else if (name.equals(BACKBONE))
			return GAMES_ID;
		else if (name.equals(PAINTBALL))
			return GAMES_ID;
		else
		{
			// System.out.println("Unknown server : " + name);
			return NEXUS_ID;
		}
	}

	private static void UpdateDatabase(int serverId, String players, int nbPlayers)
	{
		try
		{
			PreparedStatement ps = HeavenProxy.getMainConnection().prepareStatement(
					"UPDATE onlinelog SET playercount = ?, playernames = ? WHERE id = ?");
			ps.setInt(1, nbPlayers);
			ps.setString(2, players);
			ps.setInt(3, serverId);

			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}