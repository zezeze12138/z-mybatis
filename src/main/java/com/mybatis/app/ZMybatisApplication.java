package com.mybatis.app;

import com.mybatis.io.Resource;
import com.mybatis.load.xml.XmlConfigBuilder;
import com.mybatis.session.Configuration;
import com.mybatis.session.SqlSession;
import com.mybatis.session.SqlSessionFactory;
import com.mybatis.session.SqlSessionFactoryBuild;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ZMybatisApplication {

    public static void main(String[] args) {
        //xml资源文件路径
        String resourcePath = "com/mybatis/app/mybatis-config.xml";
        //加载资源到输入流
        InputStream inputStream = Resource.getResourceAsStream(resourcePath);
        //资源转换成配置类
        Configuration configuration = new XmlConfigBuilder(inputStream).parse();
        //通过配置类生成SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuild().build(configuration);
        //通过SqlSessionFactory创建SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //获取用户Mapper实例
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        //获取用户列表
        List<Map<String, Object>> userList = userDao.getUser();
        System.out.println(userList);
    }

}
