package fr.heavencraft.railways.functions;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Vehicle;

public class CartDestroyer implements RailwayFunction{

	@Override
	public Material ControlBlockMaterial() {
		return Material.MOSSY_COBBLESTONE;
	}

	@Override
	public ArrayList<String> OpCodes() {
		ArrayList<String> codes = new ArrayList<String>();
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
		cart.remove();
	}

}
