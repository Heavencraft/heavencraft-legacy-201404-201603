package fr.heavencraft.deployer;

import java.util.Properties;

import junit.framework.TestCase;

public class DeployerTest extends TestCase
{

	public void testReplaceProperties()
	{
		final String content = "aaa: ${aaa}; bbb: ${bbb}; ccc: ${ccc};";
		final Properties properties = new Properties();
		properties.put("aaa", "A");
		properties.put("bbb", "B");
		properties.put("ccc", "C");

		assertEquals("aaa: A; bbb: B; ccc: C;", Deployer.replaceProperties(content, properties));
	}
}