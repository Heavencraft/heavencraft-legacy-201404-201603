package fr.heavencraft.deployer.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

public class StaticFilesUtil
{
	private static final File STATIC_FILES_DIR = new File("static-files");

	public static void copyStaticFiles(Map<String, String> staticFiles, File serverDir)
	{
		if (staticFiles != null && !staticFiles.isEmpty())
		{
			for (final Entry<String, String> staticFile : staticFiles.entrySet())
			{
				final File sourceFile = new File(STATIC_FILES_DIR, staticFile.getKey());
				final File destFile = new File(serverDir, staticFile.getValue());

				copyFile(sourceFile, destFile);
			}
		}
	}

	public static void copyFile(File sourceFile, File destFile)
	{
		if (sourceFile == null || !sourceFile.exists())
		{
			System.err.println("WARNING: source file not found: " + sourceFile);
			return;
		}

		final File destDir = destFile.getParentFile();
		if (destDir != null && !destDir.exists())
			destDir.mkdirs();

		try
		{
			if (!areSameFile(sourceFile, destFile))
			{
				System.out.println("Copying " + sourceFile + " to " + destFile);
				Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING,
						LinkOption.NOFOLLOW_LINKS);
			}
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	private static boolean areSameFile(File sourceFile, File destFile) throws IOException
	{
		if (!sourceFile.exists() || !destFile.exists())
			return false;

		// Normal files
		if (!Files.isSymbolicLink(sourceFile.toPath()))
			return FileUtils.contentEquals(sourceFile, destFile);

		if (!Files.isSymbolicLink(destFile.toPath()))
			return false;

		return Files.readSymbolicLink(sourceFile.toPath()).equals(Files.readSymbolicLink(destFile.toPath()));
	}
}