package io.log.extension.server.repo;

import io.log.extension.server.entity.DefaultMessage;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DefaultMessageRepo extends ElasticsearchRepository<DefaultMessage, Long> {

	DefaultMessage findByDomainAndClassNameAndClassMethod(String domain, String className, String classMethod);

	List<DefaultMessage> findByDomainAndRootMessageId(String domain, String rootMessageId);

	Page<DefaultMessage> findByDomainAndIsRootMessage(String domain, Boolean isRootMessage, Pageable pageable);
}
