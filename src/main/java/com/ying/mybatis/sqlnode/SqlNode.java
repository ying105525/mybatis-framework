package com.ying.mybatis.sqlnode;

import com.ying.mybatis.mapping.DynamicContext;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc
 */
public interface SqlNode {


    /**
     * 解析功能
     * 最终解析出来的SQL语句，封装到DynamicContext中的StringBuffer对象中
     * 解析的时候，可能要用到入参对象
     *
     * 此时解析出来的SQL语句，只是根据动态标签的逻辑，完成了字符串的拼接，它还没有被解析
     * @param context
     */
    void apply(DynamicContext context);
}
