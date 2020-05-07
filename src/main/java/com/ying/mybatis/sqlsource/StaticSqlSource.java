package com.ying.mybatis.sqlsource;

import com.ying.mybatis.mapping.BoundSql;
import com.ying.mybatis.mapping.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc
 */
public class StaticSqlSource implements SqlSource {

    // 解析之后的SQL语句
    private String sql;

    // 解析过程中产生的SQL参数信息
    private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();


    public StaticSqlSource(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return new BoundSql(sql, parameterMappings);
    }
}
