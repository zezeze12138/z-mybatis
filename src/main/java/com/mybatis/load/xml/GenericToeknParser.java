package com.mybatis.load.xml;


/**
 * 对字符串中${}和#{}进行定位，每次定位完成之后调用TokenHandler进行内容替换
 * openToken为"${"或"#{"
 * closeToken为"}"
 */
public class GenericToeknParser {

    private final String openToken;
    private final String closeToken;
    private final TokenHandler handler;

    public GenericToeknParser(String openToken, String closeToken, TokenHandler handler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = handler;
    }

    public String parse(String text){
        //如果字符串为空，返回空字符串
        if(text == null || text.isEmpty()){
            return "";
        }
        //匹配text内容中第一个openToken
        int start = text.indexOf(openToken);
        //表示没有匹配到时，返回原字符串
        if(start == -1){
            return text;
        }
        char[] src = text.toCharArray();
        int offset = 0;
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        do{
            //如果为这个openToken前面有转义字符，那么就不能做解析,将这一块的openToken加上后跳过
            if(start > 0 && src[start - 1] == '\\'){
                builder.append(src, offset, start - offset -1).append(openToken);
                offset = start + openToken.length();
            }else{
                //匹配到openToken的操作
                if(expression == null){
                    expression = new StringBuilder();
                }else {
                    expression.setLength(0);
                }
                builder.append(src, offset, start - offset);
                offset = start + openToken.length();
                //匹配closeToken的操作
                int end = text.indexOf(closeToken, offset);
                while(end > -1){
                    //如果为这个closeToken前面有转义字符，那么就不能做解析,将这一块的closeToken加上后跳过
                    if(end > offset && src[end - 1] =='\\'){
                        expression.append(src, offset, end - offset -1).append(closeToken);
                        offset = end + closeToken.length();
                        end = text.indexOf(closeToken, offset);
                    }else{
                        //匹配到closeToken的操作
                        expression.append(src, offset, end - offset);
                        break;
                    }
                }
                //没匹配到closeToken的处理
                if(end == -1){
                    //将后面的字符都拼接到builder中
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                }else{
                    //表示匹配到了closeToken,那么调用handler对字符串进行处理
                    builder.append(handler.handlerToken(expression.toString()));
                    //将扫描下表添加closeToken的长度
                    offset = end + closeToken.length();
                }
            }
            //设置新的内容查询位置
            start = text.indexOf(openToken, offset);
        } while (start > -1);
        if(offset < src.length){
            //将后面没有可匹配的内容添加到builder中，完成整个text内容的匹配解析
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }
}
