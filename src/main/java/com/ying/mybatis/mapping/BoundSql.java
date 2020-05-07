package com.ying.mybatis.mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc 封装解析之后的SQL信息，以及解析占位符?时产生的参数信息
 */
public class BoundSql {

    /**
     * 解析之后的sql语句
     */
    private String sql;

    /**
     * 解析过程中产生的SQL参数信息
     */
    private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();

    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void addParameterMappings(ParameterMapping parameterMapping) {
        this.parameterMappings.add(parameterMapping);
    }
}
