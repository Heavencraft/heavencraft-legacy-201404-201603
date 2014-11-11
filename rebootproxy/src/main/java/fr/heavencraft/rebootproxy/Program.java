package fr.heavencraft.rebootproxy;

import java.util.Date;
import java.util.Scanner;

import ch.jamiete.mcping.MinecraftPing;

public class Program implements Runnable
{
	// private static final String PROXY_HOST = "heavencraft.fr";
	private static final String PROXY_HOST = "127.0.0.1";
	private static final String START_SCRIPT = "./start";
	private static final String STOP_SCRIPT = "./stop";

	public static void main(String[] args)
	{
		System.out.println(new Date() + " Starting RebootProxy 1.0 by lorgan17");
		System.out.println(new Date() + " Proxy to watch : " + PROXY_HOST + ":25565");
		System.out.println(new Date() + " Start script : " + START_SCRIPT);
		System.out.println(new Date() + " Type 'stop' to stop this program");

		Thread thread = new Thread(new Program());
		thread.start();

		Scanner keyboard = new Scanner(System.in);

		while (!keyboard.nextLine().equals("stop"))
			safeSleep(1);

		System.out.println(new Date() + " Stopping RebootProxy 1.0");

		thread.interrupt();
	}

	public void run()
	{
		try
		{
			while (true)
			{
				Thread.sleep(10000);

				if (isProxyDown())
				{
					System.out.println(new Date() + " ***** PROXY IS DOWN *****");
					relaunchProxy();

					Thread.sleep(30000);
				}
			}
		}
		catch (InterruptedException ex)
		{
			ex.printStackTrace();
		}
	}

	private static boolean isProxyDown()
	{
		try
		{
			new MinecraftPing().getPing(PROXY_HOST);
			return false;
		}
		catch (final Throwable t)
		{
			System.out.println(t.getMessage());
			return true;
		}
	}

	private static void relaunchProxy()
	{
		try
		{
			Runtime.getRuntime().exec(STOP_SCRIPT);
			safeSleep(1);
			Runtime.getRuntime().exec(START_SCRIPT);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private static void safeSleep(long sec)
	{
		try
		{
			Thread.sleep(sec * 1000);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}