package io.log.extension.agent.core.entity.spi;

import java.io.Serializable;
import java.util.Date;

public class DefaultMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 上下文
	 */
	private String domain;
	/**
	 * 类名称
	 */
	private String className;
	/**
	 * 方法名
	 */
	private String classMethod;
	/**
	 * 当前消息
	 */
	private String messageId;
	/**
	 * 父消息
	 */
	private String parentMessageId;
	/**
	 * 根消息
	 */
	private String rootMessageId;
	/**
	 * 回话
	 */
	private String sessionId;
	/**
	 * 当前节点
	 */
	private String host;
	/**
	 * 消息时间
	 */
	private Long time;
	/**
	 * 开始时间
	 */
	private Date start;
	/**
	 * 结束时间
	 */
	private Date end;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 内容
	 */
	private String content;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getParentMessageId() {
		return parentMessageId;
	}

	public void setParentMessageId(String parentMessageId) {
		this.parentMessageId = parentMessageId;
	}

	public String getRootMessageId() {
		return rootMessageId;
	}

	public void setRootMessageId(String rootMessageId) {
		this.rootMessageId = rootMessageId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
