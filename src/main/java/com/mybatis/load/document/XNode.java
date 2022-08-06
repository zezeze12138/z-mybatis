package com.mybatis.load.document;

import com.mybatis.load.xml.PropertyParser;
import com.mybatis.load.xml.XPathParser;
import org.w3c.dom.CharacterData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
}
