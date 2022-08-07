package com.mybatis.load.xml;

import com.mybatis.load.document.XNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.util.Properties;

/**
 * 将xml配置文件的信息解析存储到该类中
 * 由于源码中这部分是使用mybatis自己写的xml封装，转换细节暂时不做过多考虑
 */
public class XPathParser {

    private final Document document;
    private Properties variables;
    private EntityResolver entityResolver;
    private XPath xpath;

    public XPathParser(InputStream inputStream, Properties properties){
        //这里将配置文件的输入流做转换
        commonConstructor(properties, new XMLMapperEntityResolver());
        this.document = createDocument(new InputSource(inputStream));
    }

    private void commonConstructor(Properties variables, EntityResolver entityResolver){
        this.variables = variables;
        this.entityResolver = entityResolver;
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
    }

    /**
     * 创建Document
     * @param inputSource 输入的配置文件xml
     * @return 返回document
     */
    private Document createDocument(InputSource inputSource){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setValidating(false);
            factory.setNamespaceAware(false);
            factory.setIgnoringComments(true);
            factory.setCoalescing(false);
            factory.setExpandEntityReferences(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(entityResolver);
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {

                }
            });

            return builder.parse(inputSource);
        }catch (Exception e){
            throw new RuntimeException("创建xml的document实例异常");
        }
    }

    /**
     *  获取节点信息
     * @param expression
     * @return
     */
    public XNode evalNode(String expression){
        Node node = null;
        try {
            node = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return new XNode(node, variables, this);
    }

    /**
     * 根据传入的节点作为根获取节点信息
     * @param root
     * @param expression
     * @return
     */
    public XNode evalNode(Object root, String expression){
        Node node = null;
        try {
            node = (Node) xpath.evaluate(expression, root, XPathConstants.NODE);
            if(node == null){
                return null;
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return new XNode(node,  variables, this);
    }

}
