package com.example.testebsintegration.config;

import oracle.jdbc.pool.OracleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.SQLException;


@Configuration
@Profile("dev")
public class OracleUCPConfiguration {

    @Bean
    public DataSource dataSource() throws SQLException {

        OracleDataSource dataSource = new OracleDataSource ();
        dataSource.setUser("apps");
        dataSource.setPassword("apps");
       // dataSource.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
        dataSource.setURL("jdbc:oracle:thin:@192.168.88.248:1521:EBSDB");
        dataSource.setFastConnectionFailoverEnabled(true);
//        dataSource.setInitialPoolSize(5);
//        dataSource.setMinPoolSize(5);
//        dataSource.setMaxPoolSize(10);
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setConnectionCachingEnabled(true);
        return dataSource;
    }
}