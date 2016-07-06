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

	List<DefaultMessage> findByDomainAndIsRootMessage(String domain, Boolean b);

	List<DefaultMessage> findByDomainAndClassNameAndIsRootMessage(String domain, String className, boolean b);

	List<DefaultMessage> findByDomainAndClassNameAndClassMethodAndIsRootMessage(String domain, String className, String classMethod, boolean b);

	List<DefaultMessage> findByRootMessageId(String rootMessageId);

	Page<DefaultMessage> findByDomainOrderByStartDesc(String domain, Pageable pageable);

	Page<DefaultMessage> findByDomainAndClassNameOrderByStartDesc(String domain, String className, Pageable pageable);

	Page<DefaultMessage> findByDomainAndClassNameAndClassMethodOrderByStartDesc(String domain, String className, String classMethod, Pageable pageable);
}
