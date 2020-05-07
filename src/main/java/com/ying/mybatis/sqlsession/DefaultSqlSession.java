package com.ying.mybatis.sqlsession;

import com.ying.mybatis.executor.Executor;
import com.ying.mybatis.mapping.Configuration;
import com.ying.mybatis.mapping.MappedStatement;

import java.util.List;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc 默认的Sql会话
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T selectOne(String statementId, Object param) {
        List<Object> list = this.selectList(statementId, param);
        if (list != null && list.size() == 1) {
            return (T) list.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> selectList(String statementId, Object param) {
        MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
        if (mappedStatement == null) {
            return null;
        }
        try {
            // TODO Executor executor = new CachingExecutor();
            Executor executor = configuration.newExecutor();
            return (List<T>) executor.query(configuration, mappedStatement, param, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
