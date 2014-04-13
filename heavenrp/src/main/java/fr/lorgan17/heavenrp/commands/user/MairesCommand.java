package fr.lorgan17.heavenrp.commands.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.heavencraft.HeavenCommand;
import fr.heavencraft.Utils;
import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.heavenrp.HeavenRP;

public class MairesCommand extends HeavenCommand {

	public MairesCommand()
	{
		super("maires");
	}

	@Override
	protected void onPlayerCommand(Player player, String[] args) throws HeavenException
	{
		onConsoleCommand(player, args);
	}

	@Override
	protected void onConsoleCommand(CommandSender sender, String[] args) throws HeavenException
	{
		try
		{
			PreparedStatement ps = HeavenRP.getConnection().prepareStatement("SELECT u.name, GROUP_CONCAT(DISTINCT m.region_name ORDER BY m.region_name DESC SEPARATOR ', ') AS villes FROM mayors m, users u WHERE u.id = m.user_id GROUP BY m.user_id");
			ResultSet rs = ps.executeQuery();
			
			if (rs.first())
			{
				Utils.sendMessage(sender, "Liste des maires connectés :");
				
				do
				{
					if (Bukkit.getPlayer(rs.getString(1)) != null)
						Utils.sendMessage(sender, "- " + rs.getString(1) + " ({" + rs.getString(2) + "})");
				}
				while (rs.next());
			}
			else
			{
				Utils.sendMessage(sender, "Aucun maire n'est connecté");
			}
			
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	protected void sendUsage(CommandSender sender)
	{
	}
}