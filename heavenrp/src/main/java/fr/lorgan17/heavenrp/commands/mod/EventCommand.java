package fr.lorgan17.heavenrp.commands.mod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.RPPermissions;
import fr.heavencraft.utils.ChatUtil;

public class EventCommand extends HeavenCommand
{
	private Location _spawn = null;
	private boolean _started = false;

	public EventCommand()
	{
		super("event");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length != 1)
		{
			sendUsage(player);
			return;
		}

		if (args[0].equalsIgnoreCase("go"))
		{
			if (_started)
			{
				player.teleport(_spawn);
				ChatUtil.sendMessage(player, "Vous avez été téléporté à l'event.");
			}
			else
				ChatUtil.sendMessage(player, "Aucun event n'est en cours.");
		}
		else if (player.hasPermission(RPPermissions.EVENT))
		{
			if (args[0].equalsIgnoreCase("setspawn"))
			{
				_spawn = player.getLocation();
				ChatUtil.sendMessage(player, "La position du spawn a bien été changée.");
			}
			else if (args[0].equalsIgnoreCase("start"))
			{
				if (_spawn == null)
				{
					ChatUtil.sendMessage(player, "Tu veux lancer un event sans spawn");
				}
				else if (!_started)
				{
					_started = true;
					Bukkit.broadcastMessage(ChatColor.AQUA + "[EVENT]" + ChatColor.RESET
							+ "Un event vient de commencer, tapez /event go pour y participer !");
				}
			}
			else if (args[0].equalsIgnoreCase("stop"))
			{
				if (_started)
				{
					_started = false;
					Bukkit.broadcastMessage(ChatColor.AQUA + "[EVENT]" + ChatColor.RESET
							+ "L'event vient de se terminer !");
				}
			}
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{event} setspawn");
		ChatUtil.sendMessage(sender, "/{event} start");
		ChatUtil.sendMessage(sender, "/{event} stop");
		ChatUtil.sendMessage(sender, "/{event} go");
	}
}
