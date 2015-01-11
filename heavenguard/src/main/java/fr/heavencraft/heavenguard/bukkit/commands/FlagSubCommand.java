package fr.heavencraft.heavenguard.bukkit.commands;

import java.sql.Types;

import org.bukkit.command.CommandSender;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenguard.api.Flag;
import fr.heavencraft.heavenguard.api.Region;
import fr.heavencraft.heavenguard.bukkit.HeavenGuard;
import fr.heavencraft.utils.ChatUtil;

public class FlagSubCommand implements SubCommand
{
	@Override
	public boolean hasPermission(CommandSender sender)
	{
		return true;
	}

	@Override
	public void execute(CommandSender sender, String[] args) throws HeavenException
	{
		switch (args.length)
		{
			case 3: // Remove the flag from the region.
				flag(sender, args[1], args[2], null);
				break;

			case 4: // Set the flag's value.
				flag(sender, args[1], args[2], args[3]);
				break;

			default:
				sendUsage(sender);
				break;
		}
	}

	@Override
	public void sendUsage(CommandSender sender)
	{
		ChatUtil.sendMessage(sender, "/{region} flag <protection> <flag> : pour supprimer un flag");
		ChatUtil.sendMessage(sender, "/{region} flag <protection> <flag> <valeur> : pour ajouter un flag");
	}

	private void flag(CommandSender sender, String regionName, String flagName, String value) throws HeavenException
	{
		Flag flag = Flag.getUniqueInstanceByName(flagName);

		if (flag == null) throw new HeavenException("Le flag {%1$s} n'existe pas.", flagName);

		Region region = HeavenGuard.getRegionProvider().getRegionByName(regionName);

		switch (flag.getType())
		{
			case Types.BIT:
				region.setBooleanFlag(flag, value != null ? Boolean.parseBoolean(value) : null);
				break;
		}

		ChatUtil.sendMessage(sender, "La protection {%1$s} a désormais : {%2$s} = {%3$s}", region.getName(), flag.getName(),
				value);
	}
}