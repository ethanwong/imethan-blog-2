package com.imethan.blog.repository;

/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.*;
import java.util.Map.Entry;

public class SearchFilter {

    public static String prefix = "search_";//搜索前缀

    public enum Operator {
        EQ, NEQ, LIKE, GT, LT, GTE, LTE, IN, OR, NIN, NOT, NOTIN
    }

    public String fieldName;
    public Object value;
    public Operator operator;

    public SearchFilter(String fieldName, Object value) {
        this.fieldName = fieldName;
        this.value = value;
    }

    public SearchFilter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    /**
     * 装换查询参数
     * searchParams中key的格式为OPERATOR_FIELDNAME
     *
     * @param searchParams
     * @return
     * @author Ethan Wong
     * @datetime 2015年10月1日上午1:24:57
     */
    public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = new HashMap<>();

        for (Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }

            // 拆分operator与filedAttribute
            String[] names = StringUtils.split(key, "_");
//			if (names.length != 2) {
//				throw new IllegalArgumentException(key + " is not a valid search filter name");
//			}
            if (names.length > 1) {
                String filedName = names[1];
                Operator operator = Operator.valueOf(names[0]);
                if (names.length >= 4) {
                    operator = Operator.valueOf(names[2]);
                    filedName = names[0] + "_" + names[1];
                    for (int i = 3; i < names.length; i++) {
                        filedName = filedName + "_" + names[i];
                    }
                }

                // 创建searchFilter
                SearchFilter filter = new SearchFilter(filedName, operator, value);
                filters.put(key, filter);
            }
        }
        return filters;
    }

    public static Map<String, Object> buildByHttpRequest(final ServletRequest request) {
        return buildByHttpRequest(request, "search");
    }

    public static Map<String, Object> buildByHttpRequest(ServletRequest request, String prefix) {
        // 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.

        Assert.notNull(request, "Request must not be null");
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    if (StringUtils.isNotBlank(values[0])) {
                        params.put(unprefixed, values[0]);
                    }
                }
            }
        }
        return params;
    }

    public static List<SearchFilter> buildByHttpRequestList(final ServletRequest request, final String filterPrefix) {
        List<SearchFilter> filterList = new ArrayList<SearchFilter>();

        // 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
        Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, filterPrefix + "_");

        // 分析参数Map,构造SearchFilter列表
        for (Entry<String, Object> entry : filterParamMap.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            String[] names = StringUtils.split(key, "_");
            String filedName = names[1];
            Operator operator = Operator.valueOf(names[0]);
            // 如果value值为空,则忽略此filter.
            if (StringUtils.isNotBlank(value)) {
                SearchFilter filter = new SearchFilter(filedName, operator, value);
                filterList.add(filter);
            }
        }

        return filterList;
    }
}
