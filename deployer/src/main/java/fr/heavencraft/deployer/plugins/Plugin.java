package fr.heavencraft.deployer.plugins;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import fr.heavencraft.deployer.utils.FilesUtil;
import fr.heavencraft.deployer.utils.StaticFilesUtil;

public class Plugin
{
	private static final File PLUGINS_DIR = new File("plugins");

	private String plugin;
	private Map<String, String> files;
	private Map<String, String> staticFiles;

	public void deploy(File serverDir, Properties serverConf)
	{
		// Copy plugin's jar
		final File sourceJar = new File(PLUGINS_DIR, plugin);
		final File destJar = new File(serverDir, "plugins/" + plugin);
		StaticFilesUtil.copyFile(sourceJar, destJar);

		FilesUtil.copyFiles(files, serverDir, serverConf);
		StaticFilesUtil.copyStaticFiles(staticFiles, serverDir);
	}
}