package com.cmschina.elasticsearch.service;

import com.cmschina.elasticsearch.entity.document.EsAccount;

import java.util.List;

public interface EsAccountService {

    EsAccount searchById(Long id);

    List<EsAccount> search();
}
