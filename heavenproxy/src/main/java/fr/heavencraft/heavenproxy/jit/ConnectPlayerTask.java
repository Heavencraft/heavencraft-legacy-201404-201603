package fr.heavencraft.heavenproxy.jit;

import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import fr.heavencraft.heavenproxy.HeavenProxy;

public class ConnectPlayerTask implements Runnable
{
	private final ScheduledTask task;
	private final ProxiedPlayer player;
	private final ServerInfo server;

	public ConnectPlayerTask(ProxiedPlayer player, ServerInfo server)
	{
		task = ProxyServer.getInstance().getScheduler()
				.schedule(HeavenProxy.getInstance(), this, 10, 1, TimeUnit.SECONDS);

		this.player = player;
		this.server = server;
	}

	@Override
	public void run()
	{
		// If the server is started
		if (!SystemHelper.isPortAvailable(server.getAddress().getPort()))
		{
			player.connect(server, new Callback<Boolean>()
			{
				@Override
				public void done(Boolean result, Throwable error)
				{
					if (result)
					{
						ProxyServer.getInstance().getScheduler().cancel(task);
					}
				}
			});
		}
	}
}
