package fr.tenkei.creaplugin.managers.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.mysql.jdbc.Statement;

import fr.heavencraft.exceptions.HeavenException;
import fr.heavencraft.utils.ChatUtil;
import fr.tenkei.creaplugin.exceptions.MemberNotFoundException;
import fr.tenkei.creaplugin.exceptions.RegionNotFoundException;
import fr.tenkei.creaplugin.managers.WorldsManager;
import fr.tenkei.creaplugin.utils.ConnectionManager;

public class Region
{

	private final int _id;

	private final int _x1;
	private final int _y1;
	private final int _z1;

	private final int _x2;
	private final int _y2;
	private final int _z2;

	private final World _world;

	private Region(int id, int x1, int y1, int z1, int x2, int y2, int z2, World w)
	{
		_id = id;

		_x1 = x1;
		_y1 = y1;
		_z1 = z1;

		_x2 = x2;
		_y2 = y2;
		_z2 = z2;

		_world = w;
	}

	public int getPrize()
	{
		if (_x1 - _x2 == 175 || _x1 - _x2 == -175)
			return 10000;

		if (_x1 - _x2 == 79 || _x1 - _x2 == -79)
			return 2500;

		if (_x1 - _x2 == 31 || _x1 - _x2 == -31)
			return 500;

		return 1337;
	}

	public int getId()
	{
		return _id;
	}

	public World getWorld()
	{
		return _world;
	}

