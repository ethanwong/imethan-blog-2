package com.imethan.blog.repository.base;

import com.imethan.blog.repository.QueryUtils;
import com.imethan.blog.repository.SearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * BaseRepositoryImpl.java
 *
 * @author Ethan Wong
 * @datetime 2015年9月30日下午6:08:09
 * @since JDK 1.7
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleMongoRepository<T, ID> implements BaseRepository<T, ID> {

    private MongoOperations mongoOperations;
    private MongoEntityInformation<T, ID> metadata;

    /**
     * Creates a new {@link SimpleMongoRepository} for the given {@link MongoEntityInformation} and {@link MongoTemplate}.
     *
     * @param metadata        must not be {@literal null}.
     * @param mongoOperations must not be {@literal null}.
     */
    public BaseRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoOperations = mongoOperations;
        this.metadata = metadata;
    }

    @Override
    public Page<T> getPageByParameters(Map<String, Object> parameters, Pageable pageable) {
        Query query = QueryUtils.dynamicGenerateQuery(SearchFilter.parse(parameters).entrySet());
        long count = mongoOperations.count(query, metadata.getJavaType());
        List<T> list = mongoOperations.find(query.with(pageable), metadata.getJavaType());

        return new PageImpl<T>(list, pageable, count);
    }
    @Override
    public Page<T> getPageBySearchFilter(SearchFilter searchFilter, Pageable pageable) {
        List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
        searchFilters.add(searchFilter);
        return this.getPageBySearchFilters(searchFilters, pageable);

    }
    @Override
    public Page<T> getPageBySearchFilters(List<SearchFilter> searchFilters, Pageable pageable) {
        Query query = QueryUtils.dynamicGenerateQuery(searchFilters);
        long count = mongoOperations.count(query, metadata.getJavaType());
        List<T> list = mongoOperations.find(query.with(pageable), metadata.getJavaType());
        return new PageImpl<T>(list, pageable, count);
    }

    @Override
    public T getById(String id) {
        return mongoOperations.findOne(Query.query(Criteria.where("_id").is(id)), metadata.getJavaType());
    }

    @Override
    public T findAndModify(String collName, Object collvalue, Map<String, Object> parameter) {
        Query query = new Query(Criteria.where(collName).is(collvalue));
        Update update = new Update();
        for (Map.Entry<String, Object> entry : parameter.entrySet()) {
            // 过滤掉空值
            update.set(entry.getKey(), entry.getValue());
        }
        FindAndModifyOptions options = new FindAndModifyOptions();
        // 先查询，如果没有符合条件的，会执行插入，插入的值是查询值 ＋ 更新值
        options.upsert(false);
        // 返回当前最新值
        options.returnNew(true);
        return mongoOperations.findAndModify(query, update, options, metadata.getJavaType());
    }

    @Override
    public T findAndModifyUpdate(String collName, Object collvalue, Map<String, Object> parameter) {
        Query query = new Query(Criteria.where(collName).is(collvalue));
        Update update = new Update();
        for (Map.Entry<String, Object> entry : parameter.entrySet()) {
            // 过滤掉空值
            update.set(entry.getKey(), entry.getValue());
        }
        FindAndModifyOptions options = new FindAndModifyOptions();
        // 先查询，如果没有符合条件的，会执行插入，插入的值是查询值 ＋ 更新值
        options.upsert(true);
        // 返回当前最新值
        options.returnNew(true);
        return mongoOperations.findAndModify(query, update, options, metadata.getJavaType());
    }

    @Override
    public T findAndModify(String collName, Object collvalue, String collName2, Object collvalue2, Map<String, Object> parameter) {
        Query query = new Query(Criteria.where(collName).is(collvalue).and(collName2).is(collvalue2));
        Update update = new Update();
        for (Map.Entry<String, Object> entry : parameter.entrySet()) {
            // 过滤掉空值
            update.set(entry.getKey(), entry.getValue());
        }
        FindAndModifyOptions options = new FindAndModifyOptions();
        // 先查询，如果没有符合条件的，会执行插入，插入的值是查询值 ＋ 更新值
        options.upsert(false);
        // 返回当前最新值
        options.returnNew(true);
        return mongoOperations.findAndModify(query, update, options, metadata.getJavaType());
    }

    @Override
    public T findAndModify(Map<String, Object> whereValue, Map<String, Object> parameter) {
        Query query = new Query();
        for (Map.Entry<String, Object> val : whereValue.entrySet()) {
            // 过滤掉空值
            query.addCriteria(Criteria.where(val.getKey()).is(val.getValue()));
        }
        Update update = new Update();
        for (Map.Entry<String, Object> entry : parameter.entrySet()) {
            // 过滤掉空值
            update.set(entry.getKey(), entry.getValue());
        }
        FindAndModifyOptions options = new FindAndModifyOptions();
        // 先查询，如果没有符合条件的，会执行插入，插入的值是查询值 ＋ 更新值
        options.upsert(false);
        // 返回当前最新值
        options.returnNew(true);
        return mongoOperations.findAndModify(query, update, options, metadata.getJavaType());
    }

    @Override
    public List<T> getListByParameters(Map<String, Object> parameters, Sort sort) {
        Query query = QueryUtils.dynamicGenerateQuery(SearchFilter.parse(parameters).entrySet());
        if (sort != null) {
            query.with(sort);
        }
        return mongoOperations.find(query, metadata.getJavaType());
    }

    @Override
    public T getOneByParameters(Map<String, Object> parameters, Sort sort) {
        Query query = QueryUtils.dynamicGenerateQuery(SearchFilter.parse(parameters).entrySet());
        if (sort != null) {
            query.with(sort);
        }
        query.limit(1);
        return mongoOperations.findOne(query, metadata.getJavaType());
    }
}