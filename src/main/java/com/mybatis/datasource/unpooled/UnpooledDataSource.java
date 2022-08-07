package com.mybatis.datasource.unpooled;

import com.mybatis.io.Resource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * 无连接池的数据源
 */
public class UnpooledDataSource implements DataSource {

    private ClassLoader driverClassLoader;
    private Properties driverProperties;
    private static Map<String, Driver> registeredDrivers = new ConcurrentHashMap<>();

    private String driver;
    private String url;
    private String username;
    private String password;

    private Boolean autoCommit;
    private Integer defaultTransactionIsolationLevel;
    private Integer defaultNetworkTimeout;

    static {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        //如果有多个驱动就塞入多个
        while(drivers.hasMoreElements()){
            Driver driver = drivers.nextElement();
            registeredDrivers.put(driver.getClass().getName(), driver);
        }
    }

    /**
     * 获取连接的方法
     * @param username 数据库用户名
     * @param password 数据库密码
     * @return 数据库连接
     */
    private Connection doGetConnection(String username, String password) throws SQLException {
        Properties properties = new Properties();
        if(driverProperties != null){
            properties.putAll(driverProperties);
        }
        if(username != null){
            properties.setProperty("user", username);
        }
        if(password != null){
            properties.setProperty("password", password);
        }
        return doGetConnection(properties);
    }

    /**
     * 获取连接方法
     * @param properties 配置信息
     * @return 数据库连接
     */
    private Connection doGetConnection(Properties properties) throws SQLException {
        //初始化驱动
        initializeDriver();
        //通过驱动管理器使用数据库连接地址、用户名、密码等消息获取数据库连接
        Connection connection = DriverManager.getConnection(url, properties);
        //配置数据库连接
        configureConnection(connection);
        return null;
    }


    /**
     * 初始化驱动
     */
    private void initializeDriver() throws SQLException {
        if(!registeredDrivers.containsKey(driver)){
            Class<?> driverType;
            try{
                //获取驱动类对象
                if(driverClassLoader != null){
                    driverType = Class.forName(driver, true, driverClassLoader);
                }else {
                    driverType = Resource.classForName(driver);
                }
                //生成驱动实例
                Driver driverInstance = (Driver) driverType.getDeclaredConstructor().newInstance();
                //注册驱动实例
                DriverManager.registerDriver(driverInstance);
                registeredDrivers.put(driver, driverInstance);
            } catch (Exception e) {
                e.printStackTrace();
                throw new SQLException("无数据连接池的驱动设置错误，错误原因:",e);
            }
        }

    }

    /**
     * 配置数据库连接
     * @param connection
     */
    private void configureConnection(Connection connection) throws SQLException {
        //设置网络超时
        if(defaultNetworkTimeout != null){
            connection.setNetworkTimeout(Executors.newSingleThreadExecutor(), defaultNetworkTimeout);
        }
        //设置自动提交
        if(autoCommit != null && autoCommit != connection.getAutoCommit()){
            connection.setAutoCommit(autoCommit);
        }
        //设置事务隔离级别
        if(defaultTransactionIsolationLevel != null){
            connection.setTransactionIsolation(defaultTransactionIsolationLevel);
        }
    }



    @Override
    public Connection getConnection() throws SQLException {
        return doGetConnection(username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return doGetConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException("该类："+getClass().getName()+" 不是一个包装类");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return DriverManager.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        DriverManager.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }
}
