package com.cmschina.elasticsearch.rest;

import com.cmschina.elasticsearch.entity.document.EsAccount;
import com.cmschina.elasticsearch.entity.repository.EsAccountRepository;
import com.cmschina.elasticsearch.service.EsAccountService;
import com.cmschina.elasticsearch.utils.BaseData;
import com.cmschina.elasticsearch.service.EsAccountService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
@Api("elasticsearch搜索引擎")
@Slf4j
public class EsAccountRest {

    @Autowired
    private EsAccountService esAccountService;

    @Autowired
    private EsAccountRepository esAccountRepository;

    @GetMapping("/search/{id}")
    @ApiOperation("搜索接口")
    public BaseData search(@PathVariable("id")Long id){
        return BaseData.ok(esAccountService.searchById(id));
    }

    @GetMapping("/listAll")
    @ApiOperation("搜索接口")
    public BaseData listAll(){
        return BaseData.ok(esAccountService.search());
    }

    @PostMapping("/searchs")
    @ApiOperation("非聚集查询")
    public BaseData searchs(@RequestParam("firstName")String firstName){
       QueryBuilder queryBuilder= QueryBuilders.matchQuery("firstname", firstName);
        Iterable<EsAccount> account=esAccountRepository.search(queryBuilder);
        log.info("account",account);
        return BaseData.ok(account);
    }

    @PostMapping("/searchPage")
    @ApiOperation("分页聚合查询查询")
    public BaseData searchPage(@RequestParam("pageFrom")Integer pageFrom,
                               @RequestParam("pageSize")Integer pageSize){
        Map<String, Object> map = Maps.newHashMap();
        //分页条件
        Pageable page = PageRequest.of(pageFrom,pageSize);
        //检索条件
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        //排序条件
        FieldSortBuilder fsb =  SortBuilders.fieldSort("age").order(SortOrder.DESC);
        //聚合条件
        TermsAggregationBuilder builder1 = AggregationBuilders.terms("gender").field("gender.keyword");
        TermsAggregationBuilder builder2 = AggregationBuilders.terms("state").field("state.keyword");
        TermsAggregationBuilder builder = builder1.subAggregation(builder2);
        //构建查询条件
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(bqb)
                .withSort(fsb)
                .addAggregation(builder)
                .withPageable(page)
                .build();
        AggregatedPage<EsAccount> search = (AggregatedPage)esAccountRepository.search(query);
        long totalElements = search.getTotalElements();
        int totalPages = search.getTotalPages();
        List<EsAccount> content = search.getContent();
        Terms term1 = (Terms)search.getAggregations().getAsMap().get("gender");
        log.debug("term1============"+term1.toString());
        for (Terms.Bucket bucket : term1.getBuckets()) {
            log.debug("一级内容"+bucket.toString());
            map.put(bucket.getKey().toString(), bucket.getDocCount());
            Terms terms_year = bucket.getAggregations().get("state");
            for (Terms.Bucket year_bucket : terms_year.getBuckets()) {
                log.debug("二级内容"+year_bucket.toString());
                map.put(year_bucket.getKey().toString(), year_bucket.getDocCount());
            }
        }
        map.put("total",totalElements);
        map.put("totalPages",totalPages);
        map.put("recommendData",content);
        return BaseData.ok(map);
    }
}
