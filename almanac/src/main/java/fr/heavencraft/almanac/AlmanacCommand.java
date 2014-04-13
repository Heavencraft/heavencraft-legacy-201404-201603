package fr.heavencraft.almanac;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlmanacCommand implements CommandExecutor
{
	public AlmanacCommand()
	{
		AlmanacPlugin.getInstance().getCommand("almanac").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args)
	{
		if (!sender.getName().equals("lorgan17"))
			return true;
		
		if (args.length != 1)
			return true;
		
		Player player = (Player)sender;
		AlmanacManager.giveAlmanac(player, args[0]);
		return true;
	}

}
