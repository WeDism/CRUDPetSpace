package com.pets_space.storages;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Properties;

public class Pool {
    private static final Logger LOG = LoggerFactory.getLogger(Pool.class);
    private final ComboPooledDataSource source;

    private static final Pool INSTANCE = new Pool();

    private Pool() {
        this.source = new ComboPooledDataSource();
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("db.properties"));

            source.setJdbcUrl(properties.getProperty("url"));
            source.setUser(properties.getProperty("username"));
            source.setPassword(properties.getProperty("password"));
            source.setDriverClass(properties.getProperty("driver"));
            source.setInitialPoolSize(5);
            source.setMinPoolSize(5);
            source.setAcquireIncrement(5);
            source.setMaxPoolSize(20);
        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    public static DataSource getDataSource() {
        return INSTANCE.source;
    }
}
