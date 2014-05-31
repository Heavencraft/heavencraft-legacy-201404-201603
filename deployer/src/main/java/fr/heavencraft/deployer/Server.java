package fr.heavencraft.deployer;

import java.util.List;

public class Server
{
	private String path;
	private String server;
	private List<String> plugins;

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getServer()
	{
		return server;
	}

	public void setServer(String server)
	{
		this.server = server;
	}

	public List<String> getPlugins()
	{
		return plugins;
	}

	public void setPlugins(List<String> plugins)
	{
		this.plugins = plugins;
	}
}