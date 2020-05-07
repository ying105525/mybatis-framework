package com.ying.mybatis.executor;

import com.ying.mybatis.mapping.BoundSql;
import com.ying.mybatis.mapping.Configuration;
import com.ying.mybatis.mapping.MappedStatement;

public interface Executor {

    Object query(Configuration configuration, MappedStatement mappedStatement, Object param, BoundSql boundSql)
            throws Exception;
}
