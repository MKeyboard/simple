package com.cmschina.elasticsearch.service.impl;

import com.cmschina.elasticsearch.common.RRException;
import com.cmschina.elasticsearch.entity.document.EsAccount;
import com.cmschina.elasticsearch.entity.repository.EsAccountRepository;
import com.cmschina.elasticsearch.service.EsAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EsAccountServiceImpl implements EsAccountService {

    @Autowired
    private EsAccountRepository esAccountRepository;


    @Override
    public EsAccount searchById(Long id) {
        Optional<EsAccount> optional = esAccountRepository.findById(id);
        log.info("optional",optional);
        if(!optional.isPresent()){
            throw new RRException("该产品不存在");
        }
        return optional.get();
    }

    @Override
    public List<EsAccount> search() {
        Iterable<EsAccount> list=esAccountRepository.findAll();
        List<EsAccount> resList = new ArrayList<>();
        list.forEach(esAccount -> {
            resList.add(esAccount);
        });
        return resList;
    }
}
