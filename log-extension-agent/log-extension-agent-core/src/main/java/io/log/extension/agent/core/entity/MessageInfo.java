package io.log.extension.agent.core.entity;

import java.util.concurrent.atomic.AtomicLong;

public class MessageInfo {
	/**
	 * 上下文
	 */
	private String domain;
	/**
	 * 当前节点
	 */
	private String host;
	/**
	 * 类名称
	 */
	private String className;
	/**
	 * 方法名
	 */
	private String classMethod;
	/**
	 * 消息总数
	 */
	private volatile AtomicLong total = new AtomicLong();
	/**
	 * 成功数
	 */
	private volatile AtomicLong success = new AtomicLong();
	/**
	 * 失败数
	 */
	private volatile AtomicLong fail = new AtomicLong();
	/**
	 * 消息执行最长时间
	 */
	private volatile long max;
	/**
	 * 消息执行最短时间
	 */
	private volatile long min;
	/**
	 * 消息执行平均时间
	 */
	private volatile long avg;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassMethod() {
		return classMethod;
	}

	public void setClassMethod(String classMethod) {
		this.classMethod = classMethod;
	}

	public AtomicLong getTotal() {
		return total;
	}

	public void setTotal(AtomicLong total) {
		this.total = total;
	}

	public AtomicLong getSuccess() {
		return success;
	}

	public void setSuccess(AtomicLong success) {
		this.success = success;
	}

	public AtomicLong getFail() {
		return fail;
	}

	public void setFail(AtomicLong fail) {
		this.fail = fail;
	}

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}

	public long getAvg() {
		return avg;
	}

	public void setAvg(long avg) {
		this.avg = avg;
	}

}
