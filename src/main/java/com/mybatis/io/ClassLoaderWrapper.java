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

    public Class<?> classForName(String name){
        return classForName(name, getClassLoaders(null));
    }

    InputStream getResourceAsStream(String resource, ClassLoader[] classLoaders){
        for(ClassLoader classLoader : classLoaders){
            if(classLoader != null){
                InputStream inputStream = classLoader.getResourceAsStream(resource);

                if(inputStream == null){
                    inputStream = classLoader.getResourceAsStream("/"+resource);
                }

                if(inputStream != null){
                    return inputStream;
                }
            }
        }
        return null;
    }

    Class<?> classForName(String name, ClassLoader[] classLoaders){
        for(ClassLoader c1 : classLoaders){
            if(c1 != null){
                try{
                    return Class.forName(name, true, c1);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException("找不到类");
    }

    ClassLoader[] getClassLoaders(ClassLoader classLoader){
        return new ClassLoader[]{
          //这里可以补充其他的类加载器
                classLoader,
                Thread.currentThread().getContextClassLoader(),
                getClass().getClassLoader(),
                systemClassLoader
        };
    }
}
