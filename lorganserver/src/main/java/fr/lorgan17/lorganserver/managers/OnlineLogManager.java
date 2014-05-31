package fr.lorgan17.lorganserver.managers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.lorgan17.lorganserver.LorganServer;

public class OnlineLogManager {

	private final static int SERVER_ID = 2;
	
	public static void Update()
	{/*
		Player[] onlinePlayers = Bukkit.getOnlinePlayers();

		int nbPlayers = onlinePlayers.length;
		String players = "";
		
		for (Player player : onlinePlayers)
			players += (players == "" ? "" : " ") + player.getName();
		
		try
		{
			PreparedStatement ps = LorganServer.getMainConnection().prepareStatement("UPDATE onlinelog SET playercount = ?, playernames = ? WHERE id = ?");
			ps.setInt(1, nbPlayers);
			ps.setString(2, players);
			ps.setInt(3, SERVER_ID);
			
			ps.executeUpdate();
			ps.close();
		}
		
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}*/
	}
}
