/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.tgr.springbootmybatis.file.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SearchFilter {

    public enum Operator {
        EQ, LIKE, GT, LT, GTE, LTE, IN, NOTIN;
    }

    public String fieldName;
    public Object value;
    public Operator operator;

    public SearchFilter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    /**
     * searchParams中key的格式为OPERATOR_FIELDNAME
     */
    public static Map<String, SearchFilter> parse(
            Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = new HashMap<>();

        for (Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StringUtils.isBlank((String) value)) {
                continue;
            }

            // 拆分operator与filedAttribute
            String[] names = StringUtils.split(key, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(key
                        + " is not a valid search filter name");
            }
            String filedName = names[1];
            Operator operator = Operator.valueOf(names[0]);

            // 创建searchFilter
            SearchFilter filter = null;
            if(operator.equals(Operator.IN) || operator.equals(Operator.NOTIN)){
                String[] values = ((String) value).split(",");
                filter = new SearchFilter(filedName, operator, values);
            }else{
                filter = new SearchFilter(filedName, operator, value);
            }
//            SearchFilter filter = new SearchFilter(filedName, operator, value);
            filters.put(key, filter);
        }

        return filters;
    }
}
