package fr.heavencraft.railways.functions;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Vehicle;

public interface RailwayFunction {
	public Material ControlBlockMaterial();
	public ArrayList<String> OpCodes();
	public void handleFunction(Vehicle cart);
}
