package com.mybatis.datasource.unpooled;

import com.mybatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class UnpooledDataSourceFactory implements DataSourceFactory{

    private DataSource dataSource;

    public UnpooledDataSourceFactory() {
        this.dataSource = new UnpooledDataSource();
    }

    @Override
    public void setProperties(Properties properties) {
        Properties driverProperties = new Properties();
        for(Object key : properties.keySet()){
            String propertiesName = (String) key;
            if(propertiesName.startsWith("driver.")){
                String value = properties.getProperty(propertiesName);
                driverProperties.setProperty(propertiesName.substring("driver.".length()), value);
            }else if(hasSetter(propertiesName)){
                String value = properties.getProperty(propertiesName);
                setMetaInfo(dataSource, propertiesName, value);
            }else{
                //throw new RuntimeException("未知的数据源类型属性："+propertiesName);
            }
        }
        System.out.println(dataSource);
    }

    /**
     * Mybatis自己封装了MetaObject类来对对象进行操作，这里先用简单的方式同样通过反射来获取属性信息
     * @param propertyName
     * @return
     */
    public boolean hasSetter(String propertyName){
        try {
            List<Field> fields = Arrays.asList(dataSource.getClass().getDeclaredFields());
            for(Field field : fields){
                if(field.getName().equals(propertyName)){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通过反射将数据设置到dataSource中
     * @param dataSource
     * @param propertiesName
     * @param value
     */
    private void setMetaInfo(DataSource dataSource, String propertiesName, String value) {
        try {
            Field field = dataSource.getClass().getDeclaredField(propertiesName);
            field.setAccessible(true);
            if(field.getType() == Integer.class || field.getType() == int.class){
                field.setInt(dataSource, Integer.valueOf(value));
            }else if(field.getType() == Long.class || field.getType() == long.class){
                field.setLong(dataSource, Long.valueOf(value));
            }else if(field.getType() == Boolean.class || field.getType() == boolean.class){
                field.setBoolean(dataSource, Boolean.valueOf(value));
            }else {
                field.set(dataSource, value);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
