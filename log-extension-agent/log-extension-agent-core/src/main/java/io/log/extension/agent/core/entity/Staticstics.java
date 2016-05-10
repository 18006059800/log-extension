package io.log.extension.agent.core.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Staticstics {

	private volatile AtomicLong total = new AtomicLong();
	private volatile AtomicLong success = new AtomicLong();
	private volatile AtomicLong fail = new AtomicLong();
	private Map<String, MessageInfo> infos = new ConcurrentHashMap<String, MessageInfo>();

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

	public Map<String, MessageInfo> getInfos() {
		return infos;
	}

	public void setInfos(Map<String, MessageInfo> infos) {
		this.infos = infos;
	}

}
