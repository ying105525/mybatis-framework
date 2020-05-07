package com.ying.mybatis.builder;

import com.ying.mybatis.mapping.Configuration;
import com.ying.mybatis.mapping.MappedStatement;
import com.ying.mybatis.mapping.XMLScriptBuilder;
import com.ying.mybatis.sqlsource.SqlSource;
import org.dom4j.Element;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc
 */
public class XMLStatementBuilder {
    private Configuration configuration;

    public XMLStatementBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parseStatementElement(Element selectElement, String namespace) {
        String statementId = selectElement.attributeValue("id");

        if (statementId == null || selectElement.equals("")) {
            return;
        }
        // 一个CURD标签对应一个MappedStatement对象
        // 一个MappedStatement对象由一个statementId来标识，所以保证唯一性
        // statementId = namespace + "." + CRUD标签的id属性
        statementId = namespace + "." + statementId;

        String parameterType = selectElement.attributeValue("parameterType");
        Class<?> parameterClass = resolveType(parameterType);

        String resultType = selectElement.attributeValue("resultType");
        Class<?> resultClass = resolveType(resultType);

        String statementType = selectElement.attributeValue("statementType");
        statementType = statementType == null || statementType == "" ? "prepared" : statementType;

        // 解析SQL信息
        SqlSource sqlSource = createSqlSource(selectElement);

        // TODO 建议使用构建者模式去优化
        MappedStatement mappedStatement = new MappedStatement(statementId, parameterClass, resultClass, statementType,
                sqlSource);
        configuration.addMappedStatement(statementId, mappedStatement);

    }

    private SqlSource createSqlSource(Element selectElement) {
        // 去解析select等CURD标签中的SQL脚本信息
        XMLScriptBuilder builder = new XMLScriptBuilder();
        SqlSource sqlSource = builder.parseScriptNode(selectElement);
        return sqlSource;
    }

    private Class<?> resolveType(String parameterType) {
        try {
            Class<?> clazz = Class.forName(parameterType);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
