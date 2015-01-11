package fr.heavencraft.common.logs;

import org.bukkit.Bukkit;

public class BukkitHeavenLog extends HeavenLog
{
	private final String prefix;

	BukkitHeavenLog(String prefix)
	{
		this.prefix = new StringBuilder().append("[").append(prefix).append("] ").toString();
	}

	@Override
	public void info(String format, Object... args)
	{
		Bukkit.getLogger().info(new StringBuilder().append(prefix).append(String.format(format, args)).toString());
	}

	@Override
	public void warn(String format, Object... args)
	{
		Bukkit.getLogger().warning(new StringBuilder().append(prefix).append(String.format(format, args)).toString());
	}

	@Override
	public void error(String format, Object... args)
	{
		Bukkit.getLogger().severe(new StringBuilder().append(prefix).append(String.format(format, args)).toString());
	}
}