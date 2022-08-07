package com.mybatis.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * dataSource工厂接口
 */
public interface DataSourceFactory {

    void setProperties(Properties properties);

    /**
     * 获取数据源
     * @return 数据源
     */
    DataSource getDataSource();

}
