package fr.heavencraft.deployer.plugins;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PluginsRepository
{
	private final Map<String, Plugin> pluginsByName;

	public PluginsRepository() throws FileNotFoundException
	{
		pluginsByName = loadPlugins();

		System.out.println("Available plugins :");
		for (final String plugin : pluginsByName.keySet())
			System.out.println("- " + plugin);
		System.out.println();
	}

	private static Map<String, Plugin> loadPlugins() throws FileNotFoundException
	{
		final Gson gson = new Gson();
		final BufferedReader br = new BufferedReader(new FileReader("cfg/plugins.json"));
		final Type listType = new TypeToken<Map<String, Plugin>>()
		{
		}.getType();

		return gson.fromJson(br, listType);
	}

	public Plugin getPluginByName(String name)
	{
		return pluginsByName.get(name);
	}

	public Collection<Plugin> getPluginsByNames(Collection<String> names)
	{
		final Collection<Plugin> plugins = new ArrayList<Plugin>();
		for (final String name : names)
		{
			final Plugin plugin = getPluginByName(name);
			if (plugin != null)
				plugins.add(plugin);
			else
				System.err.println("WARNING: plugin " + name + " does not exist.");
		}
		return plugins;
	}
}