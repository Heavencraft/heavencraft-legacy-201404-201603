package fr.heavencraft.heavenproxy.radio;

import net.md_5.bungee.api.CommandSender;
import fr.heavencraft.heavenproxy.commands.HeavenCommand;
import fr.heavencraft.heavenproxy.exceptions.HeavenException;

public class HcsCommand extends HeavenCommand
{
	public HcsCommand()
	{
		super("hcs", "heavencraft.commands.radio", new String[] {});
	}

	@Override
	protected void onCommand(CommandSender sender, String[] args) throws HeavenException
	{
		// TODO Auto-generated method stub

	}
}