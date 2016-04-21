package fr.heavencraft.deployer;

import java.util.List;
import java.util.Map;

public class Server
{
	private String path;
	private String server;
	private String configFile;
	private List<String> plugins;
	private Map<String, String> files;

	public String getPath()
	{
		return path;
	}

	public String getServer()
	{
		return server;
	}

	public String getConfigFile()
	{
		return configFile;
	}

	public List<String> getPlugins()
	{
		return plugins;
	}

	public Map<String, String> getFiles()
	{
		return files;
	}
}