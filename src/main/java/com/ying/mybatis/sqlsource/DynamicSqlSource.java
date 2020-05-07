package com.ying.mybatis.sqlsource;

import com.ying.mybatis.mapping.BoundSql;
import com.ying.mybatis.mapping.DynamicContext;
import com.ying.mybatis.sqlnode.SqlNode;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc 封装带有${}或者动态SQL标签的SQL信息
 */
public class DynamicSqlSource  implements SqlSource {

    private SqlNode rootSqlNode;

    public DynamicSqlSource(SqlNode mixedSqlNode) {
        this.rootSqlNode = mixedSqlNode;
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        // #{}处理的时候，不需要入参对象的支持
        DynamicContext context = new DynamicContext(param);
        // 处理SqlNode，先去处理动态标签和${}，拼接成一条SQL文本，该SQL文本还包含#{}
        rootSqlNode.apply(context);

        String sqlText = context.getSql();
        System.out.println(sqlText);

        // 需要去对#{}解析解析，解析之后的内容，放到SqlSource

        // // 处理#{}
        // ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        // // 将#{}解析为?并保存参数信息
        // GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", handler);
        // // 获取真正可以执行的SQL语句
        // String sql = tokenParser.parse(context.getSql());
        //
        // System.out.println(sql);
        SqlSourceParser parser = new SqlSourceParser(context);
        SqlSource sqlSource = parser.parse();

        // 其实调用的就是StaticSqlSource的API功能
        return sqlSource.getBoundSql(param);
    }
}
