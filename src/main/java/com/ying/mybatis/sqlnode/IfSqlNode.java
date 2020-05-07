package com.ying.mybatis.sqlnode;

import com.ying.mybatis.mapping.DynamicContext;
import com.ying.mybatis.utils.OgnlUtils;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc
 */
public class IfSqlNode implements SqlNode {

    private String test;

    private SqlNode rootSqlNode;

    public IfSqlNode(String test, SqlNode rootSqlNode) {
        super();
        this.test = test;
        this.rootSqlNode = rootSqlNode;
    }



    @Override
    public void apply(DynamicContext context) {
        boolean evaluateBoolean = OgnlUtils.evaluateBoolean(test, context.getBindings().get("_parameter"));
        if (evaluateBoolean) {
            rootSqlNode.apply(context);
            // TODO context.appendSql(context.getSql());
        }
    }
}
