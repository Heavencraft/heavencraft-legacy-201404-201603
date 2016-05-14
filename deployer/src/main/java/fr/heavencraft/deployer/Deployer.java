package fr.heavencraft.deployer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Properties;

import fr.heavencraft.deployer.plugins.Plugin;
import fr.heavencraft.deployer.plugins.PluginsRepository;
import fr.heavencraft.deployer.recipes.Recipe;
import fr.heavencraft.deployer.recipes.RecipesRepository;
import fr.heavencraft.deployer.servers.Server;
import fr.heavencraft.deployer.servers.ServersRepository;
import fr.heavencraft.deployer.utils.ConfigUtil;
import fr.heavencraft.deployer.utils.FilesUtil;
import fr.heavencraft.deployer.utils.PropertiesUtil;
import fr.heavencraft.deployer.utils.StaticFilesUtil;

public class Deployer
{
	private static final File DEFAULT_CONFIG_FILE = new File("cfg/default.cfg");

	public static void main(String[] args) throws FileNotFoundException
	{
		// Loading configurations
		final Properties defaultConf = ConfigUtil.loadPropertiesFile(DEFAULT_CONFIG_FILE);
		final RecipesRepository recipesRepository = new RecipesRepository();
		final ServersRepository serversRepository = new ServersRepository();
		final PluginsRepository pluginsRepository = new PluginsRepository();

		for (final Recipe recipe : recipesRepository.getRecipes())
		{
			final Server server = serversRepository.getServerByName(recipe.getServerName());
			final Collection<Plugin> plugins = pluginsRepository.getPluginsByNames(recipe.getPluginsNames());

			for (final String configFile : recipe.getApplyTo())
			{
				final Properties serverConf = ConfigUtil.loadPropertiesFile(configFile, defaultConf);

				/*
				 * Deploy server
				 */

				final File serverDir = new File(PropertiesUtil.getProperty("server.path", serverConf));
				if (!serverDir.exists())
					serverDir.mkdirs();

				server.deploy(serverDir, serverConf);

				/*
				 * Deploy plugins
				 */

				final File pluginsDir = new File(serverDir, "plugins");
				if (!pluginsDir.exists())
					pluginsDir.mkdirs();

				for (final Plugin plugin : plugins)
					plugin.deploy(serverDir, serverConf);

				/*
				 * Deploy files and static files
				 */

				FilesUtil.copyFiles(recipe.getFiles(), serverDir, serverConf);
				StaticFilesUtil.copyStaticFiles(recipe.getStaticFiles(), serverDir);
			}
		}
	}
}