package fr.heavencraft.heavennexus.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.commands.HeavenCommand;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavennexus.HeavenNexus;
import fr.heavencraft.utils.ChatUtil;

public class TutoCommand extends HeavenCommand
{
    public TutoCommand()
    {
        super("tuto");
    }

    @Override
    protected void onPlayerCommand(Player player, String[] args) throws HeavenException
    {
        player.teleport(HeavenNexus.getTuto());
        ChatUtil.sendMessage(player, "Vous avez été téléporté au {tuto}.");
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