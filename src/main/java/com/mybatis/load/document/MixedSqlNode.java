package com.mybatis.load.document;

import com.mybatis.load.xml.DynamicContext;
import com.mybatis.mapping.SqlSource;

import java.util.List;

public class MixedSqlNode implements SqlNode{

    private final List<SqlNode> contents;

    public MixedSqlNode(List<SqlNode> contents) {
        this.contents = contents;
    }

    @Override
    public boolean apply(DynamicContext context) {
        for(SqlNode sqlNode : contents){
            sqlNode.apply(context);
        }
        return true;
    }
}
