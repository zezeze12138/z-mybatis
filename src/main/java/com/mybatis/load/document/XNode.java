package com.mybatis.load.document;

import com.mybatis.load.xml.PropertyParser;
import com.mybatis.load.xml.XPathParser;
import org.w3c.dom.CharacterData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class XNode {

    private final Node node;
    private final String name;
    private final String body;
    private final Properties attributes;
    private final Properties variables;
    private final XPathParser xPathParser;

    public XNode(Node node, Properties variables, XPathParser xPathParser) {
        this.node = node;
        this.variables = variables;
        this.xPathParser = xPathParser;
        this.name = node.getNodeName();
        this.attributes = paresAttributes(node);
        this.body = parseBody(node);
    }

    /**
     * 获取节点信息
     * @param expression 节点位置
     * @return 节点信息
     */
    public XNode evalNode(String expression){
        return xPathParser.evalNode(node, expression);
    }

    /**
     * 获取属性信息，获取不到就用默认值
     * @param name 属性key名称
     * @param defaultValue 默认值
     * @return 返回取值结果
     */
    public String getStringAttribute(String name, String defaultValue){
        String value = attributes.getProperty(name, defaultValue);
        return value;
    }

    /**
     * 获取孩子节点列表信息
     * @return 孩子节点列表
     */
    public List<XNode> getChildren(){
        List<XNode> children = new ArrayList<>();
        NodeList nodeList = node.getChildNodes();
        if(nodeList != null){
            for(int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                //如果在节点列表中的节点的类型是节点，那么加入到列表中。
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    children.add(new XNode(node, variables, xPathParser));
                }
            }
        }
        return children;
    }

    /**
     * 获取孩子节点中的name和value并放入属性配置中
     * @return 属性配置信息
     */
    public Properties getChildrenAsProperties(){
        Properties properties = new Properties();
        for(XNode child : getChildren()){
            String name = child.getStringAttribute("name", null);
            String value = child.getStringAttribute("value", null);
            if(name != null && value != null){
                properties.setProperty(name, value);
            }
        }
        return properties;
    }

    private String parseBody(Node node) {
        String data = getBodyData(node);
        //如果查不到，就查子列表中有没有
        if(data == null){
            NodeList children = node.getChildNodes();
            for(int i=0; i<children.getLength();i++){
                Node child = children.item(i);
                data = getBodyData(child);
                if(data != null)
                    break;
            }
        }
        return data;
    }

    private String getBodyData(Node node) {

        if(node.getNodeType() == Node.CDATA_SECTION_NODE || node.getNodeType() == Node.TEXT_NODE){
            String data = ((CharacterData) node).getData();
            data = PropertyParser.parse(data, variables);
            return data;
        }
        return null;
    }

    private Properties paresAttributes(Node node) {
        Properties attributes = new Properties();
        NamedNodeMap attributeNodes = node.getAttributes();
        if(attributeNodes != null){
            for(int i = 0; i < attributeNodes.getLength(); i++){
                Node attribute = attributeNodes.item(i);
                String value = PropertyParser.parse(attribute.getNodeValue(), variables);
                attributes.put(attribute.getNodeName(), value);
            }
        }
        return attributes;
    }

    public String getName() {
        return name;
    }
}
