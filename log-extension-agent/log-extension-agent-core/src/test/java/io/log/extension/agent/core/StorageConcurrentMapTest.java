package io.log.extension.agent.core;

import java.util.Map;

import io.log.extension.agent.core.handler.StorageConcurrentMap;

import org.junit.Test;

public class StorageConcurrentMapTest {

	@Test
	public void test() {
		Map<String, Boolean> root = StorageConcurrentMap.getRoot();
		System.out.println((null == root));
		root.put("-----", true);
	}
}
