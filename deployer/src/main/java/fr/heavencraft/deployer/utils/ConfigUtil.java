package fr.heavencraft.deployer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil
{
	private static final File CONFIG_DIR = new File("cfg");

	public static Properties loadPropertiesFile(String configFile)
	{
		return loadPropertiesFile(new File(CONFIG_DIR, configFile), null);
	}

	public static Properties loadPropertiesFile(String configFile, Properties defaults)
	{
		return loadPropertiesFile(new File(CONFIG_DIR, configFile), defaults);
	}

	public static Properties loadPropertiesFile(File configFile)
	{
		return loadPropertiesFile(configFile, null);
	}

	public static Properties loadPropertiesFile(File configFile, Properties defaults)
	{
		final Properties properties = new Properties(defaults);

		try (BufferedReader reader = new BufferedReader(new FileReader(configFile)))
		{
			properties.load(reader);
		}
		catch (final IOException e)
		{
			throw new RuntimeException("Unable to load properties file " + configFile, e);
		}

		return properties;
	}
}