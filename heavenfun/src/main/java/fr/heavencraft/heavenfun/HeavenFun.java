package fr.heavencraft.heavenfun;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import fr.heavencraft.HeavenPlugin;
import fr.heavencraft.heavenfun.commands.CommandsManager;
import fr.heavencraft.listeners.ColoredSignsListener;
import fr.heavencraft.listeners.JumpListener;
import fr.heavencraft.listeners.NoChatListener;
import fr.heavencraft.listeners.RedstoneLampListener;
import fr.heavencraft.listeners.sign.LinkSignListener;

public class HeavenFun extends HeavenPlugin
{
	private static Location spawn;

	@Override
	public void onEnable()
	{
		super.onEnable();

		CommandsManager.init();

		new ServerListener();

		new JumpListener();
		new NoChatListener();
		new RedstoneLampListener();

		new ColoredSignsListener();
		new LinkSignListener();
	}

	public static Location getSpawn()
	{
		if (spawn == null)
			spawn = new Location(Bukkit.getWorld("world"), 0.5, 100.0, 0.5);

		return spawn;
	}
}