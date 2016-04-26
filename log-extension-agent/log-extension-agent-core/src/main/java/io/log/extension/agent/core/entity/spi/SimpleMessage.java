package io.log.extension.agent.core.entity.spi;

import java.io.Serializable;

public class SimpleMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 上下文
	 */
	private String domain;

	/**
	 * 节点的IP地址
	 */
	private String host;
	/**
	 * 消息类型
	 */
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
