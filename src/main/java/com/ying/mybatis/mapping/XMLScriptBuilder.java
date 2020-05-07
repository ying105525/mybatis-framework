package com.ying.mybatis.mapping;

import com.ying.mybatis.sqlnode.*;
import com.ying.mybatis.sqlsource.DynamicSqlSource;
import com.ying.mybatis.sqlsource.RawSqlSource;
import com.ying.mybatis.sqlsource.SqlSource;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc
 */
public class XMLScriptBuilder {

    private boolean isDynamic;

    public SqlSource parseScriptNode(Element selectElement) {
        // 1.解析出来所有的SqlNode信息
        MixedSqlNode rootSqlNode = parseDynamicTags(selectElement);
        // isDynamic是parseDynamicTags过程中，得到的值
        // 如果包含${}或者包含动态标签，则isDynamic为true
        SqlSource sqlSource;
        // 2.将SqlNode信息封装到SqlSource中，并且要根据是否动态节点去选择不同的SqlSource
        // 如果isDynamic为true，则说明SqlNode集合信息里面包含${}SqlNode节点信息或者动态标签的节点信息
        if (isDynamic) {
            sqlSource = new DynamicSqlSource(rootSqlNode);
        } else {
            sqlSource = new RawSqlSource(rootSqlNode);
        }

        return sqlSource;
    }

    private MixedSqlNode parseDynamicTags(Element selectElement) {
        List<SqlNode> sqlNodes = new ArrayList<>();
        int nodeCount = selectElement.nodeCount();
        // 获取select标签的子标签
        for (int i = 0; i < nodeCount; i++) {
            Node node = selectElement.node(i);
            // 区分是文本标签还是元素标签
            if (node instanceof Text) {
                String sqlText = node.getText().trim();
                if (sqlText == null || "".equals(sqlText)) {
                    continue;
                }
                TextSqlNode textSqlNode = new TextSqlNode(sqlText);
                if (textSqlNode.isDynamic()) {
                    // 设置是否动态为true
                    isDynamic = true;
                    sqlNodes.add(textSqlNode);
                } else {
                    sqlNodes.add(new StaticTextSqlNode(sqlText));
                }
            } else if (node instanceof Element) {
                // 获取动态标签的标签名称
                String nodeName = node.getName();
                // TODO 设计模式优化
                if ("if".equals(nodeName)) {
                    // 封装成IfSqlNode（test信息、子标签信息）
                    Element element = (Element) node;
                    // if标签的test属性信息
                    String test = element.attributeValue("test");
                    // if标签的子标签信息
                    MixedSqlNode rootSqlNode = parseDynamicTags(element);

                    // IfSqlNode就是封装if标签信息的
                    IfSqlNode ifSqlNode = new IfSqlNode(test, rootSqlNode);
                    sqlNodes.add(ifSqlNode);
                }
                isDynamic = true;
            }
        }

        return new MixedSqlNode(sqlNodes);
    }
}
