package fr.tenkei.creaplugin.listeners;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.listeners.protection.ProtectionBlockListener;
import fr.tenkei.creaplugin.listeners.protection.ProtectionEntityListener;
import fr.tenkei.creaplugin.listeners.protection.ProtectionHangingListener;
import fr.tenkei.creaplugin.listeners.protection.ProtectionPlayerListener;
import fr.tenkei.creaplugin.listeners.protection.ProtectionVehicleListener;

public class ListenersManager {
	public ListenersManager(MyPlugin plugin) {
		
		new BlockListener(plugin);
		new ChatListener(plugin);
		new ServerListener(plugin);
		new PlayerListener(plugin);
		new JumpListener(plugin);
		
		new ProtectionBlockListener(plugin);
		new ProtectionEntityListener(plugin);
		new ProtectionHangingListener(plugin);
		new ProtectionPlayerListener(plugin);
		new ProtectionVehicleListener(plugin);
	}
}
