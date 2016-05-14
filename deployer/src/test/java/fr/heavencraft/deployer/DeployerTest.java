package fr.heavencraft.deployer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import fr.heavencraft.deployer.utils.PropertiesUtil;
import junit.framework.TestCase;

public class DeployerTest extends TestCase
{
	private final ByteArrayOutputStream sysout = new ByteArrayOutputStream();
	private final ByteArrayOutputStream syserr = new ByteArrayOutputStream();

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		System.setOut(new PrintStream(sysout));
		System.setErr(new PrintStream(syserr));
	}

	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
		System.setOut(null);
		System.setErr(null);
	}

	public void testReplaceProperties()
	{
		final String content = "aaa: ${aaa}; bbb: ${bbb}; ccc: ${ccc};";
		final Properties properties = new Properties();
		properties.put("aaa", "A");
		properties.put("bbb", "B");
		properties.put("ccc", "C");

		assertEquals("aaa: A; bbb: B; ccc: C;", PropertiesUtil.replaceProperties(content, properties));
	}

	public void testReplacePropertiesChain()
	{
		final String content = "aaa: ${aaa}; bbb: ${bbb}; ccc: ${ccc};";
		final Properties properties = new Properties();
		properties.put("aaa", "${bbb}");
		properties.put("bbb", "${ccc}");
		properties.put("ccc", "C");

		assertEquals("aaa: C; bbb: C; ccc: C;", PropertiesUtil.replaceProperties(content, properties));
	}

	public void testReplacePropertiesNotFound()
	{
		final String content = "aaa: ${aaa}; bbb: ${bbb}; ccc: ${ccc};";
		final Properties properties = new Properties();
		properties.put("aaa", "A");
		properties.put("ccc", "C");

		assertEquals("aaa: A; bbb: ${bbb}; ccc: C;", PropertiesUtil.replaceProperties(content, properties));
		assertEquals("WARNING: property not found: bbb" + System.lineSeparator(), syserr.toString());
	}
}