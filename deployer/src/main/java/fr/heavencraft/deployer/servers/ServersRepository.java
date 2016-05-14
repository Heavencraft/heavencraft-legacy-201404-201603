package fr.heavencraft.deployer.servers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ServersRepository
{
	private final Map<String, Server> serversByName;

	public ServersRepository() throws FileNotFoundException
	{
		serversByName = loadServers();

		System.out.println("Available servers :");
		for (final String plugin : serversByName.keySet())
			System.out.println("- " + plugin);
		System.out.println();
	}

	private static Map<String, Server> loadServers() throws FileNotFoundException
	{
		final Gson gson = new Gson();
		final BufferedReader br = new BufferedReader(new FileReader("cfg/servers.json"));
		final Type listType = new TypeToken<Map<String, Server>>()
		{
		}.getType();

		return gson.fromJson(br, listType);
	}

	public Server getServerByName(String name)
	{
		return serversByName.get(name);
	}
}