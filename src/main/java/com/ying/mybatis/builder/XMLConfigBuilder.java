package com.ying.mybatis.builder;

import com.ying.mybatis.mapping.Configuration;
import com.ying.mybatis.utils.DocumentUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author 应森亮
 * @date 2020/05/02
 * @desc 整个配置类的构建者
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        configuration = new Configuration();
    }

    /**
     * 解析config XML文件
     * @param rootElement 根元素
     * @return 解析完成初始化好的Configuration对象
     */
    public Configuration parse(Element rootElement) {
        Element environments = rootElement.element("environments");
        parseEnvironments(environments);

        Element mappers = rootElement.element("mappers");
        parseMappers(mappers);

        return configuration;
    }


    /**
     * 解析environment标签
     * @param environments environments标签元素
     */
    @SuppressWarnings("unchecked")
    private void parseEnvironments(Element environments) {
        String def = environments.attributeValue("default");
        List<Element> elements = environments.elements("environment");
        for (Element envElement : elements) {
            String id = envElement.attributeValue("id");
            if (id.equals(def)) {
                parseEnvironment(envElement);
            }
        }
    }

    /**
     * 解析mapper标签
     * @param mappers mappers元素
     */
    @SuppressWarnings("unchecked")
    private void parseMappers(Element mappers) {
        List<Element> mapperElements = mappers.elements("mapper");
        for (Element mapperElement : mapperElements) {
            parseMapper(mapperElement);
        }
    }

    private void parseEnvironment(Element envElement) {
        Element dataSource = envElement.element("dataSource");
        parseDataSource(dataSource);
    }


    /**
     * 解析DataSource
     * @param dataSourceElement  dataSourceElement元素
     */
    @SuppressWarnings("unchecked")
    private void parseDataSource(Element dataSourceElement) {
        String type = dataSourceElement.attributeValue("type");
        if ("DBCP".equals(type)) {
            BasicDataSource dataSource = new BasicDataSource();

            Properties properties = new Properties();

            List<Element> propertyElements = dataSourceElement.elements();
            for (Element prop : propertyElements) {
                String name = prop.attributeValue("name");
                String value = prop.attributeValue("value");
                properties.put(name, value);
            }

            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));

            configuration.setDataSource(dataSource);
        }
    }

    /**
     * 解析mapper文件
     * @param mapperElement
     */
    private void parseMapper(Element mapperElement) {
        String resource = mapperElement.attributeValue("resource");

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        Document document = DocumentUtils.loadDocument(inputStream);

        // 解析映射文件XMLMapperBuilder
        XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(configuration);
        mapperBuilder.parse(document.getRootElement());
    }
}
