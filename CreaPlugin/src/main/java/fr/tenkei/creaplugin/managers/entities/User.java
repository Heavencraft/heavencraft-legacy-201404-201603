package fr.tenkei.creaplugin.managers.entities;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;

import fr.tenkei.creaplugin.MyPlugin;
import fr.tenkei.creaplugin.exceptions.MyException;
import fr.tenkei.creaplugin.exceptions.PlayerNoJetonRequired;
import fr.tenkei.creaplugin.exceptions.PlayerNotHaveHomeNumber;
import fr.tenkei.creaplugin.utils.ConnectionManager;
import fr.tenkei.creaplugin.utils.Message;
import fr.tenkei.creaplugin.utils.Stuff;

public class User {
	
	private int _id;
	private String _name;
	
	private int _jeton;
	
	private int  _nbHome;
	private String _savedDate;
	
	private String _requestName;
	private Block _lastInteractBlock;
	
	private HashMap<String, String> _stringVariables;
	
	public User(int id, String name, int jetons, int nbHome, String savedDate, String varStr) {
		_id = id;
		_name = name;
		_jeton = jetons;
		_nbHome = nbHome;
		_savedDate = savedDate;
		
		_stringVariables = new HashMap<String, String>();
		
		loadVarStr(varStr);
	}

	public boolean shouldBeNewDay()
	{
		String currentDate = Stuff.getDateTime();
		MyPlugin.log(currentDate + " vs " + _savedDate);
		return !currentDate.equalsIgnoreCase(_savedDate);
	}
	
	public void setStringVariable(String variable, String value)
	{
		if (_stringVariables.containsKey(variable))
			_stringVariables.remove(variable);
		_stringVariables.put(variable, value);
	}

	public String getStringVariable(String variable)
	{
		if (_stringVariables.containsKey(variable))
			return _stringVariables.get(variable);
		return "";
	}

	public void setInteractBlock(Block block)
	{
		_lastInteractBlock = block;
	}
	
	public Block getInteractBlock()
	{
		return _lastInteractBlock;
	}
	
	public int getId()
	{
		return _id;
	}

	public String getName()
	{
		return _name;
	}
	
	public int getJeton()
	{
		return _jeton;
	}

	public void saveUser()
	{	
		String varString = "";
		for (Entry<String, String> element : _stringVariables.entrySet())
		{
			varString += element.getKey() + "=" + element.getValue() + "\n";
		}
		
		try
		{
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement("UPDATE users SET jetons = ?, nbHome = ?, savedDate = ?, varString = ? WHERE id = ?");
			ps.setInt(1, _jeton);

			ps.setInt(2, _nbHome);
			ps.setString(3, _savedDate);
			ps.setString(4, varString);
			
			ps.setInt(5, _id);

			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void loadVarStr(String varString)
	{
		String[] listString = varString.split("\n");
		
		for(int i = 0 ; i<listString.length ; i++)
		{
			String[] lineData = listString[i].split("=");
			if (lineData.length == 2)
			{
				String variable = lineData[0].trim();
				String value = lineData[1].trim();
				_stringVariables.put(variable, value);
			}
		}	
	}
	
	public int updateBalance(int delta) throws MyException
	{
		if (_jeton < 0)
			throw new PlayerNoJetonRequired(-delta);
		if (_jeton + delta < 0)
			throw new PlayerNoJetonRequired(-delta);
		
		_jeton += delta;
		setBalance(_jeton);
		return _jeton;
	}
	
	public void stateBalance()
	{
		Message.sendMessage(Bukkit.getPlayer(_name), "Vous avez maintenant {" + _jeton + "} Jetons.");
	}
	
	public boolean setBalance(int value)
	{
		try
		{
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement("UPDATE users SET jetons = ? WHERE id = ?");
			ps.setInt(1, value);
			ps.setInt(2, _id);
			ps.executeUpdate();
			ps.close();
			return true;
		}
		catch (SQLException e)
		{
			
		}
		return false;
	}
	
	public void onLogin() throws MyException
	{
		String currentDate = Stuff.getDateTime();

		if (shouldBeNewDay())
		{
			Message.sendMessage(Bukkit.getPlayer(this._name), ChatColor.AQUA + "Vous venez d'obtenir {100 Jetons" 
					+ ChatColor.AQUA + " en vous connectant !");
			this.updateBalance(100);
			_savedDate = currentDate;
		}
	}

	public void setTeleportRequestName(String name) {
		this._requestName = name;
	}

	public String getTeleportRequestName() {
		return this._requestName;
	}

	public boolean setHome(int number, Location loc)
	{
		this.setStringVariable("home_" + number, Stuff.locationToString(loc));
		return true;
	}

	public boolean hasHome(int number)
	{
		return !this.getStringVariable("home_" + number).isEmpty();
	}

	public Location getHome(int number) throws PlayerNotHaveHomeNumber
	{
		if(number > _nbHome)
			throw new PlayerNotHaveHomeNumber(number);

		return Stuff.stringToLocation(this.getStringVariable("home_" + number));
	}
	
	public int getHomeNumbre()
	{
		return _nbHome;
	}
	
	/*** How to buy home ? C'est ici. Prix donné par : prix = (2^nbHomeToBuy * 500)/4
	 * @throws MyException */
	public void addHommeNumbre(Block block) throws MyException
	{
		int prix = (int) (Math.pow(2.0, _nbHome+1) * 500)/4;
		
	    if ((_lastInteractBlock == null) || !Stuff.blocksEquals(_lastInteractBlock, block)) {
	      setInteractBlock(block);
	      int homeNombre = _nbHome + 1;
	      Message.sendMessage(Bukkit.getPlayer(_name), "Cliquez une seconde fois pour confirmer l'achat du home "+ homeNombre +" pour {" + prix +"} Jetons.");
	      return;
	    }

	    setInteractBlock(null);

		updateBalance(-prix);
		
		_nbHome++;
		
		Message.sendMessage(Bukkit.getPlayer(_name), "Vous êtes maintenant l'heureux propriétaire de "+ _nbHome +" homes , cela vous a couté {" + prix + "} Jetons !");
		stateBalance();
	}
}
