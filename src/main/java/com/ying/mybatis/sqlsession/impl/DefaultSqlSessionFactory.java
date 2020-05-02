package com.ying.mybatis.sqlsession.impl;

import com.ying.mybatis.mapping.Configuration;
import com.ying.mybatis.sqlsession.SqlSession;
import com.ying.mybatis.sqlsession.SqlSessionFactory;

/**
 * @author 应森亮
 * @date 2020/05/02
 * @desc 默认SqlSessionFactory
 * DefaultSqlSessionFactory 的职责只是创建SqlSession对象，不负责配置文件的解析工作
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    // 外部注入一个configuration，因为工厂不负责Configuration的解析，只提供创建SqlSession对象
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSqlSession() {
        return null;
    }
}
