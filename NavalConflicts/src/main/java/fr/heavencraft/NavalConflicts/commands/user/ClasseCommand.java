package fr.heavencraft.NavalConflicts.commands.user;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.NavalConflicts.Utils;
import fr.heavencraft.NavalConflicts.commands.NavalCommand;
import fr.heavencraft.NavalConflicts.exceptions.NavalException;

public class ClasseCommand extends NavalCommand{
	public ClasseCommand()
	{
		super("class");
	}
	
	@Override
	protected void onPlayerCommand(Player player, String[] args) throws NavalException
	{
		player.sendMessage("Vous pouvez selectionner parmis les classes suivantes:");
		//TODO: afficher les classes
		//TODO: si argument, selectioner la classe.
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws NavalException {
		Utils.sendMessage(sender, "Cette commande ne peut pas être utilisée depuis la {console}.");
		}

	@Override
	protected void sendUsage(CommandSender sender) {}
}
