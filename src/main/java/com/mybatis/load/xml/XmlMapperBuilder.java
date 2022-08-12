package com.mybatis.load.xml;

import com.mybatis.io.Resource;
import com.mybatis.load.document.XNode;
import com.mybatis.session.Configuration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper xml解析
 */
public class XmlMapperBuilder {

    private final XPathParser parser;
    private final String resource;
    private final Configuration configuration;

    public XmlMapperBuilder(InputStream inputStream, String resource, Configuration configuration) {
        this.parser = new XPathParser(inputStream, null);
        this.configuration = configuration;
        this.resource = resource;
    }

    /**
     * mapper xml解析
     */
    public void parse(){
        configurationElement(parser.evalNode("/mapper"));
    }


    /**
     * 解析配置实现
     * @param evalNode
     */
    private void configurationElement(XNode evalNode) {
        String namespace = evalNode.getStringAttribute("namespace", null);
        if(namespace != null){
            try {
                configuration.addMapper(Resource.classForName(namespace));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<XNode> list = new ArrayList<>();
        list.add(evalNode.evalNode("select"));
        //buildStatementFromContext(namespace, evalNode.evalNodes("select|insert|update|delete"));
        buildStatementFromContext(namespace, list);
    }

    /**
     * 解析mapper中的sql配置，创建出mappedStatement
     * @param namespace 命名空间，传进去和方法id拼接，作为key值放入configuration中的mappedStatements中。
     * @param list
     */
    private void buildStatementFromContext(String namespace, List<XNode> list) {
        for(XNode context : list){
            final XmlStatementBuilder statementBuilder = new XmlStatementBuilder(configuration, context, namespace);
            statementBuilder.parse();
        }
    }


}
