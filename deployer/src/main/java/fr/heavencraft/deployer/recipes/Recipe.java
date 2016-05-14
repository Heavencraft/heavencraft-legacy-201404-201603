package fr.heavencraft.deployer.recipes;

import java.util.List;
import java.util.Map;

public class Recipe
{
	private List<String> applyTo;
	private String server;
	private List<String> plugins;
	private Map<String, String> files;
	private Map<String, String> staticFiles;

	public List<String> getApplyTo()
	{
		return applyTo;
	}

	public String getServerName()
	{
		return server;
	}

	public List<String> getPluginsNames()
	{
		return plugins;
	}

	public Map<String, String> getFiles()
	{
		return files;
	}

	public Map<String, String> getStaticFiles()
	{
		return staticFiles;
	}
}