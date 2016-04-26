package io.log.extension.agent.core.handler;

import io.log.extension.agent.core.entity.Staticstics;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;

public class StorageConcurrentMap {
	private static StorageConcurrentMap storage = new StorageConcurrentMap();
	private Set<String> blanks = new ConcurrentHashSet<String>();
	private Map<Class<?>, Set<String>> root = new ConcurrentHashMap<Class<?>, Set<String>>();
	private Map<Integer, Staticstics> staticstics = new ConcurrentHashMap<Integer, Staticstics>();

	private StorageConcurrentMap() {

	}

	public Set<String> getBlanks() {
		return blanks;
	}

	public void setBlanks(Set<String> blanks) {
		this.blanks = blanks;
	}

	public Boolean isContains(Class<?> classType, String method) {
		boolean hasClass = root.containsKey(classType);
		if (!hasClass) {
			return false;
		}

		Set<String> methods = root.get(classType);
		if (methods.contains(method)) {
			return true;
		}

		return false;
	}

	public void remove(Integer time) {
		staticstics.remove(time);
	}

	public static StorageConcurrentMap getInstance() {
		return storage;
	}

}
