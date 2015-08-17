package fr.heavencraft.heavenproxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import com.mojang.api.profiles.HttpProfileRepository;
import com.mojang.api.profiles.Profile;
import com.mojang.api.profiles.ProfileRepository;

import fr.heavencraft.heavenproxy.ban.BanCommand;
import fr.heavencraft.heavenproxy.ban.BanListener;
import fr.heavencraft.heavenproxy.ban.SilentBanListener;
import fr.heavencraft.heavenproxy.ban.UnbanCommand;
import fr.heavencraft.heavenproxy.chat.ChatListener;
import fr.heavencraft.heavenproxy.chat.FloodListener;
import fr.heavencraft.heavenproxy.chat.ModoListener;
import fr.heavencraft.heavenproxy.chat.TabCompleteListener;
import fr.heavencraft.heavenproxy.commands.ActifCommand;
import fr.heavencraft.heavenproxy.commands.ListCommand;
import fr.heavencraft.heavenproxy.commands.MeCommand;
import fr.heavencraft.heavenproxy.commands.ModoCommand;
import fr.heavencraft.heavenproxy.commands.NexusCommand;
import fr.heavencraft.heavenproxy.commands.OuestCommand;
import fr.heavencraft.heavenproxy.commands.ReplyCommand;
import fr.heavencraft.heavenproxy.commands.SayCommand;
import fr.heavencraft.heavenproxy.commands.SendCommand;
import fr.heavencraft.heavenproxy.commands.SpyCommand;
import fr.heavencraft.heavenproxy.commands.TellCommand;
import fr.heavencraft.heavenproxy.commands.TextCommand;
import fr.heavencraft.heavenproxy.commands.VoterCommand;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.exceptions.UUIDNotFoundException;
import fr.heavencraft.heavenproxy.jit.ServerProcessManager;
import fr.heavencraft.heavenproxy.kick.KickCommand;
import fr.heavencraft.heavenproxy.kick.RagequitCommand;
import fr.heavencraft.heavenproxy.listeners.LogListener;
import fr.heavencraft.heavenproxy.listeners.OnlineLogListener;
import fr.heavencraft.heavenproxy.listeners.SpyListener;
import fr.heavencraft.heavenproxy.managers.RequestsManager;
import fr.heavencraft.heavenproxy.motd.ProxyPingListener;
import fr.heavencraft.heavenproxy.mute.MuteCommand;
import fr.heavencraft.heavenproxy.mute.MuteListener;
import fr.heavencraft.heavenproxy.radio.HcsCommand;
import fr.heavencraft.heavenproxy.radio.RadioCommand;
import fr.heavencraft.heavenproxy.servers.TitleListener;
import fr.heavencraft.heavenproxy.users.TabListener;
import fr.heavencraft.heavenproxy.users.UsersListener;
import fr.heavencraft.heavenproxy.warn.WarnCommand;

public class HeavenProxy extends Plugin
{
	private final static String DB_URL = "jdbc:mysql://localhost:3306/proxy?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca&zeroDateTimeBehavior=convertToNull";
	private final static String MAIN_DB_URL = "jdbc:mysql://localhost:3306/mc-db?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca&zeroDateTimeBehavior=convertToNull";
	private final static String SRP_DB_URL = "jdbc:mysql://localhost:3306/minecraft-semirp?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca&zeroDateTimeBehavior=convertToNull";
	private final static String CREA_DB_URL = "jdbc:mysql://localhost:3306/minecraft-creative?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca&zeroDateTimeBehavior=convertToNull";
	private final static String ORIGINES_DB_URL = "jdbc:mysql://localhost:3306/minecraft-survie?user=mc-sql&password=9e781e41f865901850d5c3060063c8ca&zeroDateTimeBehavior=convertToNull";

	private static Connection _connection;
	private static Connection _mainConnection;
	private static Connection _srpConnection;
	private static Connection _creaConnection;
	private static Connection _originesConnection;

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
			new OuestCommand();
			new ReplyCommand();
			new SayCommand();
			new SendCommand();
			new SpyCommand();
			new TellCommand();
			new TextCommand();
			new VoterCommand();

			// Ban
			new BanCommand();
			new BanListener();
			new SilentBanListener();
			new UnbanCommand();

			// Chat
			new ChatListener();
			new FloodListener();
			new ModoListener();
			new TabCompleteListener();

			// Kick
			new KickCommand();
			new RagequitCommand();

			// MOTD
			new ProxyPingListener();

			// Mute
			new MuteCommand();
			new MuteListener();

			// Radio
			new HcsCommand();
			new RadioCommand();

			// Servers
			new TitleListener();

			// Users
			new TabListener();
			new UsersListener();

			// Warn
			new WarnCommand();

			new ServerProcessManager();

			new AutoMessageTask();
			new MemoryWatcherTask();

			_requestsManager = new RequestsManager();

