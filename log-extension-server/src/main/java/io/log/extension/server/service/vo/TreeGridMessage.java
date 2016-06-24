package io.log.extension.server.service.vo;

/**
 * Created by percy on 6/23/16.
 */
public class TreeGridMessage {
    private String id;
    private String domain;
    private String pid;
    private String className;
    private String classMethod;
    private Boolean hasError;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public Boolean getHasError() {
        return hasError;
    }

    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }
}
