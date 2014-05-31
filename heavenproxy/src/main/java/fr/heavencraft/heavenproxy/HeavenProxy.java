package fr.heavencraft.heavenproxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import fr.heavencraft.heavenproxy.ban.BanCommand;
import fr.heavencraft.heavenproxy.ban.BanListener;
import fr.heavencraft.heavenproxy.ban.UnbanCommand;
import fr.heavencraft.heavenproxy.chat.ChatListener;
import fr.heavencraft.heavenproxy.chat.FloodListener;
import fr.heavencraft.heavenproxy.chat.TabCompleteListener;
import fr.heavencraft.heavenproxy.commands.ActifCommand;
import fr.heavencraft.heavenproxy.commands.ListCommand;
import fr.heavencraft.heavenproxy.commands.MeCommand;
import fr.heavencraft.heavenproxy.commands.ModoCommand;
import fr.heavencraft.heavenproxy.commands.NexusCommand;
import fr.heavencraft.heavenproxy.commands.ReplyCommand;
import fr.heavencraft.heavenproxy.commands.SayCommand;
import fr.heavencraft.heavenproxy.commands.SendCommand;
import fr.heavencraft.heavenproxy.commands.SpyCommand;
import fr.heavencraft.heavenproxy.commands.TellCommand;
import fr.heavencraft.heavenproxy.commands.VoterCommand;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.kick.KickCommand;
import fr.heavencraft.heavenproxy.kick.RagequitCommand;
import fr.heavencraft.heavenproxy.listeners.LogListener;
import fr.heavencraft.heavenproxy.listeners.OnlineLogListener;
import fr.heavencraft.heavenproxy.listeners.SpyListener;
import fr.heavencraft.heavenproxy.managers.RequestsManager;
import fr.heavencraft.heavenproxy.motd.ProxyPingListener;
import fr.heavencraft.heavenproxy.mute.MuteCommand;
import fr.heavencraft.heavenproxy.mute.MuteListener;
import fr.heavencraft.heavenproxy.users.TabListener;
import fr.heavencraft.heavenproxy.users.UsersListener;
import fr.heavencraft.heavenproxy.warn.WarnCommand;

public class HeavenProxy extends Plugin
{
	private final static String DB_URL = "jdbc:mysql://localhost:3306/proxy?user=mc-sql&password=MfGJQMBzmAS5xYhH&zeroDateTimeBehavior=convertToNull";
	private final static String MAIN_DB_URL = "jdbc:mysql://localhost:3306/mc-db?user=mc-sql&password=MfGJQMBzmAS5xYhH&zeroDateTimeBehavior=convertToNull";
	private final static String SRP_DB_URL = "jdbc:mysql://localhost:3306/minecraft-semirp?user=mc-sql&password=MfGJQMBzmAS5xYhH&zeroDateTimeBehavior=convertToNull";

	private static Connection _connection;
	private static Connection _mainConnection;
	private static Connection _srpConnection;

	private static HeavenProxy _instance;

	private RequestsManager _requestsManager;

	@Override
	public void onEnable()
	{
		try
		{
			super.onEnable();

			_instance = this;

			new LogListener();
			new OnlineLogListener();
			new SpyListener();

			new ActifCommand();
			new ListCommand();
			new MeCommand();
			new ModoCommand();
			new NexusCommand();
			new ReplyCommand();
			new SayCommand();
			new SendCommand();
			new SpyCommand();
			new TellCommand();
			new VoterCommand();

			// Ban
			new BanCommand();
			new BanListener();
			new UnbanCommand();

			// Chat
			new ChatListener();
			new FloodListener();
			new TabCompleteListener();

			// Kick
			new KickCommand();
			new RagequitCommand();

			// MOTD
			new ProxyPingListener();

			// Mute
			new MuteCommand();
			new MuteListener();

			// Users
			new TabListener();
			new UsersListener();

			// Warn
			new WarnCommand();

			new AutoMessageTask();
			new MemoryWatcherTask();

			_requestsManager = new RequestsManager();

			convertBanList();
			convertUserList();
			convertUserSemirp();
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

	public static Connection getSrpConnection()
	{
		try
		{
			if (_srpConnection == null || _srpConnection.isClosed())
			{
				_srpConnection = DriverManager.getConnection(SRP_DB_URL);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			ProxyServer.getInstance().stop();
		}

		return _srpConnection;
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
				try
				{
					String name = rs.getString("name");
					String uuid = Utils.getUUID(name);

					PreparedStatement ps2 = getConnection().prepareStatement(
							"UPDATE banlist SET uuid = ? WHERE name = ?");
					ps2.setString(1, uuid);
					ps2.setString(2, name);

					ps2.executeUpdate();
					System.out.println(name + " -> " + uuid);
				}
				catch (HeavenException ex)
				{
					System.out.println(ex.getMessage());
				}
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
		new Thread()
		{

			@Override
			public void run()
			{
				try
				{
					PreparedStatement ps = getConnection().prepareStatement("SELECT name FROM users WHERE uuid = ''");
					ResultSet rs = ps.executeQuery();

					while (rs.next())
					{
						try
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
						catch (HeavenException ex)
						{
							System.out.println(ex.getMessage());
						}
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

	public static void convertUserSemirp()
	{
		new Thread()
		{

			@Override
			public void run()
			{
				try
				{
					PreparedStatement ps = getSrpConnection()
							.prepareStatement("SELECT name FROM users WHERE uuid = ''");
					ResultSet rs = ps.executeQuery();

					while (rs.next())
					{
						try
						{
							String name = rs.getString("name");
							String uuid = Utils.getUUID(name);

							PreparedStatement ps2 = getSrpConnection().prepareStatement(
									"UPDATE users SET uuid = ? WHERE name = ?");
							ps2.setString(1, uuid);
							ps2.setString(2, name);

							ps2.executeUpdate();
							System.out.println(name + " -> " + uuid);
						}
						catch (HeavenException ex)
						{
							System.out.println(ex.getMessage());
						}
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