			convertBanList();
			convertUserList();
			convertUserSemirp();
			convertUserCreative();
			convertUserOrigines();
		}
		catch (final Throwable t)
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
		catch (final SQLException ex)
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
		catch (final SQLException ex)
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
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			ProxyServer.getInstance().stop();
		}

		return _srpConnection;
	}

	public static Connection getCreaConnection()
	{
		try
		{
			if (_creaConnection == null || _creaConnection.isClosed())
			{
				_creaConnection = DriverManager.getConnection(CREA_DB_URL);
			}
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			ProxyServer.getInstance().stop();
		}

		return _creaConnection;
	}

	public static Connection getOriginesConnection()
	{
		try
		{
			if (_originesConnection == null || _originesConnection.isClosed())
			{
				_originesConnection = DriverManager.getConnection(ORIGINES_DB_URL);
			}
		}
		catch (final SQLException ex)
		{
			ex.printStackTrace();
			ProxyServer.getInstance().stop();
		}

		return _originesConnection;
	}

	public static HeavenProxy getInstance()
	{
		return _instance;
	}

	public static void convertBanList()
	{
		try
		{
			final PreparedStatement ps = getConnection().prepareStatement(
					"SELECT name FROM banlist WHERE uuid = ''");
			final ResultSet rs = ps.executeQuery();

			while (rs.next())
			{
				try
				{
					final String name = rs.getString("name");
					final String uuid = Utils.getUUID(name);

					final PreparedStatement ps2 = getConnection().prepareStatement(
							"UPDATE banlist SET uuid = ? WHERE name = ?");
					ps2.setString(1, uuid);
					ps2.setString(2, name);

					ps2.executeUpdate();
					System.out.println(name + " -> " + uuid);
				}
				catch (final HeavenException ex)
				{
					System.out.println(ex.getMessage());
				}
			}

		}
		catch (final SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void convertUserList()
	{
		ProxyServer.getInstance().getScheduler().runAsync(HeavenProxy.getInstance(), new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					final PreparedStatement ps = getConnection().prepareStatement(
							"SELECT name FROM users WHERE uuid = ''");
					final ResultSet rs = ps.executeQuery();

					while (rs.next())
					{
						try
						{
							final String name = rs.getString("name");
							final String uuid = Utils.getUUID(name);

							final PreparedStatement ps2 = getConnection().prepareStatement(
									"UPDATE users SET uuid = ? WHERE name = ?");
							ps2.setString(1, uuid);
							ps2.setString(2, name);

							ps2.executeUpdate();
							System.out.println("Proxy" + name + " -> " + uuid);
						}
						catch (final HeavenException ex)
						{
							System.out.println(ex.getMessage());
						}
					}

				}
				catch (final SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public static void convertUserSemirp()
	{
		ProxyServer.getInstance().getScheduler().runAsync(HeavenProxy.getInstance(), new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					final PreparedStatement ps = getSrpConnection().prepareStatement(
							"SELECT name FROM users WHERE uuid = ''");
					final ResultSet rs = ps.executeQuery();

					while (rs.next())
					{
						try
						{
							final String name = rs.getString("name");
							final String uuid = Utils.getUUID(name);

							final PreparedStatement ps2 = getSrpConnection().prepareStatement(
									"UPDATE users SET uuid = ? WHERE name = ?");
							ps2.setString(1, uuid);
							ps2.setString(2, name);

							ps2.executeUpdate();
							System.out.println("SemiRP" + name + " -> " + uuid);
						}
						catch (final HeavenException ex)
						{
							System.out.println(ex.getMessage());
						}
					}

				}
				catch (final SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	private static final Pattern UUID_PATTERN = Pattern
			.compile("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)");

	public static void convertUserCreative()
	{
		ProxyServer.getInstance().getScheduler().runAsync(HeavenProxy.getInstance(), new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					final PreparedStatement ps = getCreaConnection().prepareStatement(
							"SELECT name FROM users WHERE uuid = ''");
					final ResultSet rs = ps.executeQuery();

					while (true)
					{
						final ArrayList<String> names = new ArrayList<String>();

						for (int i = 0; i != 100; i++)
							if (rs.next())
								names.add(rs.getString("name"));

						if (names.size() == 0)
							return;

						final ProfileRepository repository = new HttpProfileRepository("minecraft");
						final Profile[] profiles = repository.findProfilesByNames(names.toArray(new String[names
								.size()]));

						for (final Profile profile : profiles)
						{
							try
							{

								final String name = profile.getName();
								final String uuid = UUID_PATTERN.matcher(profile.getId()).replaceFirst(
										"$1-$2-$3-$4-$5");

								if (name == null || uuid == null)
									throw new UUIDNotFoundException(name);

								final PreparedStatement ps2 = getCreaConnection().prepareStatement(
										"UPDATE users SET uuid = ? WHERE name = ?");
								ps2.setString(1, uuid);
								ps2.setString(2, name);

								ps2.executeUpdate();
								System.out.println("Creative " + name + " -> " + uuid);
							}
							catch (final HeavenException ex)
							{
								System.out.println(ex.getMessage());
								ex.printStackTrace();
							}
						}
					}

				}
				catch (final SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	public static void convertUserOrigines()
	{
		ProxyServer.getInstance().getScheduler().runAsync(HeavenProxy.getInstance(), new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					final PreparedStatement ps = getOriginesConnection().prepareStatement(
							"SELECT name FROM users WHERE uuid = ''");
					final ResultSet rs = ps.executeQuery();

					while (true)
					{
						final ArrayList<String> names = new ArrayList<String>();

						for (int i = 0; i != 100; i++)
							if (rs.next())
								names.add(rs.getString("name"));

						if (names.size() == 0)
							return;

						final ProfileRepository repository = new HttpProfileRepository("minecraft");
						final Profile[] profiles = repository.findProfilesByNames(names.toArray(new String[names
								.size()]));

						for (final Profile profile : profiles)
						{
							try
							{

								final String name = profile.getName();
								final String uuid = UUID_PATTERN.matcher(profile.getId()).replaceFirst(
										"$1-$2-$3-$4-$5");

								if (name == null || uuid == null)
									throw new UUIDNotFoundException(name);

								final PreparedStatement ps2 = getOriginesConnection().prepareStatement(
										"UPDATE users SET uuid = ? WHERE name = ?");
								ps2.setString(1, uuid);
								ps2.setString(2, name);

								ps2.executeUpdate();
								System.out.println("Origines " + name + " -> " + uuid);
							}
							catch (final HeavenException ex)
							{
								System.out.println(ex.getMessage());
								ex.printStackTrace();
							}
						}
					}

				}
				catch (final SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}
}