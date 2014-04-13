package fr.heavencraft.almanac;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;


public class AlmanacManager
{
	public final static String HEAVENCRAFT = ChatColor.WHITE + "Heaven" + ChatColor.AQUA + "craft";
	
	private static final String PAGE1 = "      %1$s\n" // Date
			+ "\n"
			+ "Bon anniversaire à :\n"
			+ "%2$s\n" // Nom de la personne
			+ "%3$s\n" // Age ou année
			+ "\n"
			+ "Phrase du jour :\n"
			+ "%4$s"; // Phrase du jour

	private static final String PAGE2 = "      %1$s\n" // Date
			+ "\n"
			+ "Saint du jour :\n"
			+ "%2$s\n" // Fête du jour
			+ "\n"
			+ "Fait historique :\n"
			+ "%3$s"; // Fait historique
	
	private static final String PAGE3 = "      %1$s\n"
			+ "\n"
			+ "%2$s";
	

	private static SimpleDateFormat _dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public static boolean isLocationCorrect(Location location)
	{
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		
		if (x == 232 && y == 66 && z == 113)
			return true;
		
		return false;
	}
	
	private static void setDate(String player)
	{
		AlmanacPlugin.getInstance().getConfig().set("almanac." + player,
				Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
		AlmanacPlugin.getInstance().saveConfig();
	}
	
	public static boolean canGetAlmanac(String player)
	{
		return AlmanacPlugin.getInstance().getConfig().getInt("almanac." + player)
				!= Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
	}

	public static void giveAlmanac(Player player)
	{
		String date  = _dateFormat.format(new Date());
		
		giveAlmanac(player, date);
	}
	
	
	public static void giveAlmanac(Player player, String date)
	{
		try
		{
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
			BookMeta meta = (BookMeta)book.getItemMeta();
			
			PreparedStatement ps = AlmanacPlugin.getConnection().prepareStatement("SELECT * FROM almanac WHERE date = ? LIMIT 1");
			ps.setString(1, date);
			ResultSet rs = ps.executeQuery();
			
			if (!rs.next())
			{
				ps.close();
				player.sendMessage(ChatColor.GOLD + "Il n'y a pas d'Almanach aujourd'hui");
				return;
			}
			
			String name		= rs.getString("name");
			String age		= rs.getString("age");
			String sentence	= rs.getString("sentence");
			String saint	= rs.getString("saint");
			String history	= rs.getString("history");
			String event	= rs.getString("event");
			
			meta.setTitle(ChatColor.GREEN + date);
			meta.setAuthor(HEAVENCRAFT);
			meta.addPage(String.format(PAGE1, date, name, age, sentence));
			meta.addPage(String.format(PAGE2, date, saint, history));
			meta.addPage(String.format(PAGE3, date, event));
			
			book.setItemMeta(meta);
			player.getInventory().addItem(book);
			
			setDate(player.getName());
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}