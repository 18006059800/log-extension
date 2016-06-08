package io.log.extension.server.repo;

import io.log.extension.server.entity.App;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AppRepo extends ElasticsearchRepository<App, Long> {

	App findByAppName(String app);

}
