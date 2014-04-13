package fr.heavencraft.heavenproxy.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;
import fr.heavencraft.heavenproxy.managers.KickManager;

public class RagequitCommand extends HeavenCommand {
	
	private final static String RAGEQUIT_MESSAGE = "Vous avez ragequit.\n\nBonne journée ;-)";
	private final static String RAGEQUIT = "R";
	
	public RagequitCommand()
	{
		super("ragequit", "", new String[] {});
	}

	@Override
	protected void onCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (!(sender instanceof ProxiedPlayer))
			return;
		
		ProxiedPlayer player = (ProxiedPlayer) sender;
		
		KickManager.addReason(player.getName(), RAGEQUIT);
		player.disconnect(RAGEQUIT_MESSAGE);
	}
}