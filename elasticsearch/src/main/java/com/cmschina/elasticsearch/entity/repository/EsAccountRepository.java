package com.cmschina.elasticsearch.entity.repository;

import com.cmschina.elasticsearch.entity.document.EsAccount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * account操作类
 */
public interface EsAccountRepository extends ElasticsearchRepository<EsAccount,Long> {

}
