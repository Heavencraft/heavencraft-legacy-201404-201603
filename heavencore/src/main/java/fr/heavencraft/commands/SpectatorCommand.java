package fr.heavencraft.commands;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.Permissions;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.heavencraft.utils.PlayerUtil;

public class SpectatorCommand extends HeavenCommand
{
	public SpectatorCommand()
	{
		super("spectator", Permissions.SPECTATOR);
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		if (player.getGameMode() == GameMode.SPECTATOR)
			player.setGameMode(GameMode.SURVIVAL);
		else
			player.setGameMode(GameMode.SPECTATOR);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		if (args.length != 1)
			return;

		Player player = PlayerUtil.getPlayer(args[0]);

		ChatUtil.sendMessage(sender, "{%1$s} est en mode {%2$s}.", player.getName(), player.getGameMode()
				.name());
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}
