package io.log.extension.agent.core.handler;

import io.log.extension.agent.core.entity.MessageInfo;
import io.log.extension.agent.core.entity.Staticstics;
import io.log.extension.agent.core.entity.spi.DefaultMessage;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeanUtils;

public class StorageConcurrentMap {
//	private static StorageConcurrentMap storage;
	private static Map<String, Boolean> root = new ConcurrentHashMap<String, Boolean>();
	private static Map<Integer, Staticstics> staticstics = new ConcurrentHashMap<Integer, Staticstics>();

	private StorageConcurrentMap() {

	}

	public static Map<String, Boolean> getRoot() {
		return StorageConcurrentMap.root;
	}

	public static void setRoot(Map<String, Boolean> root) {
		StorageConcurrentMap.root = root;
	}

	/**
	 * 添加类名称和方法名
	 * 
	 * @param classNameAndMethodName
	 */
	public static void addRootClassMethod(String classNameAndMethodName) {
		root.put(classNameAndMethodName, true);
	}

	public static Map<Integer, Staticstics> getStaticstics() {
		return staticstics;
	}

	public static void setStaticstics(Map<Integer, Staticstics> staticstics) {
		StorageConcurrentMap.staticstics = staticstics;
	}

	public static void remove(Integer time) {
		staticstics.remove(time);
	}

//	public static StorageConcurrentMap getInstance() {
//		if (null == storage) {
//			storage = new StorageConcurrentMap();	
//		}
//		return storage;
//	}

	public static void handleMessage(DefaultMessage message) {
		String className = message.getClassName();
		String methodName = message.getClassMethod();
		Boolean hasError = message.getHasError();
		Long time = message.getTime();

		String key = className + "-" + methodName;
		Integer minute = getKey();

		if (!staticstics.containsKey(minute)) {
			Staticstics s = new Staticstics();
			Map<String, MessageInfo> infos = new ConcurrentHashMap<String, MessageInfo>();
			MessageInfo mi = new MessageInfo();
			BeanUtils.copyProperties(message, mi);
			s.getSuccess().getAndIncrement();
			if (hasError) {
				s.getFail().getAndIncrement();
			} else {
				s.getSuccess().getAndIncrement();
			}
			mi.setAvg(time);
			mi.setMax(time);
			mi.setMin(time);
			if (hasError) {
				mi.getFail().getAndIncrement();
			} else {
				mi.getSuccess().getAndIncrement();
			}
			mi.getTotal().getAndIncrement();
			infos.put(key, mi);
			s.setInfos(infos);
			staticstics.put(minute, s);
		} else {
			Staticstics s = staticstics.get(minute);
			s.getSuccess().getAndIncrement();
			if (hasError) {
				s.getFail().getAndIncrement();
			} else {
				s.getSuccess().getAndIncrement();
			}
			Map<String, MessageInfo> infos = s.getInfos();
			if (infos.containsKey(key)) { // 包含
				MessageInfo mi = infos.get(key);
				mi.getTotal().getAndIncrement();
				if (hasError) {
					mi.getFail().getAndIncrement();
				} else {
					mi.getSuccess().getAndIncrement();
				}
				if (time > mi.getMax()) {
					mi.setMax(time);
				}
				if (time < mi.getMin()) {
					mi.setMin(time);
				}
				Long t = time - mi.getAvg();
				double fc = t / mi.getTotal().get();
				Long avg = mi.getAvg() + (long) fc;
				mi.setAvg(avg);
			} else {
				MessageInfo mi = new MessageInfo();
				BeanUtils.copyProperties(message, mi);
				mi.setAvg(time);
				mi.setMax(time);
				mi.setMin(time);
				if (hasError) {
					mi.getFail().getAndIncrement();
				} else {
					mi.getSuccess().getAndIncrement();
				}
				mi.getTotal().getAndIncrement();
				infos.put(key, mi);
			}

		}

	}

	private static Integer getKey() {
		Calendar c = Calendar.getInstance();
		Integer minute = c.get(Calendar.MINUTE);
		return minute;
	}

}
