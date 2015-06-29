package fr.heavencraft.heavenproxy.jit;

import java.io.File;
import java.io.IOException;

import fr.heavencraft.heavenproxy.ProxyLogger;

public enum ServerProcess
{
	UAT_SemiRP("uat-semirp", ServerProcessManager._2G, "/home/minecraft/uat/servers/semirp"),
	UAT_Creative("uat-creative", ServerProcessManager._2G, "/home/minecraft/uat/servers/creative");

	public static ServerProcess getUniqueInstanceByName(String name)
	{
		for (final ServerProcess serverProcess : ServerProcess.values())
		{
			if (serverProcess.name.equals(name))
			{
				return serverProcess;
			}
		}
		return null;
	}

	private static final ProxyLogger log = ProxyLogger.getLogger(ServerProcess.class);

	// Bash scripts
	private static final String START_SERVER = "/home/minecraft/scripts/start_server.sh";
	private static final String STOP_SERVER = "/home/minecraft/scripts/stop_server.sh";
	// Log messages
	private static final String STARTING_SERVER = "Starting server %1$s with %2$s MB on %3$s";
	private static final String STOPPING_SERVER = "Stopping server %1$s";
	private static final String SERVER_STARTED = "Server %1$s started";
	private static final String SERVER_STOPPED = "Server %1$s stopped";

	private final String name;
	private final int memory;
	private final File path;

	private ServerProcess(String name, int memory, String path)
	{
		this.name = name;
		this.memory = memory;
		this.path = new File(path).getAbsoluteFile();
	}

	public boolean start()
	{
		log.info(STARTING_SERVER, name, memory, path);

		try
		{
			new ProcessBuilder(START_SERVER, name, memory + "M").directory(path).start();
			log.info(SERVER_STARTED, name);
			return true;
		}
		catch (final IOException ex)
		{
			log.error("Cannot start server %1$s", name);
			ex.printStackTrace();
			return false;
		}
	}

	public boolean stop()
	{
		log.info(STOPPING_SERVER, name);

		try
		{
			new ProcessBuilder(STOP_SERVER, name).start();
			log.info(SERVER_STOPPED, name);
			return true;
		}
		catch (final IOException ex)
		{
			log.error("Cannot stop server %1$s", name);
			ex.printStackTrace();
			return false;
		}
	}

	public boolean hasEnoughtMemory()
	{
		final long freeMemory = SystemHelper.getFreeMemoryMb();
		log.info("Free memory : %1$s, needed memory : %2$s", freeMemory, memory);
		return freeMemory > memory;
	}
}