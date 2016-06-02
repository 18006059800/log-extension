package io.log.extension.server.repo;

import io.log.extension.server.entity.DefaultMessage;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DefaultMessageRepo extends
		ElasticsearchRepository<DefaultMessage, Long> {

	DefaultMessage findByDomainAndClassNameAndClassMethod(String domain,
			String className, String classMethod);

}
