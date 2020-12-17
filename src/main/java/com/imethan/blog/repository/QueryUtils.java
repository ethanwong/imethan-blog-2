package com.imethan.blog.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;


/**
 * QueryUtils.java
 *
 * @author Ethan Wong
 * @time 2014年3月12日下午5:08:11
 */
public class QueryUtils {

    /**
     * Dynamic generate {@link Query}.
     *
     * @param searchFilters
     * @return
     * @author Ethan Wong
     * @datetime 2015年10月1日下午8:41:05
     */
    public static Query dynamicGenerateQuery(List<SearchFilter> searchFilters) {
        Query query = new Query();
        for (SearchFilter searchFilter : searchFilters) {
            addCriteriaBySearchFilter(query, searchFilter);
        }

        return query;
    }

    /**
     * Dynamic generate {@link Query}.
     *
     * @param searchFilterSet
     * @return
     * @author Ethan Wong
     * @datetime 2015年10月1日下午8:41:31
     */
    public static Query dynamicGenerateQuery(Set<Entry<String, SearchFilter>> searchFilterSet) {
        Query query = new Query();
        ArrayList<Criteria> crsTmp = new ArrayList<>();
        for (Entry<String, SearchFilter> entry : searchFilterSet) {
            SearchFilter searchFilter = entry.getValue();
            SearchFilter.Operator operator = searchFilter.operator;
            switch (operator) {
                case EQ:
                    crsTmp.add(new Criteria(searchFilter.fieldName).is(searchFilter.value));
                    break;
                case NEQ:
                    crsTmp.add(new Criteria(searchFilter.fieldName).ne(searchFilter.value));
                    break;
                case LIKE:
                    crsTmp.add(new Criteria(searchFilter.fieldName).regex(searchFilter.value.toString()));
                    break;
                case GT:
                    crsTmp.add(new Criteria(searchFilter.fieldName).gt(searchFilter.value));
                    break;
                case LT:
                    crsTmp.add(new Criteria(searchFilter.fieldName).lt(searchFilter.value));
                    break;
                case GTE:
                    crsTmp.add(new Criteria(searchFilter.fieldName).gte(searchFilter.value));
                    break;
                case LTE:
                    crsTmp.add(new Criteria(searchFilter.fieldName).lte(searchFilter.value));
                    break;
                case IN:
                    crsTmp.add(new Criteria(searchFilter.fieldName).in((Object[]) searchFilter.value));
                    break;
                case NIN:
                    crsTmp.add(new Criteria(searchFilter.fieldName).nin((Object[]) searchFilter.value));
                    break;
                case NOT:
                    crsTmp.add(new Criteria(searchFilter.fieldName).not().regex(searchFilter.value.toString()));
                    break;
                case OR:
                    String[] names = StringUtils.split(searchFilter.fieldName, "_");
                    switch (names[0]) {
                        case "EQ":
                            Criteria[] crs = new Criteria[names.length - 1];
                            for (int i = 1; i < names.length; i++) {
                                crs[i - 1] = new Criteria(names[i]).is(searchFilter.value);
                            }
                            Criteria criteria = new Criteria();
                            criteria.orOperator(crs);
                            crsTmp.add(criteria);
                            break;
                    }
            }
        }
        /*
         * 因为criteria.andOperator()方法接收一个Criteria[]类型的数组，
         * 我们这里先将查询条件放入ArrayList中，然后再转成Criteria[]
         */
        Criteria[] crs = new Criteria[crsTmp.size()];
        for (int i = 0; i < crsTmp.size(); i++) {
            crs[i] = crsTmp.get(i);
        }
        Criteria criteria = new Criteria();
        if (crs.length != 0) {
            criteria.andOperator(crs);
        }
        query.addCriteria(criteria);
        return query;
    }

    /**
     * Add criteria by {@link SearchFilter}.
     *
     * @param query
     * @param searchFilter
     * @author Ethan Wong
     * @datetime 2015年10月1日下午8:44:53
     */
    private static void addCriteriaBySearchFilter(Query query, SearchFilter searchFilter) {
        SearchFilter.Operator operator = searchFilter.operator;
        switch (operator) {
            case EQ:
                query.addCriteria(new Criteria(searchFilter.fieldName).is(searchFilter.value));
                break;
            case NEQ:
                query.addCriteria(new Criteria(searchFilter.fieldName).ne(searchFilter.value));
                break;
            case LIKE:
                query.addCriteria(new Criteria(searchFilter.fieldName).regex(searchFilter.value.toString()));
                break;
            case GT:
                query.addCriteria(new Criteria(searchFilter.fieldName).gt(searchFilter.value));
                break;
            case LT:
                query.addCriteria(new Criteria(searchFilter.fieldName).lt(searchFilter.value));
                break;
            case GTE:
                query.addCriteria(new Criteria(searchFilter.fieldName).gte(searchFilter.value));
                break;
            case LTE:
                query.addCriteria(new Criteria(searchFilter.fieldName).lte(searchFilter.value));
                break;
            case IN:
                query.addCriteria(new Criteria(searchFilter.fieldName).in((Object[]) searchFilter.value));
                break;
            case OR:
                String[] names = StringUtils.split(searchFilter.fieldName, "_");
                switch (names[0]) {
                    case "EQ":
                        query.addCriteria(new Criteria().orOperator(new Criteria(names[1]).is(searchFilter.value), new Criteria(names[2]).is(searchFilter.value)));
                        break;
                }
        }
    }

}