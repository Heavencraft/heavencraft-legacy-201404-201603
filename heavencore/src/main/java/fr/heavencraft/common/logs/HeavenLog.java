package fr.heavencraft.common.logs;

public abstract class HeavenLog
{
	public static HeavenLog getLogger(Class<?> clazz)
	{
		return new BukkitHeavenLog(clazz.getSimpleName());
	}

	public abstract void enableDebug();

	public abstract void debug(String format, Object... args);

	public abstract void info(String format, Object... args);

	public abstract void warn(String format, Object... args);

	public abstract void error(String format, Object... args);
}