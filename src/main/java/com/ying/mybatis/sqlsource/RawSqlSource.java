package com.ying.mybatis.sqlsource;

import com.ying.mybatis.mapping.BoundSql;
import com.ying.mybatis.mapping.DynamicContext;
import com.ying.mybatis.sqlnode.SqlNode;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc 封装最多只带有#{}的SQL信息
 * 也就是#{}需要被处理一次就可以，就可以使用占位符来长期使用。
 * 而这一点和${}很不一样。${}每一次被调用时，就需要去解析一次SQL语句
 */
public class RawSqlSource implements SqlSource{


    /**
     * 使用面向对象的思维去设计的一个类
     */
    private SqlSource sqlSource;

    // 解析阶段去做的
    public RawSqlSource(SqlNode mixedSqlNode) {
        // #{}处理的时候，不需要入参对象的支持
        DynamicContext context = new DynamicContext(null);
        // 处理SqlNode
        mixedSqlNode.apply(context);

        // 需要去对#{}解析解析，解析之后的内容，放到SqlSource
        SqlSourceParser parser = new SqlSourceParser(context);
        sqlSource = parser.parse();
    }

    // 执行阶段去做的
    @Override
    public BoundSql getBoundSql(Object param) {
        return sqlSource.getBoundSql(param);
    }
}
