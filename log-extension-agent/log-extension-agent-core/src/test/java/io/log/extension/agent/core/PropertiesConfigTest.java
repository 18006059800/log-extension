package io.log.extension.agent.core;

import io.log.extension.agent.core.config.PropertiesConfig;

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
