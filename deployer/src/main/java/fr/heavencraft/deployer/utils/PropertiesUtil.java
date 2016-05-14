package fr.heavencraft.deployer.utils;

import java.util.Properties;

public class PropertiesUtil
{
	public static String getProperty(String name, Properties properties)
	{
		return replaceProperties("${" + name + "}", properties);
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
}