	public void delete() throws HeavenException
	{
		try
		{
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(
					"DELETE FROM regions WHERE id = ?");
			ps.setInt(1, _id);

			if (ps.executeUpdate() == 0)
				throw new HeavenException("Oups, la suppression a échouée");

		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public void addMember(User user, boolean owner) throws HeavenException
	{

		try
		{
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(
					"INSERT INTO regions_users (region_id, user_id, owner) VALUES (?, ?, ?);");
			ps.setInt(1, _id);
			ps.setInt(2, user.getId());
			ps.setBoolean(3, owner);

			ps.executeUpdate();
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new HeavenException("Le joueur {" + user.getName() + "} est déjà membre de la protection {" + _id
					+ "}.");
		}
	}

	@SuppressWarnings("deprecation")
	public void carre()
	{
		if (_x1 - _x2 < 0)
		{
			for (int i = _x1; i < _x2; i++)
			{
				new Location(WorldsManager.getTheCreative(), i, 51, _z1).getBlock().setTypeId(152);
				new Location(WorldsManager.getTheCreative(), i, 51, _z2).getBlock().setTypeId(152);
			}
		}
		else
		{
			ChatUtil.broadcastMessage("Protection inversée");
		}

		if (_z1 - _z2 < 0)
		{
			for (int j = _z1; j <= _z2; j++)
			{
				new Location(WorldsManager.getTheCreative(), _x1, 51, j).getBlock().setTypeId(152);
				new Location(WorldsManager.getTheCreative(), _x2, 51, j).getBlock().setTypeId(152);
			}
		}
		else
		{
			ChatUtil.broadcastMessage("Protection inversée");
		}
	}

	public boolean isRealMember(String name)
	{
		try
		{
			PreparedStatement ps = ConnectionManager
					.getConnection()
					.prepareStatement(
							"SELECT owner FROM regions_users ru, users u WHERE ru.region_id = ? AND ru.user_id = u.id AND u.name = ? LIMIT 1");
			ps.setInt(1, _id);
			ps.setString(2, name);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				return false;

			return true;
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public boolean isMember(String name, boolean owner)
	{
		try
		{
			PreparedStatement ps = ConnectionManager
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
			PreparedStatement ps = ConnectionManager
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

	public static void createRegion(User owner, int x1, int y1, int z1, int x2, int y2, int z2, World world)
			throws HeavenException
	{
		int tmp;

		if (x2 < x1)
		{
			tmp = x1;
			x1 = x2;
			x2 = tmp;
		}

		if (y2 < y1)
		{
			tmp = y1;
			y1 = y2;
			y2 = tmp;
		}

		if (z2 < z1)
		{
			tmp = z1;
			z1 = z2;
			z2 = tmp;
		}

		try
		{
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(
					"SELECT id FROM regions WHERE " + "(? LIKE world AND " + x1 + " BETWEEN x1 AND x2 AND " + z1
							+ " BETWEEN z1 AND z2 AND " + y1 + " BETWEEN y1 AND y2) OR " + "(? LIKE world AND " + x2
							+ " BETWEEN x1 AND x2 AND " + z2 + " BETWEEN z1 AND z2 AND " + y2
							+ " BETWEEN y1 AND y2) OR " + "(? LIKE world AND x1 BETWEEN " + x1 + " AND " + x2
							+ " AND y1 BETWEEN " + y1 + " AND " + y2 + " AND z1 BETWEEN " + z1 + " AND " + z2 + ") OR "
							+ "(? LIKE world AND x2 BETWEEN " + x1 + " AND " + x2 + " AND y2 BETWEEN " + y1 + " AND "
							+ y2 + " AND z2 BETWEEN " + z1 + " AND " + z2 + ")");
			ps.setString(1, world.getName());
			ps.setString(2, world.getName());
			ps.setString(3, world.getName());
			ps.setString(4, world.getName());

			ResultSet rs = ps.executeQuery();

			if (rs.next())
				throw new HeavenException("Une protection existe déjà ici.");

			ps = ConnectionManager.getConnection().prepareStatement(
					"INSERT INTO regions (x1, y1, z1, x2, y2, z2, world) VALUES (?, ?, ?, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, x1);
			ps.setInt(2, y1);
			ps.setInt(3, z1);
			ps.setInt(4, x2);
			ps.setInt(5, y2);
			ps.setInt(6, z2);
			ps.setString(7, world.getName());

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
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(
					"SELECT r.x1, r.y1, r.z1, r.x2, r.y2, r.z2, r.world FROM regions r WHERE r.id = ? LIMIT 1");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				throw new RegionNotFoundException(id);

			return new Region(id, rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6),
					Bukkit.getWorld(rs.getString(7)));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			throw new RegionNotFoundException(id);
		}
	}

	public static Region getRegionByLocation(int x, int y, int z, World world)
	{
		try
		{
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(
					"SELECT r.id, r.x1, r.y1, r.z1, r.x2, r.y2, r.z2, r.world, r.public FROM regions r WHERE ? LIKE r.world AND"
							+ " ? BETWEEN r.x1 AND r.x2 AND" + " ? BETWEEN r.y1 AND r.y2 AND"
							+ " ? BETWEEN r.z1 AND r.z2 LIMIT 1");
			ps.setString(1, world.getName());
			ps.setInt(2, x);
			ps.setInt(3, y);
			ps.setInt(4, z);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				return null;

			return new Region(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6),
					rs.getInt(7), Bukkit.getWorld(rs.getString(8)));
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public static boolean canBuildAt(String name, int x, int y, int z, World w) throws HeavenException
	{
		Region region = getRegionByLocation(x, y, z, w);

		return region == null ? true : region.canBuild(name);
	}

	public String getProprio()
	{
		try
		{
			PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(
					"SELECT `user_id` FROM `regions_users` WHERE `region_id` = ? AND `owner` = 1");
			ps.setInt(1, _id);

			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				return "Introuvable";

			PreparedStatement ps2 = ConnectionManager.getConnection().prepareStatement(
					"SELECT `name` FROM `users` WHERE `id` = ?");
			ps2.setInt(1, rs.getInt(1));

			ResultSet rs2 = ps2.executeQuery();

			if (!rs2.next())
				return "u_u";

			return rs2.getString(1);
		}

		catch (SQLException ex)
		{
			ex.printStackTrace();
			return "";
		}
	}

	/***
	 * Le bool permet d'avoir les deux positions de la région a : TRUE retourne la position 1 a : FALSE retourne la
	 * position 2
	 ***/
	public Location getLocation(boolean a)
	{
		if (a)
			return new Location(_world, _x1, _y1, _z1);
		else
			return new Location(_world, _x2, _y2, _z2);
	}
}
