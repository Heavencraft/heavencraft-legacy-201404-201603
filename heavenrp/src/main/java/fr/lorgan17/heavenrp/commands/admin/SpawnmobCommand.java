package fr.lorgan17.heavenrp.commands.admin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Permissions;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;

public class SpawnmobCommand extends HeavenCommand
{
	public SpawnmobCommand()
	{
		super("spawnmob", Permissions.SPAWNMOB_COMMAND);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length == 0)
		{
			return;
		}
		int nb = 1;
		
		if (args.length > 1)
			nb = Utils.toUint(args[1]);
		
		EntityType entity = EntityType.fromName(args[0]);
		
		for (int i = 0; i != nb; i++)
			player.getWorld().spawnEntity(player.getLocation(), entity);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
		// TODO Auto-generated method stub

	}

}
