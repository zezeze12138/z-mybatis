package com.mybatis.load.xml;

import com.mybatis.load.document.MixedSqlNode;
import com.mybatis.load.document.SqlNode;
import com.mybatis.load.document.TextSqlNode;
import com.mybatis.load.document.XNode;
import com.mybatis.mapping.DynamicSqlSource;
import com.mybatis.mapping.SqlSource;
import com.mybatis.session.Configuration;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * sqlSource解析
 */
public class XmlScriptBuilder {

    private final Configuration configuration;
    private final XNode script;
    private final Class<?> parameterType;

    public XmlScriptBuilder(Configuration configuration, XNode script, Class<?> parameterType) {
        this.configuration = configuration;
        this.script = script;
        this.parameterType = parameterType;
    }

    /**
     * 解析方法
     */
    public SqlSource parse(){
        MixedSqlNode sqlNode = parseDynamicTags(script);
        return new DynamicSqlSource(configuration, sqlNode);
    }

    private MixedSqlNode parseDynamicTags(XNode script) {
        List<SqlNode> contents = new ArrayList<>();
        NodeList children = script.getNode().getChildNodes();
        for(int i=0;i<children.getLength();i++){
            XNode child = script.newXnode(children.item(i));
            if(child.getNode().getNodeType() == Node.CDATA_SECTION_NODE || child.getNode().getNodeType() == Node.TEXT_NODE){
                String data = child.getBody();
                if(data == null){
                    data = "";
                }
                //将sql做处理
                TextSqlNode textSqlNode = new TextSqlNode(data);
                //放入列表中
                contents.add(textSqlNode);
            }
        }
        return new MixedSqlNode(contents);
    }
}
