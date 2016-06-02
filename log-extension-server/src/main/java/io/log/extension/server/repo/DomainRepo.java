package io.log.extension.server.repo;

import io.log.extension.server.entity.Domain;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DomainRepo extends ElasticsearchRepository<Domain, Long> {

	Domain findByName(String domain);

}
