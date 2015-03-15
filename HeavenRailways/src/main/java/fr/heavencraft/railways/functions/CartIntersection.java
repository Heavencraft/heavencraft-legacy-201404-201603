package fr.heavencraft.railways.functions;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.material.Rails;

import fr.heavencraft.Utils.ChatUtil;
import fr.heavencraft.railways.PlayerStationManager;

public class CartIntersection implements RailwayFunction{

	private final static String STATION_FORMAT_ERR = "[SHCF] TOKEN INVALID st:<nom_station>:<Destination: N/W/S/E>";
	private final static String DIR_FORMAT_ERR = "[SHCF] TOKEN INVALID dir:<Origine: N/W/S/E>:<Destination: N/W/S/E>";

	@Override
	public
	Material ControlBlockMaterial() {
		return Material.BRICK;
	}

	@Override
	public ArrayList<String> OpCodes() {
		ArrayList<String> codes = new ArrayList<String>();
		codes.add("st:<station>:<N/W/S/E>");
		codes.add("dir:<N/W/S/E>:<N/W/S/E>");
		codes.add("all:<N/W/S/E>");
		return codes;
	}

	@Override
	public void handleFunction(Vehicle cart) {
		Location RailLocation = cart.getLocation();
		Location ParameterSignLocation = cart.getLocation();

		RailLocation.setY(Math.floor(RailLocation.getY()));
		ParameterSignLocation.setY(Math.floor(RailLocation.getY()) - 2.0D); // Just below the control block.

		// No parameters found
		if(ParameterSignLocation.getBlock() == null)
			return;
		if (ParameterSignLocation.getBlock().getType() != Material.WALL_SIGN && ParameterSignLocation.getBlock().getType() != Material.SIGN && ParameterSignLocation.getBlock().getType() != Material.SIGN_POST)
			return;
		
		String cartDirection = getDirection(cart.getLocation().getYaw());

		// Prepare data about intersection
		Block rail = RailLocation.getBlock();
		Material railMaterial = rail.getType();	
		byte oldData = rail.getData();
		Rails r = (Rails)railMaterial.getNewData(oldData);	
		if(r == null)
			return;
		BlockState paramBlck = ParameterSignLocation.getBlock().getState();
		Sign paramsSign = (Sign) paramBlck;
		Player passager = (Player)cart.getPassenger();

		// For each line on the sign.
		for(int i = 0; i<4; i++) {
			// Empty line.
			if(paramsSign.getLine(i) == null || paramsSign.getLine(i).equalsIgnoreCase(""))
				continue;
			
			// Separate into different parts.
			String[] token = paramsSign.getLine(i).split(":");
			if(token.length == 0)
				continue;
			
			// Search for the OPCODE.
			switch(token[0].toLowerCase()){
			
			/**
			 * st:<station>:<N/W/S/E>
			 */
			case "st":
				if(passager == null)
					continue;
				if(token.length != 3)
				{
					ChatUtil.sendMessage(passager, STATION_FORMAT_ERR);
					continue;
				}
				String destination = PlayerStationManager.getUserStation(passager.getUniqueId());
				if(destination == null)
					continue;// No destination found
				if(token[1].equalsIgnoreCase(destination)){
					// Update rails orientation
					passager.sendMessage("to:" + token[2] + " ");
					r.setDirection(getBlockFacing(token[2]), r.isOnSlope());
					byte newdata = r.getData();
					rail.setData(newdata);
				}
				else
					continue;
				break;

				/**
				 * dir:<N/W/S/E>:<N/W/S/E>
				 */
			case "dir":
				if(token.length != 3)
				{
					if(passager != null)
						ChatUtil.sendMessage(passager, DIR_FORMAT_ERR);
					continue;
				}
				if(token[1].equalsIgnoreCase(cartDirection)){
					// Update rails orientation
					passager.sendMessage("to:" + token[2] + " ");
					r.setDirection(getBlockFacing(token[2]), r.isOnSlope());
					byte newdata = r.getData();
					rail.setData(newdata);
				}
				else
					continue;
				
				/**
				 * all:<N/W/S/E>
				 */
			case "all":
				if(token.length != 2)
				{
					if(passager != null)
						ChatUtil.sendMessage(passager, DIR_FORMAT_ERR);
					continue;
				}			
				// Update rails orientation
				r.setDirection(getBlockFacing(token[1]), r.isOnSlope());
				byte newdata = r.getData();
				rail.setData(newdata);
			default:

				break;
			}
		}
	}

	public String getDirection(float yaw) { 
		yaw = (yaw - 90) % 360; 
		if (yaw < 0.0F) { 
			yaw += 360.0F; 
		} 
		if ((yaw >= 0.0F) && (yaw < 45.0F)) { 
			return "N"; 
		} 
		if ((yaw >= 45.0F) && (yaw < 135.0F)) { 
			return "E"; 
		} 
		if ((yaw >= 135.0F) && (yaw < 225.0F)) { 
			return "S"; 
		} 
		if ((yaw >= 225.0F) && (yaw < 315.0F)) { 
			return "W"; 
		} 
		if ((yaw >= 315.0F) && (yaw < 360.0F)) { 
			return "N"; 
		} 
		return null; 
	}

	public BlockFace getBlockFacing(String direction) {
		BlockFace targetDirection = null;
		switch(direction.toUpperCase())
		{
		case "N":
			targetDirection = BlockFace.NORTH;
			break;
		case "E":
			targetDirection = BlockFace.EAST;
			break;
		case "S":
			targetDirection = BlockFace.SOUTH;
			break;
		case "W":
			targetDirection = BlockFace.WEST;
			break;
		default:
			break;
		}
		return targetDirection;
	}


}
