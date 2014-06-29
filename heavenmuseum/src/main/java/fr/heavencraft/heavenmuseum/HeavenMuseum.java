package fr.heavencraft.heavenmuseum;

import fr.heavencraft.HeavenPlugin;
import fr.heavencraft.commands.AccepterCommand;
import fr.heavencraft.commands.RejoindreCommand;
import fr.heavencraft.heavenmuseum.commands.SpawnCommand;
import fr.heavencraft.heavenmuseum.listeners.PlayerListener;
import fr.heavencraft.heavenmuseum.listeners.ServerListener;
import fr.heavencraft.heavenmuseum.managers.WorldsManager;

public class HeavenMuseum extends HeavenPlugin
{
	@Override
	public void onEnable()
	{
		super.onEnable();

		new PlayerListener();
		new ServerListener();

		new SpawnCommand();
		new RejoindreCommand();
		new AccepterCommand();

		WorldsManager.init();
	}

}