package io.log.extension.agent.core.config;

import org.junit.Test;

public class PropertiesConfigTest {

	@Test
	public void testGetDomain() {
		String domain = PropertiesConfig.getDomain();
		System.out.println(domain);
	}
	
	@Test
	public void testGetHost() {
		String host = PropertiesConfig.getHost();
		System.out.println(host);
	}
}
