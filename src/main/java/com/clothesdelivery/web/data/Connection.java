package com.clothesdelivery.web.data;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
public class Connection {
    //@Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${DATABASE_PASSWORD}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Autowired
    private Environment env;

    public void init() {
        System.out.println("Database URL: " + env.getProperty("DATABASE_URL"));
        System.out.println("Database Username: " + username);
        System.out.println("Database Driver: " + driverClassName);
    }

    @Bean
    public DataSource dataSource() {
        init();
        var dataSource = new DriverManagerDataSource(url, username, password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        var adapter = new HibernateJpaVendorAdapter();

        adapter.setDatabase(Database.MYSQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        adapter.setPrepareConnection(true);

        return  adapter;
    }
}
