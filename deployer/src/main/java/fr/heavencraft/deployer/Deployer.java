package fr.heavencraft.deployer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Deployer
{
	private static final File AVAILABLE_SERVERS_DIR = new File("servers");
	private static final File AVAILABLE_PLUGINS_DIR = new File("plugins");
	private static final File CONFIG_DIR = new File("cfg");
	private static final File AVAILABLE_FILES_DIR = new File("files");

	private static final File DEFAULT_CONFIG_FILE = new File("cfg/default.cfg");

	public static void main(String[] args) throws FileNotFoundException
	{
		// Loading default configuration
		final Properties defaultConfig = loadPropertiesFile(DEFAULT_CONFIG_FILE);

		/*
		 * Servers to update
		 */

		final Gson gson = new Gson();
		final BufferedReader br = new BufferedReader(new FileReader("servers.json"));
		final Type listType = new TypeToken<ArrayList<Server>>()
		{
		}.getType();

		final List<Server> servers = gson.fromJson(br, listType);

		System.out.println("Servers to update :");

		for (final Server server : servers)
			System.out.println("- " + server.getPath());

		System.out.println();

		/*
		 * Servers & plugins jars
		 */

		final Map<String, File> availableServers = getJars(AVAILABLE_SERVERS_DIR);
		final Map<String, File> availablePlugins = getJars(AVAILABLE_PLUGINS_DIR);
		final Map<String, File> availableFiles = getFiles(AVAILABLE_FILES_DIR);

		System.out.println("Available servers :");

		for (final String server : availableServers.keySet())
			System.out.println("- " + server);

		System.out.println();
		System.out.println("Available plugins :");

		for (final String plugin : availablePlugins.keySet())
			System.out.println("- " + plugin);

		System.out.println();

		/*
		 * UPDATE !!
		 */

		for (final Server server : servers)
		{
			// Loading server configuration
			final Properties serverConfig = loadPropertiesFile(new File(CONFIG_DIR, server.getConfigFile()),
					defaultConfig);

			final File serverDir = new File(server.getPath());
			if (!serverDir.exists())
				serverDir.mkdirs();

			final File pluginsDir = new File(serverDir, "plugins");
			if (!pluginsDir.exists())
				pluginsDir.mkdirs();

			final File serverJar = availableServers.get(server.getServer());
			if (serverJar != null)
			{
				copyFile(serverJar, new File(serverDir, server.getServer()));
				serverConfig.put("server.jar", server.getServer());
			}

			// Plugins: just copy them
			final List<String> plugins = server.getPlugins();
			if (plugins != null && !plugins.isEmpty())
			{
				for (final String plugin : plugins)
				{
					final File sourceFile = availablePlugins.get(plugin);
					if (sourceFile == null)
					{
						System.err.println("WARNING: source file not found: " + plugin);
						continue;
					}

					copyFile(sourceFile, new File(pluginsDir, plugin));
				}
			}

			// Static files: just copy them
			final Map<String, String> staticFiles = server.getStaticFiles();
			if (staticFiles != null && !staticFiles.isEmpty())
			{
				for (final Entry<String, String> config : staticFiles.entrySet())
				{
					final File sourceFile = availableFiles.get(config.getKey());
					if (sourceFile == null)
					{
						System.err.println("WARNING: source file not found: " + config.getKey());
						continue;
					}

					copyFile(sourceFile, new File(serverDir, config.getValue()));
				}
			}

			for (final Entry<String, String> config : server.getFiles().entrySet())
			{
				final File model = availableFiles.get(config.getKey());

				if (model == null)
				{
					System.err.println("WARNING: Unable to locate config " + config.getKey());
					continue;
				}

				final File destFile = new File(serverDir, config.getValue());
				final File destDir = destFile.getParentFile();
				if (destDir != null && !destDir.exists())
					destDir.mkdirs();

				try
				{
					copyConfig(model, destFile, serverConfig);
				}
				catch (final IOException e)
				{
					System.err.println("WARNING: Unable to copy config " + config.getKey());
					e.printStackTrace();
				}
			}
		}

	}

	private static void copyFile(File sourceFile, File destFile)
	{
		final File destDir = destFile.getParentFile();
		if (destDir != null && !destDir.exists())
			destDir.mkdirs();

		try
		{
			if (!FileUtils.contentEquals(sourceFile, destFile))
			{
				System.out.println("Copying " + sourceFile + " to " + destFile);
				Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (final IOException ex)
		{
			ex.printStackTrace();
		}
	}

	private static void copyConfig(File model, File dest, Properties properties) throws IOException
	{
		if (model == null)
		{
			System.err.println("WARNING: file not found " + model);
			return;
		}

		String content = FileUtils.readFileToString(model);
		content = replaceProperties(content, properties);

		if (dest.exists())
		{
			final String destContent = FileUtils.readFileToString(dest);

			if (destContent.equals(content))
				return;
		}

		System.out.println("Copying " + model + " to " + dest);
		FileUtils.writeStringToFile(dest, content);
		if (dest.getName().endsWith(".sh") || model.getName().endsWith(".sh"))
		{
			final Set<PosixFilePermission> perms = Files.getPosixFilePermissions(dest.toPath());
			perms.add(PosixFilePermission.OWNER_EXECUTE);
			Files.setPosixFilePermissions(dest.toPath(), perms);
		}
	}

	public static String replaceProperties(String content, Properties properties)
	{
		int currentIndex = 0;
		while ((currentIndex = content.indexOf("${", currentIndex)) != -1)
		{
			final int endIndex = content.indexOf('}', currentIndex);

			final String propertyName = content.substring(currentIndex + 2, endIndex);
			final String propertyValue = properties.getProperty(propertyName);
			if (propertyValue != null)
			{
				content = content.substring(0, currentIndex) + propertyValue + content.substring(endIndex + 1);
			}
			else
			{
				System.err.println("WARNING: property not found: " + propertyName);
				currentIndex = endIndex + 1;
			}
		}
		return content;
	}

	private static Map<String, File> getJars(File dir)
	{
		if (!dir.exists())
		{
			dir.mkdirs();
			return Collections.emptyMap();
		}

		final Map<String, File> files = new HashMap<String, File>();

		for (final File file : dir.listFiles(new JarFilenameFilter()))
			files.put(file.getName(), file);

		return files;
	}

	private static Map<String, File> getFiles(File dir)
	{
		if (!dir.exists())
		{
			dir.mkdirs();
			return Collections.emptyMap();
		}

		final Map<String, File> files = new HashMap<String, File>();

		for (final File file : dir.listFiles())
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

	private static Properties loadPropertiesFile(File file)
	{
		return loadPropertiesFile(file, null);
	}

	private static Properties loadPropertiesFile(File file, Properties defaults)
	{
		final Properties properties = new Properties(defaults);

		try (BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			properties.load(reader);
		}
		catch (final IOException e)
		{
			throw new RuntimeException("Unable to load properties file " + file, e);
		}

		return properties;
	}
}