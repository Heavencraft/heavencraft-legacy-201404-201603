package fr.heavencraft.heavenfactions.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.massivecraft.factions.entity.UPlayer;

import fr.heavencraft.heavenfactions.Utils;
import fr.heavencraft.heavenfactions.commands.HeavenCommand;
import fr.heavencraft.heavenfactions.exceptions.HeavenException;

public class FAllianceCommand extends HeavenCommand
{
	private final static String FORMAT = "§a[%1$s] §f§l> §r§d[Alliés] §f%2$s";
	private final static String FORMATEXT = "§r§d[Alliés] §f§l> §r§a[%1$s] §r§f%2$s";

	public FAllianceCommand()
	{
		super("falliance");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (args.length == 0)
			return;

		UPlayer uplayer = UPlayer.get(player);

		if (!uplayer.hasFaction())
			throw new HeavenException("Vous n'êtes membre d'aucune faction.");

		String message = Utils.ArrayToString(args, 0, " ");

		for (Player player2 : Bukkit.getOnlinePlayers())
		{
			if (areAlly(player, player2))
			{
				if (uplayer.getFaction() != UPlayer.get(player2).getFaction())
				{
					player2.sendMessage(String.format(FORMATEXT, player.getName(), message));
				}
				else
				{
					player2.sendMessage(String.format(FORMAT, player.getName(), message));
				}

			}
		}

	}

	private static boolean areAlly(Player p1, Player p2)
	{
		UPlayer up1 = UPlayer.get(p1);
		UPlayer up2 = UPlayer.get(p2);

		return up1.getRelationTo(up2).getValue() >= 40;
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