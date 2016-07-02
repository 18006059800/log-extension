package io.log.extension.server.repo;

import io.log.extension.server.entity.ExceptionMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by percy on 7/2/16.
 */
public interface ExceptionMessageRepo extends ElasticsearchRepository<ExceptionMessage, Long> {


}
