package com.mybatis.datasource.pooled;

import com.mybatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 数据连接池工厂
 */
public class PooledDataSourceFactory implements DataSourceFactory {

    private DataSource dataSource;

    public PooledDataSourceFactory() {
        this.dataSource = new PooledDataSource();
    }

    @Override
    public void setProperties(Properties properties) {

    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }


}
