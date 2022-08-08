package com.mybatis.datasource.unpooled;

import com.mybatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
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
                String value = driverProperties.getProperty(propertiesName);
                driverProperties.setProperty(propertiesName.substring("driver.".length()), value);
            }else{
                throw new RuntimeException("未知的数据源类型属性："+propertiesName);
            }
        }
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
