package io.log.extension.server.repo;

import io.log.extension.server.entity.ExceptionMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by percy on 7/2/16.
 */
public interface ExceptionMessageRepo extends ElasticsearchRepository<ExceptionMessage, Long> {

    Page<ExceptionMessage> findByDomainOrderByStartDesc(String domain, Pageable pageable);

    Page<ExceptionMessage> findByDomainAndClassNameOrderByStartDesc(String domain, String className, Pageable pageable);

    Page<ExceptionMessage> findByDomainAndClassNameAndClassMethodOrderByStartDesc(String domain, String className, String classMethod, Pageable pageable);

    List<ExceptionMessage> findByRootMessageId(String rootMessageId);
}
