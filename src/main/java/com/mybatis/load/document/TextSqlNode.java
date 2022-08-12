package com.mybatis.load.document;

import com.mybatis.load.xml.DynamicContext;
import com.mybatis.load.xml.GenericToeknParser;
import com.mybatis.load.xml.TokenHandler;

public class TextSqlNode implements SqlNode {

    private final String text;

    public TextSqlNode(String text) {
        this.text = text;
    }

    @Override
    public boolean apply(DynamicContext context) {
        GenericToeknParser parser = new GenericToeknParser("${", "}", new BindingTokenParser(context));
        context.appendSql(parser.parse(text));
        return true;
    }

    private static class BindingTokenParser implements TokenHandler{

        private DynamicContext context;

        public BindingTokenParser(DynamicContext context) {
            this.context = context;
        }

        //匹配到符号后的变量替换
        // TODO: 2022/8/13 这里因为sql没有加上${}，所以先不做处理
        @Override
        public String handlerToken(String content) {
            return content;
        }
    }
}
