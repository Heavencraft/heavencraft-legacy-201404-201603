package fr.heavencraft.utils;

public class LogUtil
{
	public static void info(Class<?> clazz, String format, Object... args)
	{
		DevUtil.getPlugin().getLogger().info(String.format(format, args));
	}
}