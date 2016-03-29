package io.log.extension.agent.container.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring Application Aware Util
 * 
 * @author percy
 * 
 */
public class SpringApplicationContextAware implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringApplicationContextAware.applicationContext = applicationContext;
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		T bean = applicationContext.getBean(name, requiredType);
		return bean;
	}

	public static Object getBean(String name) {
		Object bean = applicationContext.getBean(name);
		return bean;
	}

	public static <T> T getBean(Class<T> requiredType) {
		T bean = applicationContext.getBean(requiredType);
		return bean;
	}

}
