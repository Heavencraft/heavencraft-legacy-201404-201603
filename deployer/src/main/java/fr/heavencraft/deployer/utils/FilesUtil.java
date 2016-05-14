package fr.heavencraft.deployer.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class FilesUtil
{
	private static final File FILES_DIR = new File("files");

	public static void copyFiles(Map<String, String> files, File serverDir, Properties serverConf)
	{
		if (files != null && !files.isEmpty())
		{
			for (final Entry<String, String> file : files.entrySet())
			{
				final File sourceFile = new File(FILES_DIR, file.getKey());
				final File destFile = new File(serverDir, file.getValue());

				try
				{
					replacePropertiesAndCopyFile(sourceFile, destFile, serverConf);
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private static void replacePropertiesAndCopyFile(File sourceFile, File destFile, Properties serverConf)
			throws IOException
	{
		if (sourceFile == null || !sourceFile.exists())
		{
			System.err.println("WARNING: source file not found: " + sourceFile);
			return;
		}

		final File destDir = destFile.getParentFile();
		if (destDir != null && !destDir.exists())
			destDir.mkdirs();

		String content = FileUtils.readFileToString(sourceFile);
		content = PropertiesUtil.replaceProperties(content, serverConf);

		if (destFile.exists())
		{
			final String destContent = FileUtils.readFileToString(destFile);

			if (destContent.equals(content))
				return;
		}

		System.out.println("Copying " + sourceFile + " to " + destFile);
		FileUtils.writeStringToFile(destFile, content);
		if (destFile.getName().endsWith(".sh") || sourceFile.getName().endsWith(".sh"))
		{
			final Set<PosixFilePermission> perms = Files.getPosixFilePermissions(destFile.toPath());
			perms.add(PosixFilePermission.OWNER_EXECUTE);
			Files.setPosixFilePermissions(destFile.toPath(), perms);
		}
	}
}