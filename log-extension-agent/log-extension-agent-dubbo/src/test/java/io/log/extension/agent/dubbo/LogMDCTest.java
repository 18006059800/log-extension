package io.log.extension.agent.dubbo;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LogMDCTest {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Before
	public void init() {
		MDC.put("aa", "bb");
		MDC.put("cc", "dd");
	}

	@Test
	public void test() {
		log.info("===============");
	}
	
	@Test
	public void testContextMap() {
		log.info("key:{}, value:{}", "aa", MDC.get("aa"));
		
		Map<String, String> prop =MDC.getCopyOfContextMap();
		
		for (String key : prop.keySet()) {
			log.info("key:{}, value:{}", key, prop.get(key));
		}
	}

}
