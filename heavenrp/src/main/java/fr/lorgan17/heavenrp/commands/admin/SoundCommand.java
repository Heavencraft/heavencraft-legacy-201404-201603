package fr.lorgan17.heavenrp.commands.admin;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;

public class SoundCommand extends HeavenCommand
{

	public SoundCommand()
	{
		super("sound", "heavenrp.administrator.sound");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		onConsoleCommand(player, args);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length == 2)
		{
			Player p = Utils.getPlayer(args[0]);
			
			p.playSound(p.getLocation(), Sound.valueOf(args[1]), 1, 1);
		}
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		// TODO Auto-generated method stub

	}

}
