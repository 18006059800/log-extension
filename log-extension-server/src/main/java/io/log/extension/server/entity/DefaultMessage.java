package io.log.extension.server.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "logx", type = "defaultMessage", shards = 5, replicas = 0, refreshInterval = "-1")
public class DefaultMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	/**
	 * 上下文
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private String domain;
	/**
	 * 类名称
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private String className;
	/**
	 * 方法名
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private String classMethod;
	/**
	 * 当前消息
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private String messageId;
	/**
	 * 父消息
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private String parentMessageId;
	/**
	 * 当前项目根消息
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private String currentRootMessageId;
	/**
	 * 根消息
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private String rootMessageId;

	/**
	 * 当前节点
	 */
	@Field(store = true, index = FieldIndex.not_analyzed, type =FieldType.Ip)
	private String host;
	/**
	 * 消息时间
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private Long time;
	/**
	 * 开始时间
	 */
	@Field(store = true, index = FieldIndex.not_analyzed, type = FieldType.Date)
	private Date start;
	/**
	 * 结束时间
	 */
	@Field(store = true, index = FieldIndex.not_analyzed, type = FieldType.Date)
	private Date end;
	/**
	 * 状态
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private String status;
	/**
	 * 内容
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private String content;
	/**
	 * 是否是根消息
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private Boolean isRootMessage;
	/**
	 * 消息链是否有异常
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private Boolean hasError;
	/**
	 * 根消息类名称
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private String rootClassName;
	/**
	 * 根消息方法名
	 */
	@Field(store = true, index = FieldIndex.not_analyzed)
	private String rootMethodName;

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

	public String getCurrentRootMessageId() {
		return currentRootMessageId;
	}

	public void setCurrentRootMessageId(String currentRootMessageId) {
		this.currentRootMessageId = currentRootMessageId;
	}

	public String getRootMessageId() {
		return rootMessageId;
	}

	public void setRootMessageId(String rootMessageId) {
		this.rootMessageId = rootMessageId;
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

	public Boolean getIsRootMessage() {
		return isRootMessage;
	}

	public void setIsRootMessage(Boolean isRootMessage) {
		this.isRootMessage = isRootMessage;
	}

	public Boolean getHasError() {
		return hasError;
	}

	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}

	public String getRootClassName() {
		return rootClassName;
	}

	public void setRootClassName(String rootClassName) {
		this.rootClassName = rootClassName;
	}

	public String getRootMethodName() {
		return rootMethodName;
	}

	public void setRootMethodName(String rootMethodName) {
		this.rootMethodName = rootMethodName;
	}

}
