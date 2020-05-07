package com.ying.mybatis.sqlnode;

import com.ying.mybatis.mapping.DynamicContext;

import java.util.List;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc
 */
public class MixedSqlNode implements SqlNode{

    /**
     * 封装SqlNode集合信息
     */
    private List<SqlNode> sqlNodes;

    public MixedSqlNode(List<SqlNode> sqlNodes) {
        this.sqlNodes = sqlNodes;
    }

    /**
     * 对外提供对封装数据的操作
     */
    @Override
    public void apply(DynamicContext context) {
        for (SqlNode sqlNode : sqlNodes) {
            sqlNode.apply(context);
        }
    }
}
