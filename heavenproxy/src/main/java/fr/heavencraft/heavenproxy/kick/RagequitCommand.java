package fr.heavencraft.heavenproxy.kick;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import fr.heavencraft.heavenproxy.Utils;
import fr.heavencraft.heavenproxy.chat.DisconnectReasonManager;
import fr.heavencraft.heavenproxy.commands.HeavenCommand;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;

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
		
		DisconnectReasonManager.addReason(player.getName(), RAGEQUIT);
		
		Utils.kickPlayer(player, RAGEQUIT_MESSAGE);
	}
}