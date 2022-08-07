package com.mybatis.io;

import java.io.InputStream;

/**
 * 资源信息
 */
public class Resource {

    private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();

    /**
     * 通过资源文件路径，获取配置资源的方法
     * @param resource 资源文件路径s
     * @return
     */
    public static InputStream getResourceAsStream(String resource){
        return classLoaderWrapper.getResourceAsStream(resource);
    }

    /**
     * 通过类名称获取类
     * @param className 类名称
     * @return 类
     */
    public static Class<?> classForName(String className){
        return classLoaderWrapper.classForName(className);
    }

}
