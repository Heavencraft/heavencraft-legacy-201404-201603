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
	public static final Location SPAWN = new Location(Bukkit.getWorld("world"), 0, 100, 0);

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
}