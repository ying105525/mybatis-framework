package com.ying.mybatis.sqlnode;

import com.ying.mybatis.mapping.DynamicContext;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc
 */
public class StaticTextSqlNode implements SqlNode {

    private String sqlText;

    public StaticTextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    @Override
    public void apply(DynamicContext context) {
        context.appendSql(sqlText);
    }
}
