package fr.heavencraft.heavenrp.commands.horses;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.horses.HorsesManager;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.PlayerUtil;

public class ChevalCommand extends HeavenCommand
{
	public ChevalCommand()
	{
		super("cheval");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (!player.isInsideVehicle() || player.getVehicle().getType() != EntityType.HORSE)
			throw new HeavenException("Ceci n'est PAS un cheval.");

		Horse horse = (Horse) player.getVehicle();

		if (HorsesManager.isWild(horse))
			throw new HeavenException("Sauvage, ce cheval est. Le dompter, tu devras.");

		else if (args.length == 2 && args[0].equalsIgnoreCase("donner"))
		{
			if (!HorsesManager.canUse(horse, player))
				throw new HeavenException("Vous n'êtes pas le propriétaire de ce cheval.");

			Player owner = PlayerUtil.getPlayer(args[1]);
			horse.setOwner(owner);
			horse.eject();

			ChatUtil.sendMessage(player, "Le propriétaire de ce cheval est désormais {%1$s}.", owner.getName());
		}

		else
			HorsesManager.sendWarning(horse, player);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}