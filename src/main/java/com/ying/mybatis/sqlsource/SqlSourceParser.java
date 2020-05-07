package com.ying.mybatis.sqlsource;

import com.ying.mybatis.mapping.DynamicContext;
import com.ying.mybatis.utils.GenericTokenParser;
import com.ying.mybatis.utils.ParameterMappingTokenHandler;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc 将#{}的处理逻辑进行封装
 */
public class SqlSourceParser {

    private DynamicContext context;

    public SqlSourceParser(DynamicContext context) {
        this.context = context;
    }

    public SqlSource parse() {
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        // 将#{}解析为?并保存参数信息
        GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", handler);
        // 获取真正可以执行的SQL语句
        String sql = tokenParser.parse(context.getSql());
        System.out.println(sql);
        // 该SqlSource就是封装已经解析完成的Sql语句
        return new StaticSqlSource(sql, handler.getParameterMappings());
    }
}
