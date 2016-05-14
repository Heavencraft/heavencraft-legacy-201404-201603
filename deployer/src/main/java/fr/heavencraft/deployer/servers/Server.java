package fr.heavencraft.deployer.servers;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import fr.heavencraft.deployer.utils.FilesUtil;
import fr.heavencraft.deployer.utils.StaticFilesUtil;

public class Server
{
	private static final File SERVERS_DIR = new File("servers");

	private String server;
	private Map<String, String> files;
	private Map<String, String> staticFiles;

	public void deploy(File serverDir, Properties serverConf)
	{
		// Copy plugin's jar
		final File sourceJar = new File(SERVERS_DIR, server);
		final File destJar = new File(serverDir, server);
		StaticFilesUtil.copyFile(sourceJar, destJar);
		serverConf.put("server.jar", server);

		FilesUtil.copyFiles(files, serverDir, serverConf);
		StaticFilesUtil.copyStaticFiles(staticFiles, serverDir);
	}
}