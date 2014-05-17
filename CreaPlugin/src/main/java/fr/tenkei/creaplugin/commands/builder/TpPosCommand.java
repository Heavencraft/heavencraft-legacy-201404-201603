package fr.tenkei.creaplugin.commands.builder;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.commands.Command;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.managers.WorldsManager;
import fr.tenkei.creaplugin.utils.Message;
import fr.tenkei.creaplugin.utils.Stuff;

public class TpPosCommand extends Command{

	public TpPosCommand(MyPlugin plugin)
	{
		super("tppos", plugin);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws MyException
	{
		if(!player.hasPermission(MyPlugin.builder))
			return;
		
		if (args.length == 3) {

			int x = Stuff.toInt(args[0]);
			int y = Stuff.toInt(args[1]);
			int z = Stuff.toInt(args[2]);

			if(!player.hasPermission(MyPlugin.administrator))
				if(player.getWorld() != WorldsManager.getTheTravaux())
					return;

			if(player.getWorld() == WorldsManager.getTheCreative())
				if(x > MyPlugin.LIMITE_WORLDS || x < -MyPlugin.LIMITE_WORLDS 
						||z > MyPlugin.LIMITE_WORLDS || z < -MyPlugin.LIMITE_WORLDS)
					return;

			player.teleport(new Location(player.getWorld(), x, y, z));
			return;
		}

		sendUsage(player);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws MyException
	{
		Message.sendMessage(sender, "Cette commande n'est pas utilisable depuis la console.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		Message.sendMessage(sender, "{/tppos} <x> <y> <z> : vous téléporte à la position indiquée.");
	}
	
}
