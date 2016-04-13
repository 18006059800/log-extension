package io.log.extension.agent.core.handler;

import io.log.extension.agent.core.entity.Staticstics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StorageConcurrentMap {
	private Map<Long, Staticstics> staticstics = new ConcurrentHashMap<Long, Staticstics>();
	private static StorageConcurrentMap storage = new StorageConcurrentMap();

	private StorageConcurrentMap() {

	}

	public void remove (Long time) {
		staticstics.remove(time);
	}
	
	public static StorageConcurrentMap getInstance() {
		return storage;
	}

}
