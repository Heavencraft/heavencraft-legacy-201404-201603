package fr.heavencraft.heavenfactions.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;

import fr.heavencraft.heavenfactions.Utils;
import fr.heavencraft.heavenfactions.commands.HeavenCommand;
import fr.heavencraft.heavenfactions.exceptions.HeavenException;

public class FChatCommand extends HeavenCommand
{
	private final static String FORMAT = "§a[%1$s] §f§l> §r§f%2$s";

	public FChatCommand()
	{
		super("fchat");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length == 0)
			return;

		UPlayer uplayer = UPlayer.get(player);

		if (!uplayer.hasFaction())
			throw new HeavenException("Vous n'êtes membre d'aucune faction.");

		Faction faction = uplayer.getFaction();
		String message = Utils.ArrayToString(args, 0, " ");

		for (UPlayer dest : faction.getUPlayersWhereOnline(true))
		{
			dest.sendMessage(String.format(FORMAT, player.getName(), message));
		}
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		Utils.sendMessage(sender, "Cette commande ne peut pas être utilisée depuis la {console}.");
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}