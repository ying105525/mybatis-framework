package com.ying.mybatis.builder;

import com.ying.mybatis.mapping.Configuration;
import org.dom4j.Element;

import java.util.List;

/**
 * @author 应森亮
 * @date 2020/05/04
 * @desc
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    private String namespace;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    @SuppressWarnings("unchecked")
    public void parse(Element rootElement) {
        // 为了方便管理statement，需要使用statement唯一标识
        namespace = rootElement.attributeValue("namespace");

        //其他mapper映射文件中的标签

        //select标签的处理
        List<Element> selectElements = rootElement.elements("select");
        for (Element selectElement : selectElements) {
            XMLStatementBuilder builder = new XMLStatementBuilder(configuration);
            builder.parseStatementElement(selectElement,namespace);
        }

    }
}
