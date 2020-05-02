package com.ying.mybatis.builder;

import com.ying.mybatis.mapping.Configuration;
import org.dom4j.Element;

/**
 * @author 应森亮
 * @date 2020/05/02
 * @desc 整个配置类的构建者
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public Configuration parse(Element rootElement) {
        Element environments = rootElement.element("environments");
//        parseEnvironments(environments);

        Element mappers = rootElement.element("mappers");
//        parseMappers(mappers);

        return configuration;
    }
}
