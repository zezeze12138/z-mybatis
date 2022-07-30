package com.mybatis.load.xml;

import com.mybatis.session.Configuration;

import java.io.InputStream;
import java.util.Properties;

/**
 * Xml配置构建器
 */
public class XmlConfigBuilder extends BaseBuilder{

    private XPathParser parser;
    private String environment;

    public XmlConfigBuilder(InputStream inputStream) {
        this(new XPathParser(inputStream), null, null);
    }

    public XmlConfigBuilder(XPathParser parser, String environment, Properties properties) {
        super(new Configuration());
        //这里有个操作，是将proerties的信息设置到Configuration中
        //this.configuration.setVariables(properties);
        this.parser = parser;
        this.environment = environment;
    }

    /**
     * 解析并返回configuration
     * @return 配置信息
     */
    public Configuration parse(){
        parseConfiguration(parser);
        return configuration;
    }

    /**
     * 实际的解析方法
     * @param parser xml信息内容
     */
    private void parseConfiguration(XPathParser parser) {
        //这里会实现去除parser中的值，并塞入到configuration的操作。

    }

}
