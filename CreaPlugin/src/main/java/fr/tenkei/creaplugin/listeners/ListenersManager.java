package fr.tenkei.creaplugin.listeners;

import fr.heavencraft.listeners.RedstoneLampListener;
import fr.heavencraft.listeners.sign.LinkSignListener;
import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.listeners.protection.ProtectionBlockListener;
import fr.tenkei.creaplugin.listeners.protection.ProtectionEntityListener;
import fr.tenkei.creaplugin.listeners.protection.ProtectionHangingListener;
import fr.tenkei.creaplugin.listeners.protection.ProtectionPlayerListener;
import fr.tenkei.creaplugin.listeners.protection.ProtectionVehicleListener;
import fr.tenkei.creaplugin.users.UserListener;

public class ListenersManager
{
	public ListenersManager(MyPlugin plugin)
	{

		new BlockListener(plugin);
		new ChatListener(plugin);
		new ServerListener();
		new PlayerListener(plugin);
		new JumpListener(plugin);
		new RedstoneLampListener();
		new LinkSignListener();

		new UserListener();

		new ProtectionBlockListener(plugin);
		new ProtectionEntityListener(plugin);
		new ProtectionHangingListener(plugin);
		new ProtectionPlayerListener(plugin);
		new ProtectionVehicleListener(plugin);
	}
}
