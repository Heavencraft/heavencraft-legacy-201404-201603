package fr.heavencraft.heavenproxy.jit;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import fr.heavencraft.heavenproxy.HeavenProxy;

public class StopServerTask implements Runnable
{
	public StopServerTask()
	{
		ProxyServer.getInstance().getScheduler()
				.schedule(HeavenProxy.getInstance(), this, 1, 1, TimeUnit.MINUTES);
	}

	@Override
	public void run()
	{
		for (final ServerInfo server : ProxyServer.getInstance().getServers().values())
		{
			// This server is not empty
			if (!server.getPlayers().isEmpty())
				continue;

			final ServerProcess serverProcess = ServerProcess.getUniqueInstanceByName(server.getName());

			// This server is not handled by this system
			if (serverProcess == null)
				continue;

			// The server is already stopped, nothing to do
			if (SystemHelper.isPortAvailable(server.getAddress().getPort()))
				continue;

			serverProcess.stop();
		}
	}
}