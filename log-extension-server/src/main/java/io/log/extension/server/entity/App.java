package io.log.extension.server.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

@Document(indexName = "logx", type = "app", shards = 5, replicas = 0, refreshInterval = "-1", createIndex=false)
public class App implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    @Field(store = true, index = FieldIndex.not_analyzed)
    private String appName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
