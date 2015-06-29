package fr.heavencraft.heavenproxy.jit;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.ServerSocket;

import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
public class SystemHelper
{
	private static final OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory
			.getOperatingSystemMXBean();

	public static long getFreeMemoryMb()
	{
		return operatingSystemMXBean.getFreePhysicalMemorySize() >> 20;
	}

	public static boolean isPortAvailable(int port)
	{
		try (ServerSocket ss = new ServerSocket(port))
		{
			ss.setReuseAddress(true);
			return true;
		}
		catch (final IOException e)
		{
			return false;
		}
	}
}