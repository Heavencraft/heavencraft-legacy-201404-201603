package fr.lorgan17.lorganserver.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.mysql.jdbc.Statement;

import fr.heavencraft.exceptions.HeavenException;
import fr.lorgan17.lorganserver.LorganServer;
import fr.lorgan17.lorganserver.exceptions.MemberNotFoundException;
import fr.lorgan17.lorganserver.exceptions.RegionNotFoundException;

public class Region
{
	private final int _id;
	private final int _x1;
	private final int _z1;
	private final int _x2;
	private final int _z2;

	private Region(int id, int x1, int z1, int x2, int z2)
	{
		_id = id;
		_x1 = x1;
		_z1 = z1;
		_x2 = x2;
		_z2 = z2;
	}

	public int getId()
	{
		return _id;
	}

	public void delete() throws HeavenException
	{
		try
		{
			PreparedStatement ps = LorganServer.getConnection().prepareStatement("DELETE FROM regions WHERE id = ?");
			ps.setInt(1, _id);

			if (ps.executeUpdate() == 0)
				throw new HeavenException("Oups, la suppression a échouée");

		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public void addMember(String name, boolean owner) throws HeavenException
	{
		User user = User.getUserByName(name);

		try
		{
			PreparedStatement ps = LorganServer.getConnection().prepareStatement(
					"INSERT INTO regions_users (region_id, user_id, owner) VALUES (?, ?, ?);");
			ps.setInt(1, _id);
			ps.setInt(2, user.getId());
			ps.setBoolean(3, owner);

			ps.executeUpdate();
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new HeavenException("Le joueur {" + name + "} est déjà membre de la protection {" + _id + "}.");
		}
	}

	public boolean isMember(String name, boolean owner)
	{
		for (OfflinePlayer player : Bukkit.getOperators())
			if (player.getName().equals(name))
				return true;

		try
		{
			PreparedStatement ps = LorganServer
					.getConnection()
					.prepareStatement(
							"SELECT owner FROM regions_users ru, users u WHERE ru.region_id = ? AND ru.user_id = u.id AND u.name = ? LIMIT 1");
			ps.setInt(1, _id);
			ps.setString(2, name);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				return false;

			return owner ? rs.getBoolean(1) : true;
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public void removeMember(String name) throws HeavenException
	{
		try
		{
			PreparedStatement ps = LorganServer
					.getConnection()
					.prepareStatement(
							"DELETE FROM regions_users WHERE region_id = ? AND user_id = (SELECT id FROM users WHERE name = ?) AND owner = 0");
			ps.setInt(1, _id);
			ps.setString(2, name);

			if (ps.executeUpdate() == 0)
				throw new MemberNotFoundException(_id, name);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public boolean canBuild(String name) throws HeavenException
	{
		return isMember(name, false);
	}

	public static void createRegion(String owner, int x1, int z1, int x2, int z2) throws HeavenException
	{
		int tmp;

		if (x2 < x1)
		{
			tmp = x1;
			x1 = x2;
			x2 = tmp;
		}

		if (z2 < z1)
		{
			tmp = z1;
			z1 = z2;
			z2 = tmp;
		}

		try
		{
			PreparedStatement ps = LorganServer.getConnection().prepareStatement(
					"SELECT id FROM regions WHERE " + "(? BETWEEN x1 AND x2 AND ? BETWEEN z1 AND z2) OR "
							+ "(? BETWEEN x1 AND x2 AND ? BETWEEN z1 AND z2) OR "
							+ "(x1 BETWEEN ? AND ? AND z1 BETWEEN ? AND ?) OR "
							+ "(x2 BETWEEN ? AND ? AND z2 BETWEEN ? AND ?)");
			ps.setInt(1, x1);
			ps.setInt(2, z1);
			ps.setInt(3, x2);
			ps.setInt(4, z2);
			ps.setInt(5, x1);
			ps.setInt(6, x2);
			ps.setInt(7, z1);
			ps.setInt(8, z2);
			ps.setInt(9, x1);
			ps.setInt(10, x2);
			ps.setInt(11, z1);
			ps.setInt(12, z2);

			ResultSet rs = ps.executeQuery();

			if (rs.next())
				throw new HeavenException("Une protection existe déjà ici.");

			ps = LorganServer.getConnection().prepareStatement(
					"INSERT INTO regions (x1, z1, x2, z2) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, x1);
			ps.setInt(2, z1);
			ps.setInt(3, x2);
			ps.setInt(4, z2);

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			rs.next();

			Region region = getRegionById(rs.getInt(1));
			region.addMember(owner, true);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new HeavenException("La création de la protection a échoué.");
		}
	}

	public static Region getRegionById(int id) throws HeavenException
	{
		try
		{
			PreparedStatement ps = LorganServer.getConnection().prepareStatement(
					"SELECT r.x1, r.z1, r.x2, r.z2 FROM regions r WHERE r.id = ? LIMIT 1");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new RegionNotFoundException(id);

			return new Region(id, rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new RegionNotFoundException(id);
		}
	}

	public static Region getRegionByLocation(int x, int z)
	{
		try
		{
			PreparedStatement ps = LorganServer
					.getConnection()
					.prepareStatement(
							"SELECT r.id, r.x1, r.z1, r.x2, r.z2 FROM regions r WHERE ? BETWEEN r.x1 AND r.x2 AND ? BETWEEN r.z1 AND r.z2 LIMIT 1");
			ps.setInt(1, x);
			ps.setInt(2, z);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				return null;

			return new Region(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public static boolean canBuildAt(String name, int x, int z) throws HeavenException
	{
		Region region = getRegionByLocation(x, z);

		return region == null ? true : region.canBuild(name);
	}

}
