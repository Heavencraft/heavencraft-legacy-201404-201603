package fr.heavencraft.heavenproxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import fr.heavencraft.heavenproxy.ban.BanCommand;
import fr.heavencraft.heavenproxy.ban.UnbanCommand;
import fr.heavencraft.heavenproxy.commands.ActifCommand;
import fr.heavencraft.heavenproxy.commands.KickCommand;
import fr.heavencraft.heavenproxy.commands.ListCommand;
import fr.heavencraft.heavenproxy.commands.MeCommand;
import fr.heavencraft.heavenproxy.commands.ModoCommand;
import fr.heavencraft.heavenproxy.commands.NexusCommand;
import fr.heavencraft.heavenproxy.commands.RagequitCommand;
import fr.heavencraft.heavenproxy.commands.SayCommand;
import fr.heavencraft.heavenproxy.commands.SendCommand;
import fr.heavencraft.heavenproxy.commands.SpyCommand;
import fr.heavencraft.heavenproxy.commands.TellCommand;
import fr.heavencraft.heavenproxy.listeners.ChatListener;
import fr.heavencraft.heavenproxy.listeners.FloodListener;
import fr.heavencraft.heavenproxy.listeners.LogListener;
import fr.heavencraft.heavenproxy.listeners.NexusListener;
import fr.heavencraft.heavenproxy.listeners.OnlineLogListener;
import fr.heavencraft.heavenproxy.listeners.SpyListener;
import fr.heavencraft.heavenproxy.listeners.TabListener;
import fr.heavencraft.heavenproxy.managers.RequestsManager;
import fr.heavencraft.heavenproxy.mute.MuteCommand;

public class HeavenProxy extends Plugin
{
	private final static String DB_URL = "jdbc:mysql://localhost:3306/proxy?user=mc-sql&password=MfGJQMBzmAS5xYhH&zeroDateTimeBehavior=convertToNull";
	private final static String MAIN_DB_URL = "jdbc:mysql://localhost:3306/mc-db?user=mc-sql&password=MfGJQMBzmAS5xYhH&zeroDateTimeBehavior=convertToNull";

	private static Connection _connection;
	private static Connection _mainConnection;

	private static HeavenProxy _instance;

	private RequestsManager _requestsManager;

	@Override
	public void onEnable()
	{
		try
		{
			super.onEnable();

			_instance = this;

			new ChatListener(this);
			new FloodListener(this);
			new LogListener(this);
			new NexusListener(this);
			new OnlineLogListener(this);
			new TabListener();
			new SpyListener();

			new ActifCommand();
			new BanCommand();
			new KickCommand();
			new ListCommand();
			new MeCommand();
			new ModoCommand();
			new NexusCommand();
			new RagequitCommand();
			new SayCommand();
			new SendCommand();
			new SpyCommand();
			new TellCommand();
			new UnbanCommand();

			new MuteCommand();

			new AutoMessageTask();

			_requestsManager = new RequestsManager();

			convertBanList();
			convertUserList();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			ProxyServer.getInstance().stop();
		}
	}

	@Override
	public void onDisable()
	{
		super.onDisable();

		getProxy().getScheduler().cancel(this);
	}

	public RequestsManager getRequestsManager()
	{
		return _requestsManager;
	}

	public static Connection getConnection()
	{
		try
		{
			if (_connection == null || _connection.isClosed())
			{
				_connection = DriverManager.getConnection(DB_URL);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			ProxyServer.getInstance().stop();
		}

		return _connection;
	}

	public static Connection getMainConnection()
	{
		try
		{
			if (_mainConnection == null || _mainConnection.isClosed())
			{
				_mainConnection = DriverManager.getConnection(MAIN_DB_URL);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			ProxyServer.getInstance().stop();
		}

		return _mainConnection;
	}

	public static HeavenProxy getInstance()
	{
		return _instance;
	}

	public static void convertBanList()
	{
		try
		{
			PreparedStatement ps = getConnection().prepareStatement("SELECT name FROM banlist WHERE uuid = ''");
			ResultSet rs = ps.executeQuery();

			while (rs.next())
			{
				String name = rs.getString("name");
				String uuid = Utils.getUUID(name);

				PreparedStatement ps2 = getConnection().prepareStatement("UPDATE banlist SET uuid = ? WHERE name = ?");
				ps2.setString(1, uuid);
				ps2.setString(2, name);

				ps2.executeUpdate();
				System.out.println(name + " -> " + uuid);
			}

		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void convertUserList()
	{
		new Thread() {

			@Override
			public void run()
			{
				try
				{
					PreparedStatement ps = getConnection().prepareStatement("SELECT name FROM users WHERE uuid = ''");
					ResultSet rs = ps.executeQuery();

					while (rs.next())
					{
						String name = rs.getString("name");
						String uuid = Utils.getUUID(name);

						PreparedStatement ps2 = getConnection().prepareStatement(
								"UPDATE users SET uuid = ? WHERE name = ?");
						ps2.setString(1, uuid);
						ps2.setString(2, name);

						ps2.executeUpdate();
						System.out.println(name + " -> " + uuid);
					}

				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}.start();
	}
}