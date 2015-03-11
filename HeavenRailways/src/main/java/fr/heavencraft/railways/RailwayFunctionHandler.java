package fr.heavencraft.railways;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleUpdateEvent;

import fr.heavencraft.railways.functions.CartDestroyer;
import fr.heavencraft.railways.functions.CartIntersection;
import fr.heavencraft.railways.functions.RailwayFunction;

public class RailwayFunctionHandler implements Listener{

	private List<RailwayFunction> _functions = new ArrayList<RailwayFunction>(); // List that holds our functions.
	
	// https://github.com/FlyingPikachu/SimpleCarts/blob/master/SimpleCarts/src/me/happypikachu/SimpleCarts/listeners/SimpleCartsVehicleListener.java
	public RailwayFunctionHandler()
	{
		loadFunction();
		Bukkit.getPluginManager().registerEvents(this, HeavenRailway.getInstance());
	}
	
	/**
	 * Loads all functions to be accessible.
	 */
	private void loadFunction(){
		_functions.add(new CartIntersection());
		_functions.add(new CartDestroyer());
	}

	/*
	 * The event, that dispatches to functions.
	 */
	@EventHandler
	public void onVehicleUpdate(VehicleUpdateEvent event) {
		
		// Entity checks
		if(!(event.getVehicle() instanceof Minecart))
			return;
		// Deduct locations of parameters relative to the vehicle.
		Location railBlockLoc = event.getVehicle().getLocation();
		railBlockLoc.setY(Math.floor(railBlockLoc.getY()));
		if(railBlockLoc.getBlock().getType().toString().equalsIgnoreCase("air"))
			return;
		Location controlBlock = event.getVehicle().getLocation();	
		controlBlock.setY(Math.floor(railBlockLoc.getY()) - 1.0D); // Just below the cart.
		
		// Dispatch events to associated function.
		for(RailwayFunction r : _functions)
			if(controlBlock.getBlock().getType() == r.ControlBlockMaterial()) 
				r.handleFunction(event.getVehicle());

	}
}
