package fr.heavencraft.deployer.recipes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RecipesRepository
{
	private final List<Recipe> recipes;

	public RecipesRepository() throws FileNotFoundException
	{
		recipes = loadRecipes();

		System.out.println(recipes.size() + " recipes loaded.");
		System.out.println();
	}

	private static List<Recipe> loadRecipes() throws FileNotFoundException
	{
		final Gson gson = new Gson();
		final BufferedReader br = new BufferedReader(new FileReader("cfg/recipes.json"));
		final Type listType = new TypeToken<ArrayList<Recipe>>()
		{
		}.getType();

		return gson.fromJson(br, listType);
	}

	public List<Recipe> getRecipes()
	{
		return recipes;
	}
}