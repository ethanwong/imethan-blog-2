package com.imethan.blog.repository.base;

import com.imethan.blog.repository.SearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * BaseRepository.java
 *
 * @author Ethan Wong
 * @datetime 2015年9月30日下午6:06:15
 * @since JDK 1.7
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {


    /**
     * Find page by parameters.
     *
     * @param parameters
     * @param pageable
     * @return
     * @author Ethan Wong
     * @datetime 2015年10月1日上午2:00:05
     */
    Page<T> getPageByParameters(Map<String, Object> parameters, Pageable pageable);

    /**
     * Find page by {@link SearchFilter}.
     *
     * @param searchFilter
     * @param pageable
     * @return
     * @author Ethan Wong
     * @datetime 2015年10月1日上午4:37:11
     */
    Page<T> getPageBySearchFilter(SearchFilter searchFilter, Pageable pageable);

    /**
     * Find page by {@link SearchFilter} list.
     *
     * @param searchFilters
     * @param pageable
     * @return
     * @author Ethan Wong
     * @datetime 2015年10月1日上午4:37:39
     */
    Page<T> getPageBySearchFilters(List<SearchFilter> searchFilters, Pageable pageable);

    /**
     * 根据ID获取
     *
     * @param id
     * @return
     */
    T getById(String id);

    /**
     * 新增/修改
     *
     * @param collName  条件参数
     * @param parameter 修改参数
     * @return
     */
    T findAndModify(String collName, Object collvalue, Map<String, Object> parameter);

    /**
     * 新增/修改
     *
     * @param collName  条件参数
     * @param parameter 修改参数
     * @return
     */
    T findAndModifyUpdate(String collName, Object collvalue, Map<String, Object> parameter);

    /**
     * 新增/修改
     *
     * @param collName   条件参数1
     * @param collvalue  条件值1
     * @param collName2  条件参数2
     * @param collvalue2 条件值2
     * @param parameter  修改参数
     * @return
     */
    T findAndModify(String collName, Object collvalue, String collName2, Object collvalue2, Map<String, Object> parameter);

    /**
     * 新增/修改
     *
     * @param whereValue 条件参数
     * @param parameter  修改参数
     * @return
     */
    T findAndModify(Map<String, Object> whereValue, Map<String, Object> parameter);

    /**
     * 根据条件获取集合
     *
     * @param parameters
     * @return
     * @author Ethan Wong
     * @datetime 2015年10月1日上午2:00:05
     */
    List<T> getListByParameters(Map<String, Object> parameters, Sort sort);

    /**
     * 根据条件获取单个参数
     *
     * @param parameters
     * @param sort
     * @return
     */
    T getOneByParameters(Map<String, Object> parameters, Sort sort);
}
