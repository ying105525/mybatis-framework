package com.ying.mybatis.sqlsession;

import com.ying.mybatis.builder.XMLConfigBuilder;
import com.ying.mybatis.mapping.Configuration;
import com.ying.mybatis.sqlsession.impl.DefaultSqlSessionFactory;
import org.dom4j.Document;
import com.ying.mybatis.utils.DocumentUtils;

import java.io.InputStream;

/**
 * @author 应森亮
 * @date 2020/05/02
 * @desc 构建者模式，获取SqlSessionFactory sql会话工厂
 */
public class SqlSessionFactoryBuilder {

    /**
     * 根据输入流获取 SqlSessionFactory
     * @param inputStream 输入流
     * @return SqlSessionFactory
     */
    public SqlSessionFactory build(InputStream inputStream) {
        Document document = DocumentUtils.loadDocument(inputStream);

        XMLConfigBuilder configBuilder = new XMLConfigBuilder();
        Configuration configuration = configBuilder.parse(document.getRootElement());
        return build(configuration);
    }


    private SqlSessionFactory build(Configuration configuration){
        return new DefaultSqlSessionFactory(configuration);
    }
}
