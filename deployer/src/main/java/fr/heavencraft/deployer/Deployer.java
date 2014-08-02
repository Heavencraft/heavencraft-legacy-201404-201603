package fr.heavencraft.deployer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Deployer
{

	public static void main(String[] args) throws FileNotFoundException
	{
		/*
		 * Servers to update
		 */

		Gson gson = new Gson();
		BufferedReader br = new BufferedReader(new FileReader("servers.json"));
		Type listType = new TypeToken<ArrayList<Server>>()
		{
		}.getType();

		List<Server> servers = gson.fromJson(br, listType);

		System.out.println("Servers to update :");

		for (Server server : servers)
			System.out.println("- " + server.getPath());

		System.out.println();

		/*
		 * Servers & plugins jars
		 */

		Map<String, File> availableServers = getJars("servers");
		Map<String, File> availablePlugins = getJars("plugins");

		System.out.println("Available servers :");

		for (String server : availableServers.keySet())
			System.out.println("- " + server);

		System.out.println();
		System.out.println("Available plugins :");

		for (String plugin : availablePlugins.keySet())
			System.out.println("- " + plugin);

		System.out.println();

		/*
		 * UPDATE !!
		 */

		for (Server server : servers)
		{
			if (availableServers.containsKey(server.getServer()))
				copyJar(availableServers.get(server.getServer()), server.getPath() + "/" + server.getServer());

			for (String plugin : server.getPlugins())
			{
				if (availablePlugins.containsKey(plugin))
					copyJar(availablePlugins.get(plugin), server.getPath() + "/plugins/" + plugin);
			}
		}

	}

	private static void copyJar(File origin, String directory)
	{
		Path filePath = origin.toPath();
		File destFile = new File(directory);
		Path dirPath = destFile.toPath();

		try
		{
			if (!FileUtils.contentEquals(origin, destFile))
			{
				System.out.println("Copying " + filePath + " to " + dirPath);
				Files.copy(filePath, dirPath, StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	private static Map<String, File> getJars(String path)
	{
		Map<String, File> files = new HashMap<String, File>();

		for (File file : new File(path).listFiles(new JarFilenameFilter()))
			files.put(file.getName(), file);

		return files;
	}

	static class JarFilenameFilter implements FilenameFilter
	{
		@Override
		public boolean accept(File dir, String name)
		{
			return name.endsWith(".jar");
		}
	}
}