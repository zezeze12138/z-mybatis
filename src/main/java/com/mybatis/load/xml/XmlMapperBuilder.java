package com.mybatis.load.xml;

import com.mybatis.io.Resource;
import com.mybatis.load.document.XNode;
import com.mybatis.session.Configuration;

import java.io.InputStream;
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
        buildStatementFromContext(evalNode.evalNodes("select|insert|update|delete"));
    }

    /**
     * 解析mapper中的sql配置，创建出mappedStatement
     * @param list
     */
    private void buildStatementFromContext(List<XNode> list) {
        for(XNode context : list){

        }
    }


}
