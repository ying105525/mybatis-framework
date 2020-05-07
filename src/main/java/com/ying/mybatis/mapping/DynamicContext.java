package com.ying.mybatis.mapping;

import java.util.HashMap;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc 动态上下文，就是封装的入参信息，解析过程中的SQL信息
 */
public class DynamicContext {

    /**
     * 存SQL信息
     */
    private StringBuffer sb = new StringBuffer();

    /**
     * 存参数信息
     */
    private HashMap<String, Object> bindings = new HashMap<>();

    public DynamicContext(Object param) {
        bindings.put("_parameter", param);
    }

    public String getSql() {
        return sb.toString();
    }

    public void appendSql(String sqlText) {
        this.sb.append(sqlText);
        this.sb.append(" ");
    }

    public HashMap<String, Object> getBindings() {
        return bindings;
    }

    public void addBinding(String name, Object param) {
        this.bindings.put(name, param);
    }

}
