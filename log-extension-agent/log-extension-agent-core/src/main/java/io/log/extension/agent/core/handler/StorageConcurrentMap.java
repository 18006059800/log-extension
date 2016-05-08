package io.log.extension.agent.core.handler;

import io.log.extension.agent.core.entity.Staticstics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StorageConcurrentMap {
	private static StorageConcurrentMap storage = new StorageConcurrentMap();
	private Map<String, Boolean> root = new ConcurrentHashMap<String, Boolean>();
	private Map<Integer, Staticstics> staticstics = new ConcurrentHashMap<Integer, Staticstics>();

	private StorageConcurrentMap() {

	}

	public Map<String, Boolean> getRoot() {
		return root;
	}

	public void setRoot(Map<String, Boolean> root) {
		this.root = root;
	}

	/**
	 * 添加类名称和方法名
	 * 
	 * @param classNameAndMethodName
	 */
	public void addRootClassMethod(String classNameAndMethodName) {
		root.put(classNameAndMethodName, true);
	}

	public Map<Integer, Staticstics> getStaticstics() {
		return staticstics;
	}

	public void setStaticstics(Map<Integer, Staticstics> staticstics) {
		this.staticstics = staticstics;
	}

	public void remove(Integer time) {
		staticstics.remove(time);
	}

	public static StorageConcurrentMap getInstance() {
		return storage;
	}

}
