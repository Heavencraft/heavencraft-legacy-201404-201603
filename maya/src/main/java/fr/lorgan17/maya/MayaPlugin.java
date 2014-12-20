package fr.lorgan17.maya;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import fr.lorgan17.maya.commands.ButcherCommand;
import fr.lorgan17.maya.commands.DevenirCommand;
import fr.lorgan17.maya.commands.RejoindreCommand;
import fr.lorgan17.maya.commands.RoucoupsCommand;
import fr.lorgan17.maya.commands.SpawnCommand;
import fr.lorgan17.maya.commands.SpawnmobCommand;
import fr.lorgan17.maya.commands.TpposCommnand;
import fr.lorgan17.maya.listeners.ChatListener;
import fr.lorgan17.maya.listeners.ExplosionListener;
import fr.lorgan17.maya.listeners.PlayerListener;
import fr.lorgan17.maya.listeners.ProtectionListener;
import fr.lorgan17.maya.listeners.SheepListener;

public class MayaPlugin extends JavaPlugin
{
	private static MayaPlugin _instance;

	public static Location spawn;

	@Override
	public void onEnable()
	{
		super.onEnable();

		_instance = this;

		spawn = new Location(Bukkit.getWorld("world"), 145.5, 67, 130.5, 270, 0);

		new ChatListener();
		new ExplosionListener();
		new PlayerListener();
		new ProtectionListener();
		new SheepListener();

		new ButcherCommand();
		new DevenirCommand();
		new RejoindreCommand();
		new RoucoupsCommand();
		new SpawnCommand();
		new SpawnmobCommand();
		new TpposCommnand();
	}

	public static MayaPlugin getInstance()
	{
		return _instance;
	}

	public static void sendMessage(CommandSender sender, String message)
	{
		sender.sendMessage(ChatColor.GREEN
				+ message.replace("{", ChatColor.DARK_GREEN.toString()).replace("}", ChatColor.GREEN.toString()));
	}
}