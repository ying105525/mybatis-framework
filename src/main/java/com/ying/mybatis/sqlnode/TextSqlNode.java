package com.ying.mybatis.sqlnode;

import com.ying.mybatis.mapping.DynamicContext;
import com.ying.mybatis.utils.GenericTokenParser;
import com.ying.mybatis.utils.OgnlUtils;
import com.ying.mybatis.utils.SimpleTypeRegistry;
import com.ying.mybatis.utils.TokenHandler;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc
 */
public class TextSqlNode implements SqlNode {

    private String sqlText;

    public TextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    // ${}的处理，就是在这个时候
    @Override
    public void apply(DynamicContext context) {

        BindingTokenParser handler = new BindingTokenParser(context);
        // 将#{}解析为?并保存参数信息
        GenericTokenParser tokenParser = new GenericTokenParser("${", "}", handler);
        // 获取真正可以执行的SQL语句
        String sql = tokenParser.parse(sqlText);
        context.appendSql(sql);
    }

    /**
     * 对外提供保存数据的处理功能
     *
     * @return
     */
    public boolean isDynamic() {
        if (sqlText.indexOf("${") > -1) {
            return true;
        }

        return false;
    }

    private static class BindingTokenParser implements TokenHandler {
        private DynamicContext context;

        public BindingTokenParser(DynamicContext context) {
            this.context = context;
        }

        /**
         * expression：比如说${username}，那么expression就是username username也就是Ognl表达式
         */
        @Override
        public String handleToken(String expression) {
            Object paramObject = context.getBindings().get("_parameter");
            if (paramObject == null) {
                // context.getBindings().put("value", null);
                return "";
            } else if (SimpleTypeRegistry.isSimpleType(paramObject.getClass())) {
                // context.getBindings().put("value", paramObject);
                return String.valueOf(paramObject);
            }

            // 使用Ognl api去获取相应的值
            // TODO Object value = OgnlUtils.getValue(expression, context.getBindings());
            Object value = OgnlUtils.getValue(expression, paramObject);
            String srtValue = value == null ? "" : String.valueOf(value);
            return srtValue;
        }

    }
}
