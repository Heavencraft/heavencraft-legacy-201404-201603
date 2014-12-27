package fr.heavencraft.utils;

public class HeavenLog
{
	public static HeavenLog getLogger(Class<?> clazz)
	{
		return new HeavenLog(clazz.getSimpleName());
	}

	private final String prefix;

	private HeavenLog(String prefix)
	{
		this.prefix = new StringBuilder().append("[").append(prefix).append("] ").toString();
	}

	public void info(String format, Object... args)
	{
		DevUtil.getPlugin().getLogger().info(prefix + String.format(format, args));
	}
}