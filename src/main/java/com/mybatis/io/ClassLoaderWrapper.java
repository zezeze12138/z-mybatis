package com.mybatis.io;

import java.io.InputStream;

public class ClassLoaderWrapper {

    ClassLoader systemClassLoader;

    public ClassLoaderWrapper() {
        this.systemClassLoader = ClassLoader.getSystemClassLoader();
    }

    InputStream getResourceAsStream(String resource){
        return getResourceAsStream(resource, getClassLoaders(null));
    }

    InputStream getResourceAsStream(String resource, ClassLoader[] classLoaders){
        for(ClassLoader classLoader : classLoaders){
            if(classLoader != null){
                InputStream inputStream = classLoader.getResourceAsStream(resource);

                if(inputStream != null){
                    return inputStream;
                }
            }
        }
        return null;
    }

    ClassLoader[] getClassLoaders(ClassLoader classLoader){
        return new ClassLoader[]{
          //这里可以补充其他的类加载器
          systemClassLoader
        };
    }
}
