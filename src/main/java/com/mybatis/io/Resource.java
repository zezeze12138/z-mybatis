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
    public InputStream getResourceAsStream(String resource){
        return classLoaderWrapper.getResourceAsStream(resource);
    }


}
