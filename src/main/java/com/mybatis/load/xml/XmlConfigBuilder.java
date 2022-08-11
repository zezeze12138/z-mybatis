package com.mybatis.load.xml;

import com.mybatis.datasource.DataSourceFactory;
import com.mybatis.io.Resource;
import com.mybatis.load.document.XNode;
import com.mybatis.mapping.Environment;
import com.mybatis.session.Configuration;
import com.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Xml配置构建器
 */
public class XmlConfigBuilder extends BaseBuilder{

    private XPathParser parser;
    private String environment;

    public XmlConfigBuilder(InputStream inputStream) {
        this(new XPathParser(inputStream, null), null, null);
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
        //这里会实现去取parser中的值，并塞入到configuration的操作。
        XNode root = parser.evalNode("/configuration");
        //读取设置环境配置信息，设置jdbc连接配置信息
        environmentsElement(root.evalNode("environments"));
        //添加mapper
        mapperElement(root.evalNode("mappers"));
    }


    /**
     * 解析并生成数据源环境
     * @param environmentContext
     */
    private void environmentsElement(XNode environmentContext) {
        if(environmentContext != null){
            if(environment == null){
                environment = environmentContext.getStringAttribute("default", null);
            }
            for(XNode child : environmentContext.getChildren()){
                String id = child.getStringAttribute("id", null);
                if(environment.equals(id)){
                    try{
                        //获取事务工厂
                        TransactionFactory transactionFactory = transactionManagerElement(child.evalNode("transactionManager"));
                        //获取数据源工厂
                        DataSourceFactory dataSourceFactory = dataSourceElement(child.evalNode("dataSource"));
                        //获取数据源
                        DataSource dataSource = dataSourceFactory.getDataSource();
                        //生成数据源环境信息
                        Environment environment = new Environment(id, transactionFactory, dataSource);
                        //设置数据源
                        configuration.setEnvironment(environment);
                    }catch (Exception e){
                        e.printStackTrace();
                        throw new RuntimeException("构建事务工厂异常");
                    }

                }
            }
        }
    }

    /**
     * 获取数据源工厂
     * @param context
     * @return
     */
    private DataSourceFactory dataSourceElement(XNode context) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if(context != null){
            String type = context.getStringAttribute("type", null);
            Properties properties = context.getChildrenAsProperties();
            //通过类别名注册器中获取事务工厂
            DataSourceFactory factory = (DataSourceFactory) configuration.getTypeAliasRegistry().resolveAlias(type).getDeclaredConstructor().newInstance();
            factory.setProperties(properties);
            return factory;
        }
        throw new RuntimeException("通过环境配置信息无法构建数据源工厂");
    }

    /**
     * 解析Mapper xml
     * @param parent
     */
    private void mapperElement(XNode parent) {
        if(parent != null){
            for(XNode child : parent.getChildren()){
                if("package".equals(child.getName())){
                    //这里暂时不实现包下的mapper添加
                }else{
                    String resource = child.getStringAttribute("resource", null);
                    String url = child.getStringAttribute("url", null);
                    String mapperClass = child.getStringAttribute("class", null);
                    if(resource != null && url == null && mapperClass == null){
                        InputStream inputStream = Resource.getResourceAsStream(resource);
                        if(inputStream == null){
                            throw new RuntimeException("*mapper.xml文件加载失败");
                        }
                        XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(inputStream, resource, configuration);
                        xmlMapperBuilder.parse();
                    }

                }
            }
        }
    }

    /**
     * 获取事务工厂
     * @param context
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private TransactionFactory transactionManagerElement(XNode context) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if(context != null){
            String type = context.getStringAttribute("type", null);
            Properties properties = context.getChildrenAsProperties();
            //通过类别名注册器中获取事务工厂
            TransactionFactory factory = (TransactionFactory) configuration.getTypeAliasRegistry().resolveAlias(type).getDeclaredConstructor().newInstance();
            factory.setProperties(properties);
            return factory;
        }
        throw new RuntimeException("通过环境配置信息无法构建事务工厂");
    }

}
