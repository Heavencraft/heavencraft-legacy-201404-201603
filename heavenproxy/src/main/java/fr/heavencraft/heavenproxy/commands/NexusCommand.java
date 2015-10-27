package fr.heavencraft.heavenproxy.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;

public class NexusCommand extends HeavenCommand {

	public NexusCommand()
	{
		super("nexus", null, new String[] { "hub", "lobby" });
	}

	@Override
	protected void onCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (!(sender instanceof ProxiedPlayer))
			return;
		
		ProxiedPlayer player = (ProxiedPlayer) sender;
		
		player.connect(_plugin.getProxy().getServerInfo("nexus"));
		Utils.sendMessage(player, "Vous avez été téléporté au Nexus.");
	}
